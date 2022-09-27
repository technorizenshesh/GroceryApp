package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.adapters.CartAdapters;
import com.user.grocery.databinding.ActivityCartBinding;

public class CartAct extends AppCompatActivity {

    ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart);

        binding.header.tvtitle.setText(getString(R.string.cart));

        binding.header.imgHeader.setOnClickListener(v -> finish());

//        binding.rvCart.setHasFixedSize(true);
//        binding.rvCart.setLayoutManager(new LinearLayoutManager(CartAct.this));
//        binding.rvCart.setAdapter(new CartAdapters(CartAct.this));

    }
}