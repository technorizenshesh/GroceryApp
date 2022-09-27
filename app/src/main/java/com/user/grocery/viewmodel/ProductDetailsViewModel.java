package com.user.grocery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.user.grocery.models.SuccessResGetProductDetails;
import com.user.grocery.models.SuccessResGetProfile;
import com.user.grocery.repositories.GroceryRepository;
import com.user.grocery.utility.SharedPreferenceUtility;

import static com.user.grocery.retrofit.Constant.USER_ID;

/**
 * Created by Ravindra Birla on 25,July,2022
 */
public class ProductDetailsViewModel extends AndroidViewModel {

    private GroceryRepository profileRepository;

    private LiveData<SuccessResGetProductDetails> successResGetProfileLiveData;

    public ProductDetailsViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new GroceryRepository();

    }

    public LiveData<SuccessResGetProductDetails> getProductDetailsLiveData(String userId,String productId)
    {

        successResGetProfileLiveData = profileRepository.getProductsDetails(userId,productId);

        return  successResGetProfileLiveData;
    }

}
