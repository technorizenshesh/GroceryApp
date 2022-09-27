package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityItsSomethingElseBinding;

public class ItsSomethingElse extends AppCompatActivity {

    ActivityItsSomethingElseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_its_something_else);
        binding.imgHeader.setOnClickListener(v -> finish());


        binding.tvDeliveryIssue.setOnClickListener(v ->
                {
                    startActivity(new Intent(this,DeliveryIsssueAct.class));
                }
                );

        binding.tvPromotion.setOnClickListener(v ->
                {
                    startActivity(new Intent(this,PromotionDidNotWorkAct.class));
                }
        );

        binding.tvFeedback.setOnClickListener(v ->
                {
                    startActivity(new Intent(this,SubmitReviewAct.class));
                }
        );

        binding.tvContact.setOnClickListener(v ->
                {
                    startActivity(new Intent(this,One2OneChatAct.class));
                }
        );

    }
}