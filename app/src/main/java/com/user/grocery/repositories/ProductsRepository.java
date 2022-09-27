package com.user.grocery.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.user.grocery.models.SuccessResGetBanner;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ravindra Birla on 28,July,2022
 */
class ProductsRepository {

    private GroceryInterface apiInterface;

    public ProductsRepository()
    {
        this.apiInterface = ApiClient.getClient().create(GroceryInterface.class);
    }

    public LiveData<SuccessResGetBanner> getBannerData(String userId)
    {
        Map<String,String> map = new HashMap<>();
        map.put("user_id",userId);
        MutableLiveData<SuccessResGetBanner> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResGetBanner> call = apiInterface.getBanners(map);
        call.enqueue(new Callback<SuccessResGetBanner>() {
            @Override
            public void onResponse(Call<SuccessResGetBanner> call, Response<SuccessResGetBanner> response) {
                try {
                    SuccessResGetBanner data = response.body();

                    mutableLiveData.setValue(response.body());

                    if (data.success.equals("1")) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);

                    } else if (data.success.equals("0")) {
//                        showToast(context, data.message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetBanner> call, Throwable t) {
                call.cancel();
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }
}
