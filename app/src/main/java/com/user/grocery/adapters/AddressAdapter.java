package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.user.grocery.R;
import com.user.grocery.databinding.AddressItemBinding;
import com.user.grocery.databinding.AddressItemBinding;
import com.user.grocery.databinding.GroceryCategoryItemBinding;
import com.user.grocery.models.SuccessResGetAddress;
import com.user.grocery.utility.AddClickListener;
import com.user.grocery.utility.AddressItemClickListener;
import com.user.grocery.utility.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.OffersViewHolder>{

    public AddressItemBinding binding;
    private LayoutInflater layoutInflater;
    private Context context;

    private ArrayList<SuccessResGetAddress.Address> addressesList;

    private AddClickListener itemClickListener;

    private AddressItemClickListener addressItemClickListener;

    private int selectedItem=-1;

     public AddressAdapter(Context context,ArrayList<SuccessResGetAddress.Address> addressesList,AddClickListener itemClickListener,AddressItemClickListener addressItemClickListener)
     {
         this.context = context;
         this.addressesList = addressesList;
         this.itemClickListener = itemClickListener;
         this.addressItemClickListener = addressItemClickListener;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AddressItemBinding dataBinding= DataBindingUtil.inflate(layoutInflater, R.layout.address_item, parent, false);
        return new AddressAdapter.OffersViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

         holder.addressItemBinding.setAddressItem(addressesList.get(position));
         holder.addressItemBinding.setClickListener(itemClickListener);
         RelativeLayout rlParent = holder.itemView.findViewById(R.id.rlParent);

         if(position == selectedItem)
         {
             final int sdk = android.os.Build.VERSION.SDK_INT;
             if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                 rlParent.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.green_stroke_cornors_5) );
             } else {
                 rlParent.setBackground(ContextCompat.getDrawable(context, R.drawable.green_stroke_cornors_5));
             }
         }else
         {
             final int sdk = android.os.Build.VERSION.SDK_INT;
             if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                 rlParent.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.gray_stroke_cornors_5) );
             } else {
                 rlParent.setBackground(ContextCompat.getDrawable(context, R.drawable.gray_stroke_cornors_5));
             }
         }

        rlParent.setOnClickListener(v ->
                {
                    selectedItem = position;
                    addressItemClickListener.addItemPosition(position);
                    notifyDataSetChanged();
                }
                );

    }

    @Override
    public int getItemCount() {
        return addressesList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {

        private final AddressItemBinding addressItemBinding;

        public OffersViewHolder(AddressItemBinding binding1) {
            super(binding1.getRoot());
            this.addressItemBinding = binding1;
        }
    }

}
