package com.user.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SuccessResGetProducts implements Serializable {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("user_data")
    @Expose
    public List<Products> userData = null;
    @SerializedName("success")
    @Expose
    public Integer success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Products> getUserData() {
        return userData;
    }

    public void setUserData(List<Products> userData) {
        this.userData = userData;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
