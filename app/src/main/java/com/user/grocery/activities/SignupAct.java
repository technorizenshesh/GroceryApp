package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.databinding.ActivitySignupBinding;
import com.user.grocery.models.SuccessResSignup;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.retrofit.NetworkAvailablity;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.SharedPreferenceUtility;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.isValidEmail;
import static com.user.grocery.retrofit.Constant.showToast;

public class SignupAct extends AppCompatActivity {

    ActivitySignupBinding binding;

    private String strEmail="",strName="",strPhone="",strPassword="";

    private GroceryInterface apiInterface;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding  = DataBindingUtil.setContentView(this,R.layout.activity_signup);

       apiInterface = ApiClient.getClient().create(GroceryInterface.class);
       
        binding.btnSignup.setOnClickListener(v ->
                {

                    if (NetworkAvailablity.checkNetworkStatus(this)) {
                        validation();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.msg_noInternet), Toast.LENGTH_SHORT).show();
                    }

                }
        );

       binding.rlDont.setOnClickListener(v ->
               {
                   startActivity(new Intent(SignupAct.this, LoginAct.class));
               }
               );

    }


    private void validation() {

        strEmail=binding.etEmail.getText().toString();
        strPassword=binding.etPass.getText().toString();
        strName=binding.etName.getText().toString();
        strPhone=binding.etPhone.getText().toString();

         if(strEmail.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Email.", Toast.LENGTH_SHORT).show();
        }else if(!isValidEmail(binding.etEmail.getText().toString().trim()))
        {
            Toast.makeText(this, "Please Valid Email.", Toast.LENGTH_SHORT).show();
        }else if(strPassword.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }else if(strName.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
        }else if(strPhone.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Mobile Number.", Toast.LENGTH_SHORT).show();

        }else
        {
//            startActivity(new Intent(SignUpPlumberAct.this,HomePlumberAct.class));
            signup();
        }
    }

    private void signup() {

        DataManager.getInstance().showProgressMessage(SignupAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("email",strEmail);
        map.put("name",strName);
        map.put("password",strPassword);
        map.put("dob","");
        map.put("mobile",strPhone);
        map.put("country_code",binding.ccp.getSelectedCountryCode());
        Call<SuccessResSignup> call = apiInterface.signup(map);
        call.enqueue(new Callback<SuccessResSignup>() {
            @Override
            public void onResponse(Call<SuccessResSignup> call, Response<SuccessResSignup> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResSignup data = response.body();

                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        startActivity(new Intent(SignupAct.this, LoginAct.class));

                    } else if (data.success == 0) {
                        showToast(SignupAct.this, data.getMessage());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResSignup> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }




}