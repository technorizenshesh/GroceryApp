package com.user.grocery.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ravindra Birla on 28,July,2022
 */

public class SuccessResGetCategories implements Serializable {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("user_data")
    @Expose
    public List<Category> userData = null;
    @SerializedName("success")
    @Expose
    public Integer success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Category> getUserData() {
        return userData;
    }

    public void setUserData(List<Category> userData) {
        this.userData = userData;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }


}
