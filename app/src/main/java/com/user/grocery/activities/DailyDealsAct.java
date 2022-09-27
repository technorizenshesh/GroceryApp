package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.adapters.BestSellerAdapters;
import com.user.grocery.adapters.BestSellerNewAdapters;
import com.user.grocery.databinding.ActivityDailyDealsBinding;
import com.user.grocery.fragments.HomeFragment;
import com.user.grocery.models.Products;
import com.user.grocery.models.SuccessResGetProducts;
import com.user.grocery.utility.DataManager;
import com.user.grocery.viewmodel.ProductsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.showToast;

public class DailyDealsAct extends AppCompatActivity {

    ActivityDailyDealsBinding binding;

    private ArrayList<Products> productsArrayList = new ArrayList<>();
    private ProductsViewModel productsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_daily_deals);
        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);

        getProductsList();

    }

    public void getProductsList()
    {

        DataManager.getInstance().showProgressMessage(DailyDealsAct.this, getString(R.string.please_wait));

        productsViewModel.getUserProfile().observe(DailyDealsAct.this, articleResponse -> {
            DataManager.getInstance().hideProgressMessage();

            if (articleResponse != null) {
                DataManager.getInstance().hideProgressMessage();
                productsArrayList.clear();
                productsArrayList.addAll(articleResponse.getUserData());
                binding.rvBestDeails.setHasFixedSize(true);
                binding.rvBestDeails.setLayoutManager(new GridLayoutManager(DailyDealsAct.this,2));
                binding.rvBestDeails.setAdapter(new BestSellerNewAdapters(DailyDealsAct.this,productsArrayList));

//                adapter.notifyDataSetChanged();
            }

        });
    }



}