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
import com.user.grocery.activities.OrderStatusAct;
import com.user.grocery.databinding.OrderItemBinding;
import com.user.grocery.models.SuccessResGetMyOrders;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class OrdersAdapters extends RecyclerView.Adapter<OrdersAdapters.OffersViewHolder>{

    public OrderItemBinding binding;

    private Context context;

    private ArrayList<SuccessResGetMyOrders.Result> myOrdersList;

     public OrdersAdapters(Context context,ArrayList<SuccessResGetMyOrders.Result> myOrdersList)
     {
         this.context = context;
         this.myOrdersList =  myOrdersList;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = OrderItemBinding.inflate(LayoutInflater.from(context));
       return new OffersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

        TextView tvTitle = holder.itemView.findViewById(R.id.tvtitle);

        ImageView ivProduct = holder.itemView.findViewById(R.id.iv_product);

        tvTitle.setOnClickListener(v ->
                {
                    context.startActivity(new Intent(context, OrderStatusAct.class));
                }
                );

        tvTitle.setText("Order Status: "+myOrdersList.get(position).getBookigStatus());

        Glide.with(context)
                .load(myOrdersList.get(position).getImage())
                .into(ivProduct);

    }

    @Override
    public int getItemCount() {
        return myOrdersList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {
        public OffersViewHolder(OrderItemBinding binding1) {
            super(binding1.getRoot());
        }
    }

}