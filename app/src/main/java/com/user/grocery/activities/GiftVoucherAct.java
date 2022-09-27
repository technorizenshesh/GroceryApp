package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.adapters.VouchersAdapter;
import com.user.grocery.databinding.ActivityGiftVoucherBinding;
import com.user.grocery.models.SuccessResGetVoucher;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.utility.DataManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.showToast;

public class GiftVoucherAct extends AppCompatActivity {

    ActivityGiftVoucherBinding binding;

    private ArrayList<SuccessResGetVoucher.Result> giftList = new ArrayList<>();

    private GroceryInterface apiInterface;

    private VouchersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_gift_voucher);
       binding.header.tvtitle.setText(getString(R.string.gift_vouchers));
       binding.header.imgHeader.setOnClickListener(v -> finish());

       apiInterface = ApiClient.getClient().create(GroceryInterface.class);

       mAdapter = new VouchersAdapter(GiftVoucherAct.this,giftList);

       binding.rvVoucers.setHasFixedSize(true);
       binding.rvVoucers.setLayoutManager(new LinearLayoutManager(GiftVoucherAct.this));
       binding.rvVoucers.setAdapter(mAdapter);

       getVoucher();

    }

    private void getVoucher() {

        DataManager.getInstance().showProgressMessage(GiftVoucherAct.this, getString(R.string.please_wait));

        Call<SuccessResGetVoucher> call = apiInterface.getVoucher();

        call.enqueue(new Callback<SuccessResGetVoucher>() {
            @Override
            public void onResponse(Call<SuccessResGetVoucher> call, Response<SuccessResGetVoucher> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResGetVoucher data = response.body();
                    Log.e("data",data.success+"");
                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        giftList.clear();
                        giftList.addAll(data.getResult());
                        mAdapter.notifyDataSetChanged();
                    } else if (data.success == 0) {
                        showToast(GiftVoucherAct.this, data.message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<SuccessResGetVoucher> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

}