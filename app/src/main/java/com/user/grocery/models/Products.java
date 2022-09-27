package com.user.grocery.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ravindra Birla on 28,July,2022
 */
public class Products {

    @SerializedName("id")
    @Expose
    public String id;
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
    @SerializedName("packaging")
    @Expose
    public List<Packaging> packaging = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Packaging> getPackaging() {
        return packaging;
    }

    public void setPackaging(List<Packaging> packaging) {
        this.packaging = packaging;
    }
    @BindingAdapter({"image"})
    public static void loadImage(ImageView imageView, String imagepath){
        Glide.with(imageView.getContext()).load(imagepath).into(imageView);
    }

    public class Packaging {

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
