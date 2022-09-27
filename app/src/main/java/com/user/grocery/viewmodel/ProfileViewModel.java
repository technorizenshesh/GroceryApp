package com.user.grocery.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.user.grocery.models.SuccessResGetProfile;
import com.user.grocery.repositories.GroceryRepository;
import com.user.grocery.repositories.ProfileRepository;
import com.user.grocery.utility.SharedPreferenceUtility;

import static com.user.grocery.retrofit.Constant.USER_ID;

/**
 * Created by Ravindra Birla on 25,July,2022
 */
public class ProfileViewModel extends AndroidViewModel {

    private GroceryRepository profileRepository;

    private LiveData<SuccessResGetProfile> successResGetProfileLiveData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new GroceryRepository();

        String userId = SharedPreferenceUtility.getInstance(application).getString(USER_ID);

        successResGetProfileLiveData = profileRepository.getUserProfile(userId);
    }

    public LiveData<SuccessResGetProfile> getUserProfile()
    {
        return  successResGetProfileLiveData;
    }

}
