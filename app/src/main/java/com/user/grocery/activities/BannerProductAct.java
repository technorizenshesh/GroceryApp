package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.user.grocery.R;
import com.user.grocery.adapters.BestSellerAdapters;
import com.user.grocery.adapters.FaqsAdapter;
import com.user.grocery.databinding.ActivityBannerProductBinding;
import com.user.grocery.fragments.HomeFragment;
import com.user.grocery.models.Products;
import com.user.grocery.models.Products;
import com.user.grocery.models.SuccessResGetProducts;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.ItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.showToast;

public class BannerProductAct extends AppCompatActivity implements ItemClickListener {

    ActivityBannerProductBinding binding;
    private String productId = "";
    private ArrayList<Products> productsArrayList = new ArrayList<>();
    private BestSellerAdapters bestSellerAdapters;
    private GroceryInterface apiInterface;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_banner_product);
       apiInterface = ApiClient.getClient().create(GroceryInterface.class);
       binding.header.imgHeader.setOnClickListener(v -> finish());
       binding.header.tvtitle.setText("Products");
       productId = getIntent().getExtras().getString("arg");
       getFaqs();
    }

    public void getFaqs()
    {

        DataManager.getInstance().showProgressMessage(BannerProductAct.this, getString(R.string.please_wait));
        Map<String, String> map = new HashMap<>();
        Call<SuccessResGetProducts> call = apiInterface.getProductsByBanner(map);
        call.enqueue(new Callback<SuccessResGetProducts>() {
            @Override
            public void onResponse(Call<SuccessResGetProducts> call, Response<SuccessResGetProducts> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResGetProducts data = response.body();
                    if (data.success==1) {
                        productsArrayList.clear();
                        productsArrayList.addAll(data.getUserData());
                        bestSellerAdapters = new BestSellerAdapters(BannerProductAct.this,productsArrayList, BannerProductAct.this);
                        binding.setProductsAdapter(bestSellerAdapters);
                    } else {
                        showToast(BannerProductAct.this, data.message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetProducts> call, Throwable t) {
                call.cancel();
                showToast(BannerProductAct.this, "Products not found");
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    @Override
    public void imageItemClick(View v, String id,String categoryId) {
        startActivity(new Intent(BannerProductAct.this, ProductDetailAct.class).putExtra("productId", id).putExtra("categoryId", categoryId));
    }
}