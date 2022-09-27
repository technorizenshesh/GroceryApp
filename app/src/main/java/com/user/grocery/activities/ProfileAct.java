package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.databinding.ActivityProfileBinding;
import com.user.grocery.models.SuccessResGetProfile;
import com.user.grocery.models.SuccessResUpdateAddress;
import com.user.grocery.models.SuccessResUpdateAddress;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.retrofit.NetworkAvailablity;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.SharedPreferenceUtility;
import com.user.grocery.viewmodel.ProfileViewModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.USER_ID;
import static com.user.grocery.retrofit.Constant.isValidEmail;
import static com.user.grocery.retrofit.Constant.showToast;

public class ProfileAct extends AppCompatActivity {

    ActivityProfileBinding binding;
    ProfileViewModel profileViewModel;
    private SuccessResGetProfile.UserData userData;
    private String strName="",strEmail="",strPhone="";
    private GroceryInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = ApiClient.getClient().create(GroceryInterface.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        init();
    }

    public void init()
    {
        binding.header.imgHeader.setOnClickListener(v -> finish());
        binding.header.tvtitle.setText(getString(R.string.personal_details));

        binding.tvChange.setOnClickListener(v ->
                {
                    startActivity(new Intent(ProfileAct.this,ChangePassScreen.class));
                }
        );

        profileViewModel = ViewModelProviders.of(ProfileAct.this).get(ProfileViewModel.class);

        getProfile();

        binding.btnSave.setOnClickListener(v ->
                {
                    if (NetworkAvailablity.checkNetworkStatus(this)) {
                        validation();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.msg_noInternet), Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    private void validation() {

        strEmail=binding.etEmail.getText().toString();
        strName=binding.etName.getText().toString();
        strPhone=binding.etPhone.getText().toString();

        if(strEmail.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Email.", Toast.LENGTH_SHORT).show();
        }else if(!isValidEmail(binding.etEmail.getText().toString().trim()))
        {
            Toast.makeText(this, "Please Valid Email.", Toast.LENGTH_SHORT).show();
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

    public void getProfile()
    {
        DataManager.getInstance().showProgressMessage(ProfileAct.this, getString(R.string.please_wait));
        profileViewModel.getUserProfile().observe(this, articleResponse -> {
            if (articleResponse != null) {
                DataManager.getInstance().hideProgressMessage();
                userData = articleResponse.getUserData();

                binding.setProfileUser(userData);

                binding.ccp.setCountryForPhoneCode(Integer.parseInt(userData.getCountryCode()));

                binding.ccp.setCountryForPhoneCode(Integer.parseInt(userData.getCountryCode()));

            }
        });
    }

    private void signup() {
        String strUserId = SharedPreferenceUtility.getInstance(ProfileAct.this).getString(USER_ID);
        DataManager.getInstance().showProgressMessage(ProfileAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("user_id",strUserId);
        map.put("email",strEmail);
        map.put("name",strName);
        map.put("mobile",strPhone);
        map.put("country_code",binding.ccp.getSelectedCountryCode());
        Call<SuccessResUpdateAddress> call = apiInterface.updateProfile(map);
        call.enqueue(new Callback<SuccessResUpdateAddress>() {
            @Override
            public void onResponse(Call<SuccessResUpdateAddress> call, Response<SuccessResUpdateAddress> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResUpdateAddress data = response.body();
                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        showToast(ProfileAct.this, data.getMessage());
                    } else if (data.success == 0) {
                        showToast(ProfileAct.this, data.getMessage());
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



}