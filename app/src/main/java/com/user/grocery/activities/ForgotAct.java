package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.databinding.ActivityForgotBinding;
import com.user.grocery.models.SuccessResUpdateAddress;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.retrofit.NetworkAvailablity;
import com.user.grocery.utility.DataManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.isValidEmail;
import static com.user.grocery.retrofit.Constant.showToast;

public class ForgotAct extends AppCompatActivity {

    ActivityForgotBinding binding;
    private String strEmail="";
    private GroceryInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot);

        apiInterface = ApiClient.getClient().create(GroceryInterface.class);

        binding.forgotAction.imgHeader.setOnClickListener(v ->
                {
                    finish();
                }
                );

        binding.forgotAction.tvtitle.setText(getString(R.string.forgot_password1));

        binding.btnSend.setOnClickListener(v ->
                {

                    strEmail = binding.etEmail.getText().toString().trim();

                    if(isValid())
                    {

                        if (NetworkAvailablity.checkNetworkStatus(this)) {
                            forgotPass();
                        } else {
                            Toast.makeText(this, getResources().getString(R.string.msg_noInternet), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, getResources().getString(R.string.on_error), Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
    private void forgotPass() {

        DataManager.getInstance().showProgressMessage(ForgotAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("email",strEmail);
        Call<SuccessResUpdateAddress> call = apiInterface.forgotPassword(map);
        call.enqueue(new Callback<SuccessResUpdateAddress>() {
            @Override
            public void onResponse(Call<SuccessResUpdateAddress> call, Response<SuccessResUpdateAddress> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResUpdateAddress data = response.body();
                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        Toast.makeText(ForgotAct.this,"Please check mail",Toast.LENGTH_SHORT).show();
                        binding.etEmail.setText("");
                    } else if (data.success == 0) {
                        showToast(ForgotAct.this, data.message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResUpdateAddress> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });

    }


    private boolean isValid() {
        if (strEmail.equalsIgnoreCase("")) {
            binding.etEmail.setError("Please enter email.");
            return false;
        } else if (!isValidEmail(strEmail)) {
            binding.etEmail.setError("Please enter valid email.");
            return false;
        }
        return true;
    }


}