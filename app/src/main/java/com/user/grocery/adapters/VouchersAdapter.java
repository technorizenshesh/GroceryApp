package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.grocery.R;
import com.user.grocery.databinding.VoucherItemBinding;
import com.user.grocery.databinding.VoucherItemBinding;
import com.user.grocery.models.SuccessResGetVoucher;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class VouchersAdapter extends RecyclerView.Adapter<VouchersAdapter.OffersViewHolder>{

     public VoucherItemBinding binding;

     private Context context;

     private ArrayList<SuccessResGetVoucher.Result> giftList ;

     public VouchersAdapter(Context context,ArrayList<SuccessResGetVoucher.Result> giftList)
     {
         this.context = context;
         this.giftList = giftList;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = VoucherItemBinding.inflate(LayoutInflater.from(context));

       return new OffersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

        TextView tvCode,tvPerOff;
        tvCode = holder.itemView.findViewById(R.id.tvCode);
        tvPerOff = holder.itemView.findViewById(R.id.tvtitle);

        tvCode.setText("Use Code: "+giftList.get(position).getName());
        tvPerOff.setText("Special Deal For You "+ giftList.get(position).getDiscount()+"% off");
    }

    @Override
    public int getItemCount() {
        return giftList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {
        public OffersViewHolder(VoucherItemBinding binding1) {
            super(binding1.getRoot());
        }
    }

}
