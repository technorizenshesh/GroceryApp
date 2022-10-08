package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.databinding.ActivityHomeBinding;
import com.user.grocery.models.SuccessResGetNotificationCount;
import com.user.grocery.models.SuccessResGetProfile;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.Constant;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.SharedPreferenceUtility;
import com.user.grocery.viewmodel.ProfileViewModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.USER_ID;
import static com.user.grocery.retrofit.Constant.showToast;

public class HomeAct extends AppCompatActivity {

    public static NavController navController;
    private SuccessResGetProfile.UserData userData;
    ActivityHomeBinding binding;
    ProfileViewModel profileViewModel;

    private GroceryInterface apiInterface;
    LocalBroadcastManager lbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_home);

       apiInterface = ApiClient.getClient().create(GroceryInterface.class);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_cart, R.id.navigation_list, R.id.navigation_account)
                .build();
        profileViewModel = ViewModelProviders.of(HomeAct.this).get(ProfileViewModel.class);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        setUpNavigationDrawer();
        binding.ivNotification.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,NotificationAct.class));
                }
                );

        lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));
        getProfile();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getNotificationCount();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    public void setUpNavigationDrawer()
    {
        binding.ivMenu.setOnClickListener(v ->
                {
                    if (binding.drawer.isDrawerOpen(GravityCompat.START))
                        binding.drawer.closeDrawer(GravityCompat.START);
                    else binding.drawer.openDrawer(GravityCompat.START);
                }
                );

        binding.drawerLayout.tvHome.setOnClickListener(v ->
                {
                    binding.drawer.closeDrawer(GravityCompat.END);
                }
                );

        binding.drawerLayout.tvDaily.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,DailyDealsAct.class).putExtra("from","home"));
                }
        );

        binding.drawerLayout.tvGift.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,GiftVoucherAct.class));
                }
        );

        binding.drawerLayout.btnLogin.setOnClickListener(v ->
                {
                    SharedPreferenceUtility.getInstance(HomeAct.this).putBoolean(Constant.IS_USER_LOGGED_IN, false);
                    Intent intent = new Intent(HomeAct.this, LoginAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
        );

        binding.drawerLayout.tvShop.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,ShopsByDepartmentAct.class));
                }
        );

        binding.drawerLayout.tvOrders.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,OrderActivity.class));
                }
        );

        binding.drawerLayout.tvHelp.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,HelpAct.class));
                }
        );

        binding.drawerLayout.tvTerms.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,TernsAndConditionActivity.class));
                }
        );

        binding.drawerLayout.tvPp.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,PPActivity.class));
                }
        );
    }

    public void getProfile()
    {
        DataManager.getInstance().showProgressMessage(HomeAct.this, getString(R.string.please_wait));
        profileViewModel.getUserProfile().observe(this, articleResponse -> {
            if (articleResponse != null) {
                DataManager.getInstance().hideProgressMessage();
                userData = articleResponse.getUserData();
                binding.drawerLayout.tvName.setText(userData.getName());
                binding.drawerLayout.tvEmail.setText(userData.getEmail());
            }
        });
    }

    private void getNotificationCount() {

        String userId = SharedPreferenceUtility.getInstance(HomeAct.this).getString(USER_ID);

        DataManager.getInstance().showProgressMessage(HomeAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("user_id",userId);
        map.put("notification_for","User");
        Call<SuccessResGetNotificationCount> call = apiInterface.getNotificationCount(map);
        call.enqueue(new Callback<SuccessResGetNotificationCount>() {
            @Override
            public void onResponse(Call<SuccessResGetNotificationCount> call, Response<SuccessResGetNotificationCount> response) {

                DataManager.getInstance().hideProgressMessage();

                try {
                    SuccessResGetNotificationCount data = response.body();

                    if (data.success==1) {

                        String dataResponse = new Gson().toJson(response.body());

                        if(data.notification==0)
                        {
                            binding.tvNotificationCount.setVisibility(View.GONE);
                        } else
                        {
                            binding.tvNotificationCount.setVisibility(View.VISIBLE);
                            binding.tvNotificationCount.setText(data.getNotification()+"");
                        }
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                    } else if (data.success == 0) {
//                        showToast(HomeAct.this, data.getMessage());
                        binding.tvNotificationCount.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResGetNotificationCount> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotificationCount();
    }

}