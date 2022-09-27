package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.adapters.ItemListAdapters;
import com.user.grocery.adapters.NotificationAdapter;
import com.user.grocery.databinding.ActivityNotificationBinding;
import com.user.grocery.models.SuccessResGetNotifications;

import java.util.ArrayList;

public class NotificationAct extends AppCompatActivity {

    ActivityNotificationBinding binding;

    private ArrayList<SuccessResGetNotifications> notifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_notification);
        binding.header.imgHeader.setOnClickListener(v -> finish());
        binding.header.tvtitle.setText(getString(R.string.notification));
        binding.rvNotification.setHasFixedSize(true);
        binding.rvNotification.setLayoutManager(new LinearLayoutManager(NotificationAct.this));
        binding.rvNotification.setAdapter(new NotificationAdapter(NotificationAct.this,notifications));
    }
}