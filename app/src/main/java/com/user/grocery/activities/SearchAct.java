package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.adapters.BestSellerNewAdapters;
import com.user.grocery.databinding.ActivitySearchBinding;
import com.user.grocery.models.Products;
import com.user.grocery.models.SuccessResGetProducts;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.utility.DataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.showToast;

public class SearchAct extends AppCompatActivity {

    ActivitySearchBinding binding;

    private GroceryInterface apiInterface;

    private ArrayList<Products> productsArrayList = new ArrayList<>();

    public void insertdata()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_search);

        apiInterface = ApiClient.getClient().create(GroceryInterface.class);

        binding.ivBack.setOnClickListener(v ->
                {
                    finish();
                }
                );

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equalsIgnoreCase(""))
                {
                    getProducts(s.toString(),false);
                }
            }
        });

//        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    getProducts(binding.etSearch.getText().toString(),true);
//                    return true;
//                }
//                return false;
//            }
//        });

    }



    private void getProducts(String title,boolean show) {
        if(show)
        {
            DataManager.getInstance().showProgressMessage(SearchAct.this, getString(R.string.please_wait));
        }
        Map<String,String> map = new HashMap<>();
        map.put("product_name",title);
        Call<SuccessResGetProducts> call = apiInterface.getProductSearch(map);
        call.enqueue(new Callback<SuccessResGetProducts>() {
            @Override
            public void onResponse(Call<SuccessResGetProducts> call, Response<SuccessResGetProducts> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResGetProducts data = response.body();
                    Log.e("data",data.success+"");
                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());

                        productsArrayList.clear();
                        productsArrayList.addAll(data.getUserData());

                        binding.rvProducts.setHasFixedSize(true);
                        binding.rvProducts.setLayoutManager(new GridLayoutManager(SearchAct.this, 2));
                        binding.rvProducts.setAdapter(new BestSellerNewAdapters(SearchAct.this,productsArrayList));

                    } else if (data.success==0) {
                        showToast(SearchAct.this, data.message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResGetProducts> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!binding.etSearch.getText().toString().equalsIgnoreCase(""))
        {
            getProducts(binding.etSearch.getText().toString(),false);
        }
    }

}