package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.user.grocery.R;
import com.user.grocery.adapters.BestSellerAdapters;
import com.user.grocery.adapters.BestSellerNewAdapters;
import com.user.grocery.adapters.ProductByCategoryAdapter;
import com.user.grocery.databinding.ActivityProductsByCategoryBinding;
import com.user.grocery.fragments.HomeFragment;
import com.user.grocery.models.Products;
import com.user.grocery.models.SuccessResGetProductsByCategory;
import com.user.grocery.retrofit.NetworkAvailablity;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.ItemClickListener;
import com.user.grocery.utility.SharedPreferenceUtility;
import com.user.grocery.viewmodel.ProductDetailsViewModel;
import com.user.grocery.viewmodel.ProductsByCategoryViewModel;

import java.util.ArrayList;

import static com.user.grocery.retrofit.Constant.USER_ID;

public class ProductsByCategoryAct extends AppCompatActivity implements ItemClickListener {

    ActivityProductsByCategoryBinding binding;

    private ProductsByCategoryViewModel productsViewModel;
    private ArrayList<SuccessResGetProductsByCategory.ProductDatum> productsArrayList = new ArrayList<>();
    private String category="";

    private ProductByCategoryAdapter adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_products_by_category);

        category = getIntent().getStringExtra("category");

        binding.header.tvtitle.setText("Products By Category");
        binding.header.imgHeader.setOnClickListener(v ->
                {
                    finish();
                }
                );
        productsViewModel = ViewModelProviders.of(ProductsByCategoryAct.this).get(ProductsByCategoryViewModel.class);

        if (NetworkAvailablity.checkNetworkStatus(this)) {
            getProductsList();
        } else {
            Toast.makeText(this, getResources().getString(R.string.msg_noInternet), Toast.LENGTH_SHORT).show();
        }

    }


    public void getProductsList()
    {

        String userId = SharedPreferenceUtility.getInstance(ProductsByCategoryAct.this).getString(USER_ID);

        DataManager.getInstance().showProgressMessage(this, getString(R.string.please_wait));

        productsViewModel.getUserProfile(category,userId).observe(ProductsByCategoryAct.this, articleResponse -> {

            DataManager.getInstance().hideProgressMessage();

            if (articleResponse != null) {
                DataManager.getInstance().hideProgressMessage();
                productsArrayList.clear();
                productsArrayList.addAll(articleResponse.getProductData());

                adapters = new ProductByCategoryAdapter(ProductsByCategoryAct.this,productsArrayList);


                binding.rvBestDeails.setAdapter(adapters);

//                adapter.notifyDataSetChanged();

            }

        });
    }

    @Override
    public void imageItemClick(View v, String id, String categoryId) {

        startActivity(new Intent(ProductsByCategoryAct.this, ProductDetailAct.class).putExtra("productId", id).putExtra("categoryId", categoryId));

    }




}