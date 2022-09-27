package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.grocery.databinding.NotificationItemBinding;
import com.user.grocery.databinding.NotificationItemBinding;
import com.user.grocery.models.SuccessResGetNotifications;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.OffersViewHolder>{

     public NotificationItemBinding binding;

     private Context context;
    private ArrayList<SuccessResGetNotifications> notifications;
     public NotificationAdapter(Context context,ArrayList<SuccessResGetNotifications> notifications)
     {
         this.context = context;
         this.notifications = notifications;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       binding = NotificationItemBinding.inflate(LayoutInflater.from(context));
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

        public OffersViewHolder(NotificationItemBinding binding1) {
            super(binding1.getRoot());
        }
    }

}
