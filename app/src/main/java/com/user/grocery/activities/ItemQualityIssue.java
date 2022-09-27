package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityItemQualityIssueBinding;

public class ItemQualityIssue extends AppCompatActivity {

    ActivityItemQualityIssueBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_item_quality_issue);

        binding.imgHeader.setOnClickListener(v -> finish());

    }
}