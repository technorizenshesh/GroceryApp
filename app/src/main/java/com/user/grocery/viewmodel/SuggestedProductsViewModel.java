package com.user.grocery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.user.grocery.models.SuccessResGetProducts;
import com.user.grocery.repositories.GroceryRepository;

/**
 * Created by Ravindra Birla on 25,July,2022
 */
public class SuggestedProductsViewModel extends AndroidViewModel {

    private GroceryRepository profileRepository;

    private LiveData<SuccessResGetProducts> successResGetBannerLiveData;

    public SuggestedProductsViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new GroceryRepository();
    }

    public LiveData<SuccessResGetProducts> getUserProfile(String categoryId)
    {
        successResGetBannerLiveData = profileRepository.getSugestedProducts(categoryId);
        return  successResGetBannerLiveData;
    }

}
