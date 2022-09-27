package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.user.grocery.R;
import com.user.grocery.databinding.CartItemBinding;
import com.user.grocery.databinding.CartItemBinding;
import com.user.grocery.databinding.FavItemBinding;
import com.user.grocery.models.SuccessResGetMyOrders;
import com.user.grocery.utility.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class CartAdapters extends RecyclerView.Adapter<CartAdapters.OffersViewHolder>{


    public CartItemBinding binding;

     private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<SuccessResGetMyOrders.Result> myOrdersList;

    private ItemClickListener itemClickListener;

     public CartAdapters(Context context,ArrayList<SuccessResGetMyOrders.Result> myOrdersList,ItemClickListener itemClickListener)
     {
         this.context = context;
         this.myOrdersList = myOrdersList;
         this.itemClickListener = itemClickListener;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CartItemBinding dataBinding= DataBindingUtil.inflate(layoutInflater, R.layout.cart_item, parent, false);
        return new OffersViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

         holder.cartItemBinding.setFavProduct(myOrdersList.get(position));
         holder.cartItemBinding.setClickListener(itemClickListener);

    }

    @Override
    public int getItemCount() {
        return myOrdersList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {

        private final CartItemBinding cartItemBinding;

        public OffersViewHolder(CartItemBinding binding1) {
            super(binding1.getRoot());
            this.cartItemBinding = binding1;
        }
    }

}
