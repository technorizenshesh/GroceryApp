package com.user.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocery.databinding.ActivityHelpBinding;

public class HelpAct extends AppCompatActivity {

    ActivityHelpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_help);

       binding.imgHeader.setOnClickListener(v -> finish());

    }
}