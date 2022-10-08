package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.grocery.R;
import com.user.grocery.databinding.NotificationItemBinding;
import com.user.grocery.databinding.NotificationItemBinding;
import com.user.grocery.models.SuccessResGetNotification;
import com.user.grocery.models.SuccessResGetNotifications;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.OffersViewHolder>{

    public NotificationItemBinding binding;

    private Context context;
    private ArrayList<SuccessResGetNotification.Notification> notificationList;

    public NotificationAdapter(Context context,ArrayList<SuccessResGetNotification.Notification> notificationList)
     {
         this.context = context;
         this.notificationList = notificationList;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       binding = NotificationItemBinding.inflate(LayoutInflater.from(context));
       return new OffersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {
        TextView textViewTitle = holder.itemView.findViewById(R.id.tvtitle);

        TextView tvTimeAgo = holder.itemView.findViewById(R.id.tvQuantity);

        textViewTitle.setText(notificationList.get(position).getNotificationMessage());

        tvTimeAgo.setText(notificationList.get(position).getNotificationDateTime());

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {
        public OffersViewHolder(NotificationItemBinding binding1) {
            super(binding1.getRoot());
        }
    }

}
