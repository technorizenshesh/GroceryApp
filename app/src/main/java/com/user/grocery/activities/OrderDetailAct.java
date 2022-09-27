package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.databinding.ActivityOrderDetailBinding;
import com.user.grocery.models.SuccessResApplyVoucher;
import com.user.grocery.models.SuccessResApplyVoucher;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.retrofit.NetworkAvailablity;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.SharedPreferenceUtility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.USER_ID;
import static com.user.grocery.retrofit.Constant.showToast;

public class OrderDetailAct extends AppCompatActivity {

    ActivityOrderDetailBinding binding;
    private String productId="",packagingSize="",strProductPrice="",strProductName="",strAddressId="",strAddress="";
    private GroceryInterface apiInterface;
    private String strVoucherCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_order_detail);
       apiInterface = ApiClient.getClient().create(GroceryInterface.class);
       binding.header.tvtitle.setText(getString(R.string.order_details));
       binding.header.imgHeader.setOnClickListener(v ->
               {
                   finish();
               }
               );

        productId = getIntent().getExtras().getString("productId");
        packagingSize = getIntent().getExtras().getString("packagingSize");
        strProductName = getIntent().getExtras().getString("productName");
        strProductPrice = getIntent().getExtras().getString("productPrice");
        strAddressId = getIntent().getExtras().getString("addressId");
        strAddress = getIntent().getExtras().getString("address");
        binding.tvAddress.setText(strAddress);
        binding.tvProductName.setText(strProductName);
        binding.tvSubtotal.setText(strProductPrice);
        binding.tvTotal.setText(strProductPrice);
        binding.tvVoucher.setText("0");

        binding.ivShare.setOnClickListener(v ->
               {
                   String shareBody = "Product Name :"+strProductName+"\n\nProduct Price :"+strProductPrice;
                   Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                   sharingIntent.setType("text/plain");
                   sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                   sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                   OrderDetailAct.this.startActivity(Intent.createChooser(sharingIntent,getResources().getString(R.string.share_using)));
               }
               );

        binding.btnApply.setOnClickListener(v ->
                {
                    if(!binding.etCoupon.getText().toString().equalsIgnoreCase(""))
                    {
                        strVoucherCode = binding.etCoupon.getText().toString();
                        applyCode();
                    }else
                    {
                     showToast(OrderDetailAct.this,"Please enter voucher code");
                    }
                }
                );

       binding.btnPay.setOnClickListener(v ->
               {

                   if (NetworkAvailablity.checkNetworkStatus(this)) {
                       int selectedId = binding.rgGender.getCheckedRadioButtonId();
                       RadioButton radioButton = findViewById(selectedId);
                       Log.i("fsvscc", "SetupUi: " + radioButton.getText().toString());
                       addBooking();
                   } else {
                       Toast.makeText(this, getResources().getString(R.string.msg_noInternet), Toast.LENGTH_SHORT).show();
                   }
               }
               );

    }

    private void applyCode() {

        String userId = SharedPreferenceUtility.getInstance(OrderDetailAct.this).getString(USER_ID);
        DataManager.getInstance().showProgressMessage(OrderDetailAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("booking_user_id",userId);
        map.put("booking_product_id",productId);
        map.put("booking_packaging_id",packagingSize);
        map.put("booking_vouchers",strVoucherCode);
        Call<SuccessResApplyVoucher> call = apiInterface.applyVoucherCode(map);
        call.enqueue(new Callback<SuccessResApplyVoucher>() {
            @Override
            public void onResponse(Call<SuccessResApplyVoucher> call, Response<SuccessResApplyVoucher> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResApplyVoucher data = response.body();
                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        Toast.makeText(OrderDetailAct.this,"Please check mail",Toast.LENGTH_SHORT).show();
                       strVoucherCode = binding.etCoupon.getText().toString();
                       binding.tvTotal.setText(data.getResult().getDiscountPrice());
                       binding.tvVoucher.setText(data.getResult().getRewardPrice());
                       binding.etCoupon.setText("");
                    } else if (data.success == 0) {
                        showToast(OrderDetailAct.this, data.message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResApplyVoucher> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });

    }

    private void addBooking()
    {

        int selectedId = binding.rgGender.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        Log.i("fsvscc", "SetupUi: " + radioButton.getText().toString());

        String userId = SharedPreferenceUtility.getInstance(OrderDetailAct.this).getString(USER_ID);
        DataManager.getInstance().showProgressMessage(OrderDetailAct.this,getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("booking_user_id",userId);
        map.put("booking_product_id",productId);
        map.put("address_id",strAddressId);
        map.put("booking_packaging_id",packagingSize);
        map.put("booking_vouchers","");
        map.put("booking_delivery_time",radioButton.getText().toString());

        Call<ResponseBody> call = apiInterface.book(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    int data = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");
                    if (data==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        showToast(OrderDetailAct.this, message);
                        startActivity(new Intent(OrderDetailAct.this, HomeAct.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        OrderDetailAct.this.finish();
                    } else if (data==0) {
                        showToast(OrderDetailAct.this, message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });

    }

}