package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityConfirmLocationBinding;

public class ConfirmLocationAct extends AppCompatActivity {

    ActivityConfirmLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_location);


       binding.btnAddAddress.setOnClickListener(v ->
               {
                   startActivity(new Intent(ConfirmLocationAct.this,HomeAct.class));
               }
               );

    }
}