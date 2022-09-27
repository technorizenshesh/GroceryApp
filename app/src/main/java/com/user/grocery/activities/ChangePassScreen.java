package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.databinding.ActivityChangePassScreenBinding;
import com.user.grocery.models.SuccessResUpdateAddress;
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

import static com.user.grocery.retrofit.Constant.USER_ID;
import static com.user.grocery.retrofit.Constant.showToast;

public class ChangePassScreen extends AppCompatActivity {

    ActivityChangePassScreenBinding binding;
    String oldPass = "",newConfirmPass = "" ,newPass = "", pass = "";

    private GroceryInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);

       binding = DataBindingUtil.setContentView(this,R.layout.activity_change_pass_screen);

       apiInterface = ApiClient.getClient().create(GroceryInterface.class);

       binding.layoutMyProfile.imgHeader.setOnClickListener(v -> finish());
       binding.layoutMyProfile.tvtitle.setText(getString(R.string.change_password));

        binding.btnLogin.setOnClickListener(v ->
                {

                    oldPass = binding.etPass.getText().toString().trim();
                    newPass = binding.etNewPass.getText().toString().trim();
                    newConfirmPass = binding.etNewConPass.getText().toString().trim();

                    if(isValid())
                    {
                        if (NetworkAvailablity.checkNetworkStatus(ChangePassScreen.this)) {

                            changePassword();

                        } else {
                            Toast.makeText(ChangePassScreen.this, getResources().getString(R.string.msg_noInternet), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ChangePassScreen.this, getResources().getString(R.string.on_error), Toast.LENGTH_SHORT).show();
                    }

                }
        );

    }

    private boolean isValid() {
        if (oldPass.equalsIgnoreCase("")) {
            binding.etPass.setError(getString(R.string.please_enter_old_pass));
            return false;
        }else if (newPass.equalsIgnoreCase("")) {
            binding.etNewPass.setError(getString(R.string.enter_new_password));
            return false;
        } else if (newConfirmPass.equalsIgnoreCase("")) {
            binding.etNewPass.setError(getString(R.string.please_enter_confirm_password));
            return false;
        }else if (!newConfirmPass.equalsIgnoreCase(newPass)) {
            binding.etNewConPass.setError(getString(R.string.password_mismatched));
            return false;
        }
        return true;
    }

    public void changePassword()
    {

        String userId = SharedPreferenceUtility.getInstance(ChangePassScreen.this).getString(USER_ID);
        DataManager.getInstance().showProgressMessage(ChangePassScreen.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("user_id",userId);
        map.put("o_password",oldPass);
        map.put("n_password",newPass);
        map.put("c_password",newConfirmPass);

        Call<SuccessResUpdateAddress> call = apiInterface.changePass(map);

        call.enqueue(new Callback<SuccessResUpdateAddress>() {
            @Override
            public void onResponse(Call<SuccessResUpdateAddress> call, Response<SuccessResUpdateAddress> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResUpdateAddress data = response.body();

                    if (data.success == 1) {
                        showToast(ChangePassScreen.this, data.message);
                        String dataResponse = new Gson().toJson(response.body());
                        binding.etNewPass.setText("");
                        binding.etPass.setText("");
                        binding.etNewConPass.setText("");
                    } else if (data.success == 0) {
                        showToast(ChangePassScreen.this, data.message);
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