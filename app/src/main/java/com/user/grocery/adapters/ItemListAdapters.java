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
import com.user.grocery.databinding.FavItemBinding;
import com.user.grocery.databinding.FavItemBinding;
import com.user.grocery.databinding.GroceryCategoryItemBinding;
import com.user.grocery.models.Category;
import com.user.grocery.models.FavProducts;
import com.user.grocery.utility.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class ItemListAdapters extends RecyclerView.Adapter<ItemListAdapters.OffersViewHolder>{


    public FavItemBinding binding;

     private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<FavProducts> favProductArrayList;
    private ItemClickListener itemClickListener;
     public ItemListAdapters(Context context,ArrayList<FavProducts> favProductArrayList,ItemClickListener itemClickListener)
     {
         this.context = context;
         this.favProductArrayList = favProductArrayList;
         this.itemClickListener=itemClickListener;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        FavItemBinding dataBinding= DataBindingUtil.inflate(layoutInflater, R.layout.fav_item, parent, false);
        return new ItemListAdapters.OffersViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {
        FavProducts feed= favProductArrayList.get(position);
        holder.feedDataBinding.setFavProduct(favProductArrayList.get(position));
        holder.feedDataBinding.setClickListener(itemClickListener);
    }

    @Override
    public int getItemCount() {
        return favProductArrayList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {
        private final FavItemBinding feedDataBinding;
        
        public OffersViewHolder(FavItemBinding binding1) {
            super(binding1.getRoot());
            this.feedDataBinding = binding1;
        }
    }

}
