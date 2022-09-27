package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityDeliveryIsssueBinding;

public class DeliveryIsssueAct extends AppCompatActivity {

    ActivityDeliveryIsssueBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_delivery_isssue);

       binding.imgHeader.setOnClickListener(v -> finish());

    }
}