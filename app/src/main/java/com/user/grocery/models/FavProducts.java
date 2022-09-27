package com.user.grocery.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ravindra Birla on 30,July,2022
 */
public class FavProducts {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("product_category_id")
    @Expose
    private String productCategoryId;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_packaging")
    @Expose
    private List<ProductPacakging> productPacakging = null;

    @BindingAdapter({"product_image"})
    public static void loadImage(ImageView imageView, String imagepath){
        Glide.with(imageView.getContext()).load(imagepath).into(imageView);
    }

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

    public List<ProductPacakging> getProductPacakging() {
        return productPacakging;
    }

    public void setProductPacakging(List<ProductPacakging> productPacakging) {
        this.productPacakging = productPacakging;
    }

    public class ProductPacakging {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("packaging")
        @Expose
        private String packaging;
        @SerializedName("price")
        @Expose
        private String price;

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
