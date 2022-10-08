package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.adapters.ItemListAdapters;
import com.user.grocery.adapters.NotificationAdapter;
import com.user.grocery.databinding.ActivityNotificationBinding;
import com.user.grocery.models.SuccessResGetNotification;
import com.user.grocery.models.SuccessResGetNotifications;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.SharedPreferenceUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.USER_ID;
import static com.user.grocery.retrofit.Constant.showToast;

public class NotificationAct extends AppCompatActivity {

    ActivityNotificationBinding binding;

    private ArrayList<SuccessResGetNotification.Notification> notificationList = new ArrayList<>();

    private GroceryInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_notification);
        binding.header.imgHeader.setOnClickListener(v -> finish());
        apiInterface = ApiClient.getClient().create(GroceryInterface.class);

        binding.header.tvtitle.setText(getString(R.string.notification));

        getProfile();

    }

    private void getProfile() {

        String userId = SharedPreferenceUtility.getInstance(NotificationAct.this).getString(USER_ID);

        Log.d("TAG", "getProfile: "+userId);

        DataManager.getInstance().showProgressMessage(NotificationAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("user_id",userId);
        map.put("notification_for","user");
        Call<SuccessResGetNotification> call = apiInterface.getNotification(map);
        call.enqueue(new Callback<SuccessResGetNotification>() {
            @Override
            public void onResponse(Call<SuccessResGetNotification> call, Response<SuccessResGetNotification> response) {

                DataManager.getInstance().hideProgressMessage();

                try {
                    SuccessResGetNotification data = response.body();
                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        notificationList.clear();
                        notificationList.addAll(data.getNotification());
                        binding.rvNotification.setHasFixedSize(true);
                        binding.rvNotification.setLayoutManager(new LinearLayoutManager(NotificationAct.this));
                        binding.rvNotification.setAdapter(new NotificationAdapter(NotificationAct.this,notificationList));
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);

                    } else if (data.success == 0) {
                        showToast(NotificationAct.this, data.getMessage());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResGetNotification> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

}