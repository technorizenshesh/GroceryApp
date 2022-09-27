package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.user.grocery.R;
import com.user.grocery.databinding.BestSellerItemBinding;
import com.user.grocery.models.Products;
import com.user.grocery.models.SuccessResGetProductsByCategory;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class ProductByCategoryAdapter extends RecyclerView.Adapter<ProductByCategoryAdapter.OffersViewHolder>{


    public BestSellerItemBinding binding;

    private ArrayList<SuccessResGetProductsByCategory.ProductDatum> productsArrayList;

     private Context context;

     public ProductByCategoryAdapter(Context context, ArrayList<SuccessResGetProductsByCategory.ProductDatum> productsArrayList)
     {
         this.context = context;
         this.productsArrayList = productsArrayList;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = BestSellerItemBinding.inflate(LayoutInflater.from(context));

       return new OffersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

        ImageView imageView = holder.itemView.findViewById(R.id.iv_product);
        TextView tvtitle = holder.itemView.findViewById(R.id.tvtitle);
        TextView tvQuantity = holder.itemView.findViewById(R.id.tvQuantity);
        TextView tvPrice = holder.itemView.findViewById(R.id.tvPrice);

        Glide.with(context)
                .load(productsArrayList.get(position).getProductImage())
                .into(imageView);

        tvtitle.setText(productsArrayList.get(position).getProductName());
        tvQuantity.setText(productsArrayList.get(position).getProductPackaging().get(0).getPackaging());
        tvPrice.setText(productsArrayList.get(position).getProductPackaging().get(0).getPrice());

    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {
        public OffersViewHolder(BestSellerItemBinding binding1) {
            super(binding1.getRoot());
        }
    }

}
