package com.user.grocery.repositories;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.user.grocery.models.SuccessResGetAddress;
import com.user.grocery.models.SuccessResGetBanner;
import com.user.grocery.models.SuccessResGetCategories;
import com.user.grocery.models.SuccessResGetFavProduct;
import com.user.grocery.models.SuccessResGetMyOrders;
import com.user.grocery.models.SuccessResGetProductDetails;
import com.user.grocery.models.SuccessResGetProducts;
import com.user.grocery.models.SuccessResGetProductsByCategory;
import com.user.grocery.models.SuccessResGetProfile;
import com.user.grocery.models.SuccessResSignup;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.showToast;

/**
 * Created by Ravindra Birla on 28,July,2022
 */
public class GroceryRepository {

    private GroceryInterface apiInterface;

    public GroceryRepository()
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

    public LiveData<SuccessResGetBanner> getBannerData()
    {
        Map<String,String> map = new HashMap<>();
        MutableLiveData<SuccessResGetBanner> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResGetBanner> call = apiInterface.getBanners(map);
        call.enqueue(new Callback<SuccessResGetBanner>() {
            @Override
            public void onResponse(Call<SuccessResGetBanner> call, Response<SuccessResGetBanner> response) {
                try {

                    SuccessResGetBanner data = response.body();
                    mutableLiveData.setValue(response.body());

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

    public LiveData<SuccessResGetCategories> getCategories()
    {
        Map<String,String> map = new HashMap<>();
        MutableLiveData<SuccessResGetCategories> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResGetCategories> call = apiInterface.getCategoris(map);
        call.enqueue(new Callback<SuccessResGetCategories>() {
            @Override
            public void onResponse(Call<SuccessResGetCategories> call, Response<SuccessResGetCategories> response) {
                try {

                    SuccessResGetCategories data = response.body();
                    mutableLiveData.setValue(response.body());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetCategories> call, Throwable t) {
                call.cancel();
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }

    public LiveData<SuccessResGetProducts> getProducts()
    {
        Map<String,String> map = new HashMap<>();
        MutableLiveData<SuccessResGetProducts> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResGetProducts> call = apiInterface.getProducts(map);
        call.enqueue(new Callback<SuccessResGetProducts>() {
            @Override
            public void onResponse(Call<SuccessResGetProducts> call, Response<SuccessResGetProducts> response) {
                try {

                    SuccessResGetProducts data = response.body();
                    mutableLiveData.setValue(response.body());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetProducts> call, Throwable t) {
                call.cancel();
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }

    public LiveData<SuccessResGetProductDetails> getProductsDetails(String userId,String productId)
    {
        Map<String,String> map = new HashMap<>();
        map.put("check_user_id",userId);
        map.put("product_id",productId);
        MutableLiveData<SuccessResGetProductDetails> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResGetProductDetails> call = apiInterface.getProductsDetails(map);
        call.enqueue(new Callback<SuccessResGetProductDetails>() {
            @Override
            public void onResponse(Call<SuccessResGetProductDetails> call, Response<SuccessResGetProductDetails> response) {
                try {
                    SuccessResGetProductDetails data = response.body();
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
            public void onFailure(Call<SuccessResGetProductDetails> call, Throwable t) {
                call.cancel();
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

    public LiveData<SuccessResSignup> updateFavProduct(String userId, String productId, Activity context)
    {
        Map<String,String> map = new HashMap<>();
        map.put("user_id",userId);
        map.put("product_id",productId);
        MutableLiveData<SuccessResSignup> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResSignup> call = apiInterface.updateFavProducts(map);
        call.enqueue(new Callback<SuccessResSignup>() {
            @Override
            public void onResponse(Call<SuccessResSignup> call, Response<SuccessResSignup> response) {
                try {
                    SuccessResSignup data = response.body();

                    mutableLiveData.setValue(response.body());

                    if (data.success.equals("1")) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);

                    } else if (data.success.equals("0")) {
                        showToast(context, data.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResSignup> call, Throwable t) {
                call.cancel();
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

    public LiveData<SuccessResGetProducts> getSugestedProducts(String categoryId)
    {
        Map<String,String> map = new HashMap<>();
        map.put("category_id",categoryId);
        MutableLiveData<SuccessResGetProducts> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResGetProducts> call = apiInterface.getSuggestedProducts(map);
        call.enqueue(new Callback<SuccessResGetProducts>() {
            @Override
            public void onResponse(Call<SuccessResGetProducts> call, Response<SuccessResGetProducts> response) {
                try {

                    SuccessResGetProducts data = response.body();
                    mutableLiveData.setValue(response.body());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetProducts> call, Throwable t) {
                call.cancel();
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }


    public LiveData<SuccessResGetFavProduct> getFavouriteProducts(String categoryId)
    {
        Map<String,String> map = new HashMap<>();
        map.put("user_id",categoryId);
        MutableLiveData<SuccessResGetFavProduct> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResGetFavProduct> call = apiInterface.getFavouriteProduct(map);
        call.enqueue(new Callback<SuccessResGetFavProduct>() {
            @Override
            public void onResponse(Call<SuccessResGetFavProduct> call, Response<SuccessResGetFavProduct> response) {
                try {

                    SuccessResGetFavProduct data = response.body();
                    mutableLiveData.setValue(response.body());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetFavProduct> call, Throwable t) {
                call.cancel();
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }

    public LiveData<SuccessResGetAddress> getAddress(String categoryId)
    {
        Map<String,String> map = new HashMap<>();
        map.put("user_id",categoryId);
        MutableLiveData<SuccessResGetAddress> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResGetAddress> call = apiInterface.getAddress(map);
        call.enqueue(new Callback<SuccessResGetAddress>() {
            @Override
            public void onResponse(Call<SuccessResGetAddress> call, Response<SuccessResGetAddress> response) {
                try {

                    SuccessResGetAddress data = response.body();
                    mutableLiveData.setValue(response.body());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetAddress> call, Throwable t) {
                call.cancel();
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }

    public LiveData<SuccessResGetProductsByCategory> getProductsByCategory(String category,String userId)
    {
        Map<String,String> map = new HashMap<>();
        map.put("category_id",category);
        map.put("check_user_id",userId);
        MutableLiveData<SuccessResGetProductsByCategory> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResGetProductsByCategory> call = apiInterface.getProductsByCategory(map);
        call.enqueue(new Callback<SuccessResGetProductsByCategory>() {
            @Override
            public void onResponse(Call<SuccessResGetProductsByCategory> call, Response<SuccessResGetProductsByCategory> response) {
                try {

                    SuccessResGetProductsByCategory data = response.body();
                    mutableLiveData.setValue(response.body());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetProductsByCategory> call, Throwable t) {
                call.cancel();
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }


    public LiveData<SuccessResGetMyOrders> getMyOrders(String userId)
    {

        Map<String,String> map = new HashMap<>();
        map.put("user_id",userId);
        MutableLiveData<SuccessResGetMyOrders> mutableLiveData = new MutableLiveData<>();
        Call<SuccessResGetMyOrders> call = apiInterface.getMyOrders(map);
        call.enqueue(new Callback<SuccessResGetMyOrders>() {
            @Override
            public void onResponse(Call<SuccessResGetMyOrders> call, Response<SuccessResGetMyOrders> response) {
                try {

                    SuccessResGetMyOrders data = response.body();
                    mutableLiveData.setValue(response.body());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetMyOrders> call, Throwable t) {
                call.cancel();
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }

}
