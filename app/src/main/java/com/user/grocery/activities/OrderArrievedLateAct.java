package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityOrderArrievedLateBinding;

public class OrderArrievedLateAct extends AppCompatActivity {

    ActivityOrderArrievedLateBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_order_arrieved_late);
       binding.imgHeader.setOnClickListener(v -> finish());


    }
}