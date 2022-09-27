package com.user.grocery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.user.grocery.models.SuccessResGetAddress;
import com.user.grocery.models.SuccessResGetFavProduct;
import com.user.grocery.repositories.GroceryRepository;

/**
 * Created by Ravindra Birla on 25,July,2022
 */

public class AddressViewModel extends AndroidViewModel {

    private GroceryRepository profileRepository;

    private LiveData<SuccessResGetAddress> successResGetBannerLiveData;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new GroceryRepository();
    }

    public LiveData<SuccessResGetAddress> getUserProfile(String userId)
    {
        successResGetBannerLiveData = profileRepository.getAddress(userId);
        return  successResGetBannerLiveData;
    }

}
