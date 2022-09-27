package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityAddNewAddressBinding;

public class AddNewAddressAct extends AppCompatActivity {

    ActivityAddNewAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_add_new_address);

       binding.imgHeader.setOnClickListener(v ->
               {
                   finish();
               }
               );

    }
}