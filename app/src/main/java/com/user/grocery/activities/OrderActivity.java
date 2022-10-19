package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.adapters.ItemListAdapters;
import com.user.grocery.adapters.OrdersAdapters;
import com.user.grocery.adapters.ProductAdapter;
import com.user.grocery.databinding.ActivityOrderBinding;
import com.user.grocery.models.SuccessResGetMyOrders;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.retrofit.NetworkAvailablity;
import com.user.grocery.utility.BottomSheetReturn;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.ItemClickListener;
import com.user.grocery.viewmodel.GetMyOrdersViewModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.showToast;

public class OrderActivity extends AppCompatActivity implements ItemClickListener {

    ActivityOrderBinding binding;
    private GetMyOrdersViewModel myOrdersViewModel;
    private ArrayList<SuccessResGetMyOrders.Result> myOrdersList = new ArrayList<>();
    private BottomSheetReturn bottomSheetFragment;
    private GroceryInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order);
        myOrdersViewModel = ViewModelProviders.of(OrderActivity.this).get(GetMyOrdersViewModel.class);
        binding.header.imgHeader.setOnClickListener(v -> finish());
        binding.header.tvtitle.setText(getString(R.string.orders));

        apiInterface = ApiClient.getClient().create(GroceryInterface.class);

        if (NetworkAvailablity.checkNetworkStatus(this)) {
            getList();
        } else {
            Toast.makeText(this, getResources().getString(R.string.msg_noInternet), Toast.LENGTH_SHORT).show();
        }
    }
    private void getList()
    {
        DataManager.getInstance().showProgressMessage(OrderActivity.this, getString(R.string.please_wait));
        myOrdersViewModel.getUserProfile().observe(this, articleResponse -> {
           DataManager.getInstance().hideProgressMessage();
            if (articleResponse != null) {
                int status = articleResponse.getSuccess();
                String message = articleResponse.getMessage();
                DataManager.getInstance().hideProgressMessage();
                if(status==1)
                {
                    myOrdersList.clear();
                    myOrdersList.addAll(articleResponse.getResult());
                    binding.rvOrders.setHasFixedSize(true);
                    binding.rvOrders.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
                    binding.rvOrders.setAdapter(new OrdersAdapters(OrderActivity.this,myOrdersList,OrderActivity.this));
                }
                else
                {
                    Toast.makeText(OrderActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void imageItemClick(View v, String Id, String category) {

        bottomSheetFragment= new BottomSheetReturn(OrderActivity.this, new ItemClickListener() {
            @Override
            public void imageItemClick(View v, String reason, String category) {
                returnOrder(Id,reason);
            }
        });
        bottomSheetFragment.show(getSupportFragmentManager(),"ModalBottomSheet");
    }

    public void returnOrder(String orderId,String issue)
    {
        DataManager.getInstance().showProgressMessage(OrderActivity.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("order_id",orderId);
        map.put("bookig_status","RETURN");
        map.put("reason",issue);
        Call<ResponseBody> call = apiInterface.orderAccept(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
//                    SuccessResAddComment data = response.body();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String data = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (data.equals("1")) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        bottomSheetFragment.dismiss();
                    } else if (data.equals("0")) {
                        showToast(OrderActivity.this,message);
                    }
                } catch (Exception e) {
                    finish();
                    Log.d("TAG", "onResponse: "+e);
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                finish();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }
}