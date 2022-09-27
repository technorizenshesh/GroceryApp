package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.databinding.ActivityPaymentBinding;
import com.user.grocery.utility.BottomSheetFragment;

public class PaymentAct extends AppCompatActivity {

    ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_payment);

        binding.header.imgHeader.setOnClickListener(v -> finish());
        binding.header.tvtitle.setText(getString(R.string.payment));

        binding.btnVoucher.setOnClickListener(v ->
                {
                    BottomSheetFragment bottomSheetFragment= new BottomSheetFragment();
                    bottomSheetFragment.show(getSupportFragmentManager(),"ModalBottomSheet");
                }
                );

        binding.btnPay.setOnClickListener(v ->
                {
                    startActivity(new Intent(PaymentAct.this,OrderDetailAct.class));
                }
                );

    }
}