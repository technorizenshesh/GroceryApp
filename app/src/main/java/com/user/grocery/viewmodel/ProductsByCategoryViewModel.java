package com.user.grocery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.user.grocery.models.SuccessResGetProducts;
import com.user.grocery.models.SuccessResGetProductsByCategory;
import com.user.grocery.repositories.GroceryRepository;

/**
 * Created by Ravindra Birla on 25,July,2022
 */
public class ProductsByCategoryViewModel extends AndroidViewModel {

    private GroceryRepository profileRepository;

    private LiveData<SuccessResGetProductsByCategory> successResGetBannerLiveData;

    public ProductsByCategoryViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new GroceryRepository();

    }

    public LiveData<SuccessResGetProductsByCategory> getUserProfile(String category, String userId)
    {
        successResGetBannerLiveData = profileRepository.getProductsByCategory(category,userId);
        return  successResGetBannerLiveData;
    }

}
