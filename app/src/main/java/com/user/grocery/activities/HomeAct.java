package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.user.grocery.R;
import com.user.grocery.databinding.ActivityHomeBinding;

public class HomeAct extends AppCompatActivity {

    public static NavController navController;

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_cart, R.id.navigation_list, R.id.navigation_account)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        setUpNavigationDrawer();

        binding.ivNotification.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,NotificationAct.class));
                }
                );

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
                    startActivity(new Intent(HomeAct.this,DailyDealsAct.class));
                }
        );

        binding.drawerLayout.tvGift.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,GiftVoucherAct.class));
                }
        );

     /*   binding.drawerLayout.tvMyList.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,ListAct.class));
                }
        );

        binding.drawerLayout.tvCart.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,CartAct.class));
                }
        );*/




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
                    startActivity(new Intent(HomeAct.this,PPActivity.class));
                }
        );

        binding.drawerLayout.tvPp.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this,TernsAndConditionActivity.class));
                }
        );

    }

}