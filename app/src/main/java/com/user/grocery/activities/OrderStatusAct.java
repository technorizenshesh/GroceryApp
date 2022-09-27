package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityOrderStatusBinding;

public class OrderStatusAct extends AppCompatActivity {

    ActivityOrderStatusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order_status);

        binding.tvtitle.setText(getString(R.string.order_status));
        binding.imgHeader.setOnClickListener(v -> finish());

        binding.imgShare.setOnClickListener(v ->
                {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Test.");
                   startActivity(Intent.createChooser(sharingIntent,getResources().getString(R.string.share_using)));
                }
                );
    }
}