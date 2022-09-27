package com.user.grocery.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.user.grocery.models.SuccessResGetProductDetails;
import com.user.grocery.models.SuccessResSignup;
import com.user.grocery.repositories.GroceryRepository;

/**
 * Created by Ravindra Birla on 25,July,2022
 */
public class UpdateFavViewModel extends AndroidViewModel {

    private GroceryRepository profileRepository;

    private LiveData<SuccessResSignup> successResGetProfileLiveData;

    public UpdateFavViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new GroceryRepository();
    }

    public LiveData<SuccessResSignup> getProductDetailsLiveData(String userId, String productId,Activity activity)
    {

        successResGetProfileLiveData = profileRepository.updateFavProduct(userId,productId,activity);

        return  successResGetProfileLiveData;
    }

}
