package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityPlacingOrderBinding;

public class PlacingOrderAct extends AppCompatActivity {

    ActivityPlacingOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_placing_order);

       binding.header.imgHeader.setOnClickListener(v -> finish());
       binding.header.tvtitle.setText(getString(R.string.order));

    }
}