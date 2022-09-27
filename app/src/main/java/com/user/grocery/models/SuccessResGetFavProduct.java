package com.user.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SuccessResGetFavProduct implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_data")
    @Expose
    private List<FavProducts> productData = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FavProducts> getProductData() {
        return productData;
    }

    public void setProductData(List<FavProducts> productData) {
        this.productData = productData;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }



}
