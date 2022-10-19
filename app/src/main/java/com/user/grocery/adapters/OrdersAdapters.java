package com.user.grocery.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.activities.OrderStatusAct;
import com.user.grocery.databinding.OrderItemBinding;
import com.user.grocery.models.SuccessResGetMyOrders;
import com.user.grocery.utility.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class OrdersAdapters extends RecyclerView.Adapter<OrdersAdapters.OffersViewHolder>{

    public OrderItemBinding binding;

    private Context context;

    private ItemClickListener itemClickListener;

    private ArrayList<SuccessResGetMyOrders.Result> myOrdersList;

     public OrdersAdapters(Context context,ArrayList<SuccessResGetMyOrders.Result> myOrdersList,ItemClickListener itemClickListener)
     {
         this.context = context;
         this.myOrdersList =  myOrdersList;
         this.itemClickListener = itemClickListener;
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

        RelativeLayout rlReturn = holder.itemView.findViewById(R.id.rlReturn);

        rlReturn.setOnClickListener(v ->
                {
                    itemClickListener.imageItemClick(v,myOrdersList.get(position).getBookingId(),"");
                }
                );

        tvTitle.setOnClickListener(v ->
                {
                    Gson gson = new Gson();
                    String myJson = gson.toJson(myOrdersList.get(position));
                    context.startActivity(new Intent(context, OrderStatusAct.class).putExtra("myjson", myJson));
                }
                );
        if(myOrdersList.get(position).getBookigStatus().equalsIgnoreCase("DELIVERED"))
        {
            rlReturn.setVisibility(View.VISIBLE);
        }else
        {
            rlReturn.setVisibility(View.GONE);
        }

        if(myOrdersList.get(position).getBookigStatus().equalsIgnoreCase("Pending"))
        {
            tvTitle.setText("Order Status: ORDER RECEIVED");
        }else if(myOrdersList.get(position).getBookigStatus().equalsIgnoreCase("ACCEPTED_BY_ADMIN"))
        {
            tvTitle.setText("Order Status: ORDER ACCEPTED BY ADMIN");
        } else
        {
            tvTitle.setText("Order Status: "+myOrdersList.get(position).getBookigStatus());
        }

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