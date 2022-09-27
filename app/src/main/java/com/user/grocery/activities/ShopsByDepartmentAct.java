package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.user.grocery.R;
import com.user.grocery.adapters.BestSellerAdapters;
import com.user.grocery.adapters.CategoryAdapter;
import com.user.grocery.adapters.FeedAdapter;
import com.user.grocery.databinding.ActivityShopsByDepartmentBinding;
import com.user.grocery.models.Category;
import com.user.grocery.models.SuccessResGetCategories;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.ItemClickListener;
import com.user.grocery.viewmodel.CategoryViewModel;

import java.util.ArrayList;

public class ShopsByDepartmentAct extends AppCompatActivity implements ItemClickListener {

    ActivityShopsByDepartmentBinding binding;

    private CategoryViewModel categoryViewModel;

    private ArrayList<Category> categoriesLis = new ArrayList<>();

    private FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_shops_by_department);

       binding.header.imgHeader.setOnClickListener(v -> finish());
       binding.header.tvtitle.setText(getString(R.string.shops_by_department));

       binding.nestedScrollView.setNestedScrollingEnabled(false);

//       binding.rvDepartments.setHasFixedSize(true);
//       binding.rvDepartments.setLayoutManager(new GridLayoutManager(this, 2));
//
//       binding.rvDepartments.setAdapter(new CategoryAdapter(ShopsByDepartmentAct.this));

//        binding.rvBestSelling.setHasFixedSize(true);
//        binding.rvBestSelling.setLayoutManager(new LinearLayoutManager(ShopsByDepartmentAct.this, LinearLayoutManager.HORIZONTAL, false));
//        binding.rvBestSelling.setAdapter(new BestSellerAdapters(ShopsByDepartmentAct.this));

        categoryViewModel = ViewModelProviders.of(ShopsByDepartmentAct.this).get(CategoryViewModel.class);

        getCateories();

    }

    public void getCateories()
    {

        DataManager.getInstance().showProgressMessage(ShopsByDepartmentAct.this, getString(R.string.please_wait));
        categoryViewModel.getCategoriesLiveData().observe(this, articleResponse -> {
            if (articleResponse != null) {
                DataManager.getInstance().hideProgressMessage();

                categoriesLis.clear();
                categoriesLis.addAll(articleResponse.getUserData());

                feedAdapter = new FeedAdapter(ShopsByDepartmentAct.this,categoriesLis,ShopsByDepartmentAct.this);

                binding.setCategoriesAdapter(feedAdapter);

            }
        });
    }

    @Override
    public void imageItemClick(View v, String Id,String category) {

        startActivity(new Intent(ShopsByDepartmentAct.this,ProductsByCategoryAct.class).putExtra("category",category));

    }

}