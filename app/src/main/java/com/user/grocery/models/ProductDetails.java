package com.user.grocery.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.user.grocery.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ravindra Birla on 29,July,2022
 */

public class ProductDetails implements Serializable {

    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("product_name")
    @Expose
    public String productName;
    @SerializedName("product_description")
    @Expose
    public String productDescription;
    @SerializedName("product_category_id")
    @Expose
    public String productCategoryId;
    @SerializedName("product_image")
    @Expose
    public String productImage;
    @SerializedName("category_name")
    @Expose
    public String categoryName;
    @SerializedName("category_image")
    @Expose
    public String categoryImage;
    @SerializedName("product_packaging")
    @Expose
    public List<ProductPackaging> productPackaging = null;
    @SerializedName("is_favorite")
    @Expose
    public String isFavorite;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public List<ProductPackaging> getProductPackaging() {
        return productPackaging;
    }

    public void setProductPackaging(List<ProductPackaging> productPackaging) {
        this.productPackaging = productPackaging;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean getImageResource()
    {
        if(this.isFavorite.equalsIgnoreCase("0"))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public class ProductPackaging {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("product_id")
        @Expose
        public String productId;
        @SerializedName("packaging")
        @Expose
        public String packaging;
        @SerializedName("price")
        @Expose
        public String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getPackaging() {
            return packaging;
        }

        public void setPackaging(String packaging) {
            this.packaging = packaging;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

    }

}
