package com.user.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SuccessResGetReviews implements Serializable {

    @SerializedName("average_Rating")
    @Expose
    public Integer averageRating;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("success")
    @Expose
    public Integer success;

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

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

    public class Result {

        @SerializedName("rating_id")
        @Expose
        public String ratingId;
        @SerializedName("rating_product_id")
        @Expose
        public String ratingProductId;
        @SerializedName("rating_points")
        @Expose
        public String ratingPoints;
        @SerializedName("rating_status")
        @Expose
        public String ratingStatus;
        @SerializedName("rating_date_time")
        @Expose
        public String ratingDateTime;
        @SerializedName("rating_user_id")
        @Expose
        public String ratingUserId;
        @SerializedName("rating_comment")
        @Expose
        public String ratingComment;
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("user_image")
        @Expose
        public String userImage;

        public String getRatingId() {
            return ratingId;
        }

        public void setRatingId(String ratingId) {
            this.ratingId = ratingId;
        }

        public String getRatingProductId() {
            return ratingProductId;
        }

        public void setRatingProductId(String ratingProductId) {
            this.ratingProductId = ratingProductId;
        }

        public String getRatingPoints() {
            return ratingPoints;
        }

        public void setRatingPoints(String ratingPoints) {
            this.ratingPoints = ratingPoints;
        }

        public String getRatingStatus() {
            return ratingStatus;
        }

        public void setRatingStatus(String ratingStatus) {
            this.ratingStatus = ratingStatus;
        }

        public String getRatingDateTime() {
            return ratingDateTime;
        }

        public void setRatingDateTime(String ratingDateTime) {
            this.ratingDateTime = ratingDateTime;
        }

        public String getRatingUserId() {
            return ratingUserId;
        }

        public void setRatingUserId(String ratingUserId) {
            this.ratingUserId = ratingUserId;
        }

        public String getRatingComment() {
            return ratingComment;
        }

        public void setRatingComment(String ratingComment) {
            this.ratingComment = ratingComment;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

    }

}
