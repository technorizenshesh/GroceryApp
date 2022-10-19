package com.user.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ravindra Birla on 28,July,2022
 */

public class SuccessResReorder implements Serializable {

    @SerializedName("result")
    @Expose
    public Result result;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class Result implements Serializable {

        @SerializedName("booking_id")
        @Expose
        public String bookingId;
        @SerializedName("booking_user_id")
        @Expose
        public String bookingUserId;
        @SerializedName("booking_product_id")
        @Expose
        public String bookingProductId;
        @SerializedName("booking_packaging_id")
        @Expose
        public String bookingPackagingId;
        @SerializedName("booking_vouchers_id")
        @Expose
        public String bookingVouchersId;
        @SerializedName("booking_delivery_time")
        @Expose
        public String bookingDeliveryTime;
        @SerializedName("bookig_status")
        @Expose
        public String bookigStatus;
        @SerializedName("driver_id")
        @Expose
        public String driverId;
        @SerializedName("address_id")
        @Expose
        public String addressId;
        @SerializedName("notification_status")
        @Expose
        public String notificationStatus;
        @SerializedName("signature_image")
        @Expose
        public String signatureImage;

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getBookingUserId() {
            return bookingUserId;
        }

        public void setBookingUserId(String bookingUserId) {
            this.bookingUserId = bookingUserId;
        }

        public String getBookingProductId() {
            return bookingProductId;
        }

        public void setBookingProductId(String bookingProductId) {
            this.bookingProductId = bookingProductId;
        }

        public String getBookingPackagingId() {
            return bookingPackagingId;
        }

        public void setBookingPackagingId(String bookingPackagingId) {
            this.bookingPackagingId = bookingPackagingId;
        }

        public String getBookingVouchersId() {
            return bookingVouchersId;
        }
        public void setBookingVouchersId(String bookingVouchersId) {
            this.bookingVouchersId = bookingVouchersId;
        }
        public String getBookingDeliveryTime() {
            return bookingDeliveryTime;
        }

        public void setBookingDeliveryTime(String bookingDeliveryTime) {
            this.bookingDeliveryTime = bookingDeliveryTime;
        }

        public String getBookigStatus() {
            return bookigStatus;
        }

        public void setBookigStatus(String bookigStatus) {
            this.bookigStatus = bookigStatus;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public String getNotificationStatus() {
            return notificationStatus;
        }

        public void setNotificationStatus(String notificationStatus) {
            this.notificationStatus = notificationStatus;
        }

        public String getSignatureImage() {
            return signatureImage;
        }

        public void setSignatureImage(String signatureImage) {
            this.signatureImage = signatureImage;
        }

    }
    
}
