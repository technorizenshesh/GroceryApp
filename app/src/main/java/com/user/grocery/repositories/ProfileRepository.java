package com.user.grocery.repositories;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.user.grocery.models.SuccessResGetProfile;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.utility.SharedPreferenceUtility;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.showToast;

/**
 * Created by Ravindra Birla on 25,July,2022
 */
public class ProfileRepository {

    private GroceryInterface apiInterface;

    public ProfileRepository()
    {
        this.apiInterface = ApiClient.getClient().create(GroceryInterface.class);
    }

    public LiveData<SuccessResGetProfile> getUserProfile(String userId)
    {
        Map<String,String> map = new HashMap<>();
        map.put("user_id",userId);
        MutableLiveData<SuccessResGetProfile> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResGetProfile> call = apiInterface.getProfile(map);
        call.enqueue(new Callback<SuccessResGetProfile>() {
            @Override
            public void onResponse(Call<SuccessResGetProfile> call, Response<SuccessResGetProfile> response) {
                try {
                    SuccessResGetProfile data = response.body();

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
            public void onFailure(Call<SuccessResGetProfile> call, Throwable t) {
                call.cancel();
                mutableLiveData.setValue(null);
            }
        });


        return mutableLiveData;
    }




}
