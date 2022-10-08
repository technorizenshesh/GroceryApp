package com.user.grocery.retrofit;

import com.user.grocery.models.SuccessResApplyVoucher;
import com.user.grocery.models.SuccessResGetAddress;
import com.user.grocery.models.SuccessResGetBanner;
import com.user.grocery.models.SuccessResGetCategories;
import com.user.grocery.models.SuccessResGetChat;
import com.user.grocery.models.SuccessResGetFavProduct;
import com.user.grocery.models.SuccessResGetMyOrders;
import com.user.grocery.models.SuccessResGetNotification;
import com.user.grocery.models.SuccessResGetNotificationCount;
import com.user.grocery.models.SuccessResGetProductDetails;
import com.user.grocery.models.SuccessResGetProducts;
import com.user.grocery.models.SuccessResGetProductsByCategory;
import com.user.grocery.models.SuccessResGetProfile;
import com.user.grocery.models.SuccessResGetReviews;
import com.user.grocery.models.SuccessResGetVoucher;
import com.user.grocery.models.SuccessResInsertChat;
import com.user.grocery.models.SuccessResLogin;
import com.user.grocery.models.SuccessResSignup;
import com.user.grocery.models.SuccessResUpdateAddress;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GroceryInterface {

    @FormUrlEncoded
    @POST("signup")
    Call<SuccessResSignup> signup(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("login")
    Call<SuccessResLogin> login(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("forgot_password")
    Call<SuccessResUpdateAddress> forgotPassword(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_profile")
    Call<SuccessResGetProfile> getProfile(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("update_address")
    Call<SuccessResUpdateAddress> updateAddress(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("change_password")
    Call<SuccessResUpdateAddress> changePass(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("update_profile")
    Call<SuccessResUpdateAddress> updateProfile(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_banners")
    Call<SuccessResGetBanner> getBanners(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_categories")
    Call<SuccessResGetCategories> getCategoris(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_products")
    Call<SuccessResGetProducts> getProducts(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_products_by_id")
    Call<SuccessResGetProductDetails> getProductsDetails(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("fav_product")
    Call<SuccessResSignup> updateFavProducts(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_suggested_products_by_category")
    Call<SuccessResGetProducts> getSuggestedProducts(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_fav_products_by_user_id")
    Call<SuccessResGetFavProduct> getFavouriteProduct(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("add_address")
    Call<SuccessResUpdateAddress> addAddress(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_address_list")
    Call<SuccessResGetAddress> getAddress(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("update_addresss")
    Call<SuccessResUpdateAddress> editAddress(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("delete_address")
    Call<SuccessResUpdateAddress> deleteAddress(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_search_products")
    Call<SuccessResGetProducts> getProductSearch(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_products_by_category")
    Call<SuccessResGetProductsByCategory> getProductsByCategory(@FieldMap Map<String, String> paramHashMap);

    @GET("get_gift_vouchers")
    Call<SuccessResGetVoucher> getVoucher();

    @FormUrlEncoded
    @POST("get_booking")
    Call<ResponseBody> book(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("apply_coupon")
    Call<SuccessResApplyVoucher> applyVoucherCode(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_order_booking")
    Call<SuccessResGetMyOrders> getMyOrders(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("set_rating")
    Call<SuccessResUpdateAddress> addRating(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_rating")
    Call<SuccessResGetReviews> getRating(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("insert_chat")
    Call<SuccessResInsertChat> insertChat(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_chat")
    Call<SuccessResGetChat> getChat(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("getNotification")
    Call<SuccessResGetNotification> getNotification(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("getnotificationCount")
    Call<SuccessResGetNotificationCount> getNotificationCount(@FieldMap Map<String, String> paramHashMap);

}
