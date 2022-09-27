package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.user.grocery.R;
import com.user.grocery.databinding.PackagingSizeItemBinding;
import com.user.grocery.databinding.PackagingSizeItemBinding;
import com.user.grocery.models.ProductDetails;
import com.user.grocery.utility.PackagingClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class PackagingSizeAdapter extends RecyclerView.Adapter<PackagingSizeAdapter.OffersViewHolder>{

    public PackagingSizeItemBinding binding;

    private Context context;

    private List<ProductDetails.ProductPackaging> productPackagings;

    private int selectPosition = 0;

    private PackagingClickListener packagingClickListener;

    public PackagingSizeAdapter(Context context,List<ProductDetails.ProductPackaging> productPackagings,PackagingClickListener packagingClickListener)
     {
         this.context = context;
         this.productPackagings = productPackagings;
         this.packagingClickListener = packagingClickListener;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       binding = PackagingSizeItemBinding.inflate(LayoutInflater.from(context));
       return new OffersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

        LinearLayout llParent = holder.itemView.findViewById(R.id.llParent);

        TextView tvPrice = holder.itemView.findViewById(R.id.tvPrice);
        TextView tvPacking = holder.itemView.findViewById(R.id.tvPacking);

        tvPrice.setText("$ "+productPackagings.get(position).getPrice());
        tvPacking.setText(productPackagings.get(position).getPackaging());

        if(position==selectPosition)
        {
            llParent.setBackground(ContextCompat.getDrawable(context, R.drawable.green_stroke_cornors_5) );
        } else {
            llParent.setBackground(ContextCompat.getDrawable(context, R.drawable.white_stroke_cornors_5));
        }

        llParent.setOnClickListener(v ->
                {
                    selectPosition = position;
                    notifyDataSetChanged();
                    packagingClickListener.packageItemClick(v,position,"");
                }
                );

    }

    @Override
    public int getItemCount() {
        return productPackagings.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {
        public OffersViewHolder(PackagingSizeItemBinding binding1) {
            super(binding1.getRoot());
        }
    }

}
