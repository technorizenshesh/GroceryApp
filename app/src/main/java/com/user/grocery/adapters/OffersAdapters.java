package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.grocery.databinding.OffersItemBinding;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class OffersAdapters extends RecyclerView.Adapter<OffersAdapters.OffersViewHolder>{


    public OffersItemBinding binding;

     private Context context;

     public OffersAdapters(Context context)
     {
         this.context = context;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = OffersItemBinding.inflate(LayoutInflater.from(context));

       return new OffersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {

        public OffersViewHolder(OffersItemBinding binding1) {
            super(binding1.getRoot());
        }
    }

}
