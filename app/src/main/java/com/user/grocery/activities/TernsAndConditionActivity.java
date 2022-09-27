package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityTernsAndConditionBinding;

public class TernsAndConditionActivity extends AppCompatActivity {

    ActivityTernsAndConditionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_terns_and_condition);
        binding.header.imgHeader.setOnClickListener(v -> finish());
        binding.header.tvtitle.setText(getString(R.string.terms_and_condition));
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.loadUrl("https://ybb.qqn.mybluehost.me/_GROCERY/terms.html");
    }
}