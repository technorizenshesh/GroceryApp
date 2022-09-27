package com.user.grocery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.user.grocery.models.SuccessResGetCategories;
import com.user.grocery.models.SuccessResGetCategories;
import com.user.grocery.repositories.GroceryRepository;
import com.user.grocery.utility.SharedPreferenceUtility;

import static com.user.grocery.retrofit.Constant.USER_ID;

/**
 * Created by Ravindra Birla on 25,July,2022
 */
public class CategoryViewModel extends AndroidViewModel {

    private GroceryRepository profileRepository;

    private LiveData<SuccessResGetCategories> successResGetCategoriesLiveData;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new GroceryRepository();
        successResGetCategoriesLiveData = profileRepository.getCategories();
    }

    public LiveData<SuccessResGetCategories> getCategoriesLiveData()
    {
        return  successResGetCategoriesLiveData;
    }

}
