package com.user.grocery.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.user.grocery.R;
import com.user.grocery.activities.ProductDetailAct;
import com.user.grocery.databinding.BestSellerItemBinding;
import com.user.grocery.databinding.BestSellerItemBinding;
import com.user.grocery.models.Products;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class BestSellerNewAdapters extends RecyclerView.Adapter<BestSellerNewAdapters.OffersViewHolder>{


    public BestSellerItemBinding binding;

    private ArrayList<Products> productsArrayList;

     private Context context;

     public BestSellerNewAdapters(Context context,ArrayList<Products> productsArrayList)
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
        ImageView ivAdd = holder.itemView.findViewById(R.id.ivAdd);
        TextView tvtitle = holder.itemView.findViewById(R.id.tvtitle);
        TextView tvQuantity = holder.itemView.findViewById(R.id.tvQuantity);
        TextView tvPrice = holder.itemView.findViewById(R.id.tvPrice);

        Glide.with(context)
                .load(productsArrayList.get(position).getImage())
                .into(imageView);

        tvtitle.setText(productsArrayList.get(position).getName());
        tvQuantity.setText(productsArrayList.get(position).getPackaging().get(0).getPackaging());
        tvPrice.setText(productsArrayList.get(position).getPackaging().get(0).getPrice());

        ivAdd.setOnClickListener(v ->
                {
                    context.startActivity(new Intent(context, ProductDetailAct.class).putExtra("productId", productsArrayList.get(position).getId()).putExtra("categoryId", productsArrayList.get(position).getCategoryId()));
                }
                );

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
