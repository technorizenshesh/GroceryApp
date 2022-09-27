package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.adapters.ItemListAdapters;
import com.user.grocery.databinding.ActivityListBinding;

public class ListAct extends AppCompatActivity {

   ActivityListBinding binding;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_list);
       binding.header.tvtitle.setText(getString(R.string.my_lists));
       binding.header.imgHeader.setOnClickListener(v -> finish());

//       binding.rvCart.setHasFixedSize(true);
//       binding.rvCart.setLayoutManager(new LinearLayoutManager(ListAct.this));
//       binding.rvCart.setAdapter(new ItemListAdapters(ListAct.this));

    }
}