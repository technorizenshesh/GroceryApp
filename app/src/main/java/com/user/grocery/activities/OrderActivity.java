package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.user.grocery.R;
import com.user.grocery.adapters.ItemListAdapters;
import com.user.grocery.adapters.OrdersAdapters;
import com.user.grocery.adapters.ProductAdapter;
import com.user.grocery.databinding.ActivityOrderBinding;
import com.user.grocery.models.SuccessResGetMyOrders;
import com.user.grocery.retrofit.NetworkAvailablity;
import com.user.grocery.utility.DataManager;
import com.user.grocery.viewmodel.GetMyOrdersViewModel;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ActivityOrderBinding binding;
    private GetMyOrdersViewModel myOrdersViewModel;

    private ArrayList<SuccessResGetMyOrders.Result> myOrdersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order);

        myOrdersViewModel = ViewModelProviders.of(OrderActivity.this).get(GetMyOrdersViewModel.class);

        binding.header.imgHeader.setOnClickListener(v -> finish());

        binding.header.tvtitle.setText(getString(R.string.orders));

        if (NetworkAvailablity.checkNetworkStatus(this)) {
            getList();
        } else {
            Toast.makeText(this, getResources().getString(R.string.msg_noInternet), Toast.LENGTH_SHORT).show();
        }

    }

    private void getList()
    {

        DataManager.getInstance().showProgressMessage(OrderActivity.this, getString(R.string.please_wait));
        myOrdersViewModel.getUserProfile().observe(this, articleResponse -> {
           DataManager.getInstance().hideProgressMessage();
            if (articleResponse != null) {
                int status = articleResponse.getSuccess();
                String message = articleResponse.getMessage();
                DataManager.getInstance().hideProgressMessage();
                if(status==1)
                {
                    myOrdersList.clear();
                    myOrdersList.addAll(articleResponse.getResult());
                    binding.rvOrders.setHasFixedSize(true);
                    binding.rvOrders.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
                    binding.rvOrders.setAdapter(new OrdersAdapters(OrderActivity.this,myOrdersList));
                }
                else
                {
                    Toast.makeText(OrderActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}