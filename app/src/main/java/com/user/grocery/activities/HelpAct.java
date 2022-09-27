package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityHelp2Binding;
import com.user.grocery.databinding.ActivityHelpBinding;

public class HelpAct extends AppCompatActivity {

    ActivityHelp2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_help2);

       binding.header.imgHeader.setOnClickListener(v -> finish());
       binding.header.tvtitle.setText(getString(R.string.help));

    }
}