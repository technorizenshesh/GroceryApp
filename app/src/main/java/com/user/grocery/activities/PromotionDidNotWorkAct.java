package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityPromotionDidNotWorkBinding;

public class PromotionDidNotWorkAct extends AppCompatActivity {

    ActivityPromotionDidNotWorkBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_promotion_did_not_work);

        binding.imgHeader.setOnClickListener(v -> finish());
    }
}