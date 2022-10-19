package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.adapters.BestSellerAdapters;
import com.user.grocery.adapters.PackagingSizeAdapter;
import com.user.grocery.adapters.ReviewAdapter;
import com.user.grocery.databinding.ActivityProductDetailBinding;
import com.user.grocery.fragments.HomeFragment;
import com.user.grocery.models.ProductDetails;
import com.user.grocery.models.Products;
import com.user.grocery.models.SuccessResGetReviews;
import com.user.grocery.models.SuccessResSignup;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.ItemClickListener;
import com.user.grocery.utility.PackagingClickListener;
import com.user.grocery.utility.SharedPreferenceUtility;

import com.user.grocery.viewmodel.ProductDetailsViewModel;
import com.user.grocery.viewmodel.SuggestedProductsViewModel;
import com.user.grocery.viewmodel.UpdateFavViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.USER_ID;
import static com.user.grocery.retrofit.Constant.showToast;

public class ProductDetailAct extends AppCompatActivity implements ItemClickListener, PackagingClickListener {

    ActivityProductDetailBinding binding;

    private String productId = "",categoryId="";

    private ProductDetailsViewModel productDetailsViewModel;

    private GroceryInterface apiInterface;
    private ArrayList<SuccessResGetReviews.Result> resultArrayList = new ArrayList<>();
    private UpdateFavViewModel updateFavViewModel;
    private ProductDetails productDetails;
    private BestSellerAdapters bestSellerAdapters;
    private SuggestedProductsViewModel suggestedProductsViewModel;
    private String fav = "";
    private int status;
    private SuccessResSignup data;
    private ArrayList<ProductDetails.ProductPackaging> productPackagings = new ArrayList<>();
    private int selectPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_product_detail);
         binding.nestedScrollView.setNestedScrollingEnabled(false);
         apiInterface = ApiClient.getClient().create(GroceryInterface.class);
         productId = getIntent().getExtras().getString("productId");
         categoryId = getIntent().getExtras().getString("categoryId");
         binding.header.imgHeader.setOnClickListener(v -> finish());
         binding.header.tvtitle.setText(getString(R.string.products_details));
         binding.btnNext.setOnClickListener(v ->
                {
                    Intent intent = new Intent(this, AddressBookAct.class);
                    intent.putExtra("arg","product");
                    intent.putExtra("productId",productDetails.getProductId());
                    intent.putExtra("productName",productDetails.getProductName());
                    intent.putExtra("productPrice",productDetails.getProductPackaging().get(selectPosition).getPrice());
                    intent.putExtra("packagingSize",productDetails.getProductPackaging().get(selectPosition).getId());
                    startActivity(intent);
                }
                );
        productDetailsViewModel = ViewModelProviders.of(ProductDetailAct.this).get(ProductDetailsViewModel.class);
        updateFavViewModel = ViewModelProviders.of(ProductDetailAct.this).get(UpdateFavViewModel.class);
        suggestedProductsViewModel = ViewModelProviders.of(ProductDetailAct.this).get(SuggestedProductsViewModel.class);
        getProductDetails();
        getProductsList();
        getRating();
        binding.ivFv.setOnClickListener(v ->
                {
                    updateFav();
                }
                );
    }

    public void getRating()
    {
        DataManager.getInstance().showProgressMessage(ProductDetailAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("rating_product_id",productId);
        Call<SuccessResGetReviews> call = apiInterface.getRating(map);
        call.enqueue(new Callback<SuccessResGetReviews>() {
            @Override
            public void onResponse(Call<SuccessResGetReviews> call, Response<SuccessResGetReviews> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResGetReviews data = response.body();
                    if (data.success==1) {
                        showToast(ProductDetailAct.this, data.message);
                        binding.tvMyRating.setVisibility(View.VISIBLE);
                        resultArrayList.clear();
                        resultArrayList.addAll(data.getResult());
                        binding.rvReview.setHasFixedSize(true);
                        binding.rvReview.setLayoutManager(new LinearLayoutManager(ProductDetailAct.this));
                        binding.rvReview.setAdapter(new ReviewAdapter(ProductDetailAct.this,resultArrayList));
                    } else if (data.success == 0) {
                        showToast(ProductDetailAct.this, data.message);
                        binding.tvMyRating.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetReviews> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    public void getProductDetails()
    {
          String userId = SharedPreferenceUtility.getInstance(ProductDetailAct.this).getString(USER_ID);
          DataManager.getInstance().showProgressMessage(ProductDetailAct.this, getString(R.string.please_wait));
          productDetailsViewModel.getProductDetailsLiveData(userId,productId).observe(this, articleResponse -> {
          DataManager.getInstance().hideProgressMessage();
          if (articleResponse != null) {
              DataManager.getInstance().hideProgressMessage();
              productDetails = articleResponse.getProductData().get(0);
              binding.setProductDetails(productDetails);
              binding.rvPackagingSize.setHasFixedSize(true);
              binding.rvPackagingSize.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
              binding.rvPackagingSize.setAdapter(new PackagingSizeAdapter(ProductDetailAct.this,productDetails.getProductPackaging(),ProductDetailAct.this));
          }
        });
    }

    public void updateFav()
    {
        String userId = SharedPreferenceUtility.getInstance(ProductDetailAct.this).getString(USER_ID);
        DataManager.getInstance().showProgressMessage(ProductDetailAct.this, getString(R.string.please_wait));
        updateFavViewModel.getProductDetailsLiveData(userId,productId,ProductDetailAct.this).observe(this, articleResponse -> {
            DataManager.getInstance().hideProgressMessage();
            if (articleResponse != null) {
                data = articleResponse;
                if (data.success==1) {
                    getProductDetails();
                } else if (data.success==0) {
                    getProductDetails();
                }
            }
        });
    }

    private ArrayList<Products> productsArrayList = new ArrayList<>();
    public void getProductsList()
    {
        DataManager.getInstance().showProgressMessage(ProductDetailAct.this, getString(R.string.please_wait));
        suggestedProductsViewModel.getUserProfile(categoryId).observe(ProductDetailAct.this, articleResponse -> {
            DataManager.getInstance().hideProgressMessage();
            if (articleResponse != null) {
                DataManager.getInstance().hideProgressMessage();
                productsArrayList.clear();
                productsArrayList.addAll(articleResponse.getUserData());
                bestSellerAdapters = new BestSellerAdapters(ProductDetailAct.this,productsArrayList,ProductDetailAct.this);
                binding.setProductsAdapter(bestSellerAdapters);
            }
        });
    }

    @Override
    public void imageItemClick(View v, String id,String category) {
        startActivity(new Intent(ProductDetailAct.this, ProductDetailAct.class).putExtra("productId", id).putExtra("categoryId", categoryId));
    }

    @Override
    public void packageItemClick(View v, int id, String category) {

        selectPosition = id;

        binding.tvPrice.setText("$"+productDetails.getProductPackaging().get(selectPosition).getPrice());

    }
}