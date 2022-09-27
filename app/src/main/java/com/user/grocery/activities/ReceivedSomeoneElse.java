package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityReceivedSomeoneElseBinding;

public class ReceivedSomeoneElse extends AppCompatActivity {

    ActivityReceivedSomeoneElseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       binding  = DataBindingUtil.setContentView(this,R.layout.activity_received_someone_else);

       binding.imgHeader.setOnClickListener(v -> finish());

    }
}