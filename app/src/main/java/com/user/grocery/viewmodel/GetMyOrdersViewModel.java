package com.user.grocery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.user.grocery.models.SuccessResGetMyOrders;
import com.user.grocery.models.SuccessResGetProductsByCategory;
import com.user.grocery.repositories.GroceryRepository;
import com.user.grocery.utility.SharedPreferenceUtility;

import static com.user.grocery.retrofit.Constant.USER_ID;

/**
 * Created by Ravindra Birla on 25,July,2022
 */
public class GetMyOrdersViewModel extends AndroidViewModel {

    private GroceryRepository profileRepository;

    private LiveData<SuccessResGetMyOrders> successResGetBannerLiveData;

    public GetMyOrdersViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new GroceryRepository();
        String userId = SharedPreferenceUtility.getInstance(application).getString(USER_ID);
        successResGetBannerLiveData = profileRepository.getMyOrders(userId);
    }

    public LiveData<SuccessResGetMyOrders> getUserProfile()
    {
        return  successResGetBannerLiveData;
    }

}
