package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.databinding.ActivityOrderStatusBinding;
import com.user.grocery.models.SuccessResGetMyOrders;

public class OrderStatusAct extends AppCompatActivity {

    ActivityOrderStatusBinding binding;
    private SuccessResGetMyOrders.Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order_status);
        Gson gson = new Gson();
        result = gson.fromJson(getIntent().getStringExtra("myjson"), SuccessResGetMyOrders.Result.class);
        binding.tvtitle.setText(getString(R.string.order_status));
        binding.imgHeader.setOnClickListener(v -> finish());
        if(result.getBookigStatus().equalsIgnoreCase("PENDING"))
        {
            binding.view1.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
            binding.tvtitle.setText(getString(R.string.order_received));
        } else if(result.getBookigStatus().equalsIgnoreCase("ACCEPTED_BY_ADMIN"))
        {
            binding.tvtitle.setText(getString(R.string.order_accepted_by_admin));
            binding.view1.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
            binding.view2.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
        } else if(result.getBookigStatus().equalsIgnoreCase("PROCESSING"))
        {
            binding.tvtitle.setText(getString(R.string.processing));
            binding.view1.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
            binding.view2.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
            binding.view3.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
        }else if(result.getBookigStatus().equalsIgnoreCase("PICKUP"))
        {
            binding.tvtitle.setText(getString(R.string.order_picked_by_driver));
            binding.view1.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
            binding.view2.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
            binding.view3.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
            binding.view4.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
        }else if(result.getBookigStatus().equalsIgnoreCase("DELIVERED"))
        {
            binding.tvtitle.setText(getString(R.string.order_delivered));
            binding.view1.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
            binding.view2.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
            binding.view3.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
            binding.view4.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
            binding.view5.setBackground(getDrawable(R.drawable.dark_blue_cornors_30));
        }
        binding.imgShare.setOnClickListener(v ->
                {
                    String shareBody = "Product: "+result.getName()+"\n\nPrice: "+result.getPrice()+"\n\n"+result.getImage();
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                   startActivity(Intent.createChooser(sharingIntent,getResources().getString(R.string.share_using)));
                }
                );
    }
}