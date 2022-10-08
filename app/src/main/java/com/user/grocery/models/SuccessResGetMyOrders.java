package com.user.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SuccessResGetMyOrders implements Serializable {

    @SerializedName("result")
    @Expose
    public List<Result> result = null;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("success")
    @Expose
    public Integer success;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
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
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("discount_price")
        @Expose
        public String discountPrice;
        @SerializedName("reward_price")
        @Expose
        public String rewardPrice;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("category_id")
        @Expose
        public String categoryId;
        @SerializedName("image")
        @Expose
        public String image;

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

        public String getRewardPrice() {
            return rewardPrice;
        }

        public void setRewardPrice(String rewardPrice) {
            this.rewardPrice = rewardPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

}
