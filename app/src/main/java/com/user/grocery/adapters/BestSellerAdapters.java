package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.user.grocery.R;
import com.user.grocery.databinding.ProductItemBinding;
import com.user.grocery.databinding.ProductItemBinding;
import com.user.grocery.models.Products;
import com.user.grocery.models.Products;
import com.user.grocery.utility.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 28,July,2022
 */
public class BestSellerAdapters extends RecyclerView.Adapter<BestSellerAdapters.ViewHolder> {

    private ArrayList<Products> feedList;
    private LayoutInflater layoutInflater;
    private Context context;
    private ItemClickListener itemClickListener;
    public BestSellerAdapters(Context context, ArrayList<Products> feedList, ItemClickListener itemClickListener) {
        this.context = context;
        this.feedList = feedList;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ProductItemBinding dataBinding= DataBindingUtil.inflate(layoutInflater, R.layout.product_item, parent, false);
        return new ViewHolder(dataBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products feed=feedList.get(position);
        holder.feedDataBinding.setProduct(feedList.get(position));
        holder.feedDataBinding.setClickListener(itemClickListener);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ProductItemBinding feedDataBinding;

        public ViewHolder(final ProductItemBinding feedbind) {
            super(feedbind.getRoot());
            this.feedDataBinding=feedbind;
        }
    }
}
