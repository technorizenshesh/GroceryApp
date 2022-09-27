package com.user.grocery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.android.gms.location.GeofencingRequest;
import com.user.grocery.models.SuccessResGetBanner;
import com.user.grocery.models.SuccessResGetProfile;
import com.user.grocery.repositories.GroceryRepository;
import com.user.grocery.repositories.ProfileRepository;
import com.user.grocery.utility.SharedPreferenceUtility;

import static com.user.grocery.retrofit.Constant.USER_ID;

/**
 * Created by Ravindra Birla on 25,July,2022
 */
public class BannerViewModel extends AndroidViewModel {

    private GroceryRepository profileRepository;

    private LiveData<SuccessResGetBanner> successResGetBannerLiveData;


    public BannerViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new GroceryRepository();

        String userId = SharedPreferenceUtility.getInstance(application).getString(USER_ID);

        successResGetBannerLiveData = profileRepository.getBannerData();
    }

    public LiveData<SuccessResGetBanner> getUserProfile()
    {
        return  successResGetBannerLiveData;
    }

}
