package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.user.grocery.R;
import com.user.grocery.databinding.FoodItemBinding;
import com.user.grocery.databinding.FoodItemBinding;
import com.user.grocery.models.SuccessResGetMyOrders;
import com.user.grocery.utility.PackagingClickListener;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.OffersViewHolder>{


    public FoodItemBinding binding;

     private Context context;

     private int selectedPosition =0;

    private ArrayList<SuccessResGetMyOrders.Result> myOrdersList;

     private PackagingClickListener packagingClickListener;

     public ProductAdapter(Context context,ArrayList<SuccessResGetMyOrders.Result> myOrdersList,PackagingClickListener packagingClickListener)
     {
         this.context = context;
         this.myOrdersList = myOrdersList;
         this.packagingClickListener = packagingClickListener;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = FoodItemBinding.inflate(LayoutInflater.from(context));
       return new OffersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

        ImageView imageView = holder.itemView.findViewById(R.id.iv_product);

         if(position==selectedPosition)
         {
             imageView.setBackgroundResource(R.drawable.stroke_blue);
             packagingClickListener.packageItemClick(new View(context),position,"");
         }
         else
         {
             imageView.setBackgroundResource(0);
         }

        Glide.with(context)
                .load(myOrdersList.get(position).getImage())
                .into(imageView);

         imageView.setOnClickListener(v ->
                 {

                     selectedPosition = position;
                     notifyDataSetChanged();

                 }
                 );

    }

    @Override
    public int getItemCount() {
        return myOrdersList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {

        public OffersViewHolder(FoodItemBinding binding1) {
            super(binding1.getRoot());
        }
    }

}
