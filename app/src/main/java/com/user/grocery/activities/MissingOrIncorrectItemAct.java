package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityMissingOrIncorrectItemBinding;

public class MissingOrIncorrectItemAct extends AppCompatActivity {

    ActivityMissingOrIncorrectItemBinding  binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_missing_or_incorrect_item);

       binding.imgHeader.setOnClickListener(v -> finish());



    }
}