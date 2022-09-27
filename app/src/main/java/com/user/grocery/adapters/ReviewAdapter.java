package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.user.grocery.R;
import com.user.grocery.databinding.ReviewItemBinding;
import com.user.grocery.databinding.ReviewItemBinding;
import com.user.grocery.models.SuccessResGetReviews;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.OffersViewHolder>{

     public ReviewItemBinding binding;

     private Context context;

     private ArrayList<SuccessResGetReviews.Result> resultArrayList;

     public ReviewAdapter(Context context,ArrayList<SuccessResGetReviews.Result> resultArrayList)
     {
         this.context = context;
         this.resultArrayList = resultArrayList;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       binding = ReviewItemBinding.inflate(LayoutInflater.from(context));
       return new OffersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

        CircleImageView circleImageView = holder.itemView.findViewById(R.id.iv_product);
        TextView tvtitle = holder.itemView.findViewById(R.id.tvtitle);
        TextView tvQuantity = holder.itemView.findViewById(R.id.tvQuantity);
        TextView tvReivew = holder.itemView.findViewById(R.id.tvReivew);
        RatingBar ratingBar = holder.itemView.findViewById(R.id.ratingBar);

         Glide.with(context)
                .load(resultArrayList.get(position).getUserImage())
                .into(circleImageView);

        ratingBar.setRating(Float.valueOf(resultArrayList.get(position).getRatingPoints()));

        tvtitle.setText(resultArrayList.get(position).getUserName());

        tvReivew.setText(resultArrayList.get(position).getRatingComment());

        tvQuantity.setText(resultArrayList.get(position).getRatingDateTime()+" ago");

    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {
        public OffersViewHolder(ReviewItemBinding binding1) {
            super(binding1.getRoot());
        }
    }

}
