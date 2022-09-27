package com.user.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SuccessResGetBooking implements Serializable {

    @SerializedName("result")
    @Expose
    public Result result;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("vouchers_message")
    @Expose
    public String vouchersMessage;
    @SerializedName("success")
    @Expose
    public Integer success;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVouchersMessage() {
        return vouchersMessage;
    }

    public void setVouchersMessage(String vouchersMessage) {
        this.vouchersMessage = vouchersMessage;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public class Result {

        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("discount_price")
        @Expose
        public String discountPrice;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

    }

}
