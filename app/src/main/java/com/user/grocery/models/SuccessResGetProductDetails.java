package com.user.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SuccessResGetProductDetails implements Serializable {


    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("product_data")
    @Expose
    public List<ProductDetails> productData = null;
    @SerializedName("success")
    @Expose
    public Integer success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProductDetails> getProductData() {
        return productData;
    }

    public void setProductData(List<ProductDetails> productData) {
        this.productData = productData;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
