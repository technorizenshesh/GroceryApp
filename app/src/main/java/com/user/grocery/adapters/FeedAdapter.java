package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.user.grocery.R;
import com.user.grocery.databinding.GroceryCategoryItemBinding;
import com.user.grocery.models.Category;
import com.user.grocery.models.SuccessResGetCategories;
import com.user.grocery.utility.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Ravindra Birla on 28,July,2022
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private ArrayList<Category> feedList;
    private LayoutInflater layoutInflater;

    private Context context;

    private ItemClickListener itemClickListener;

    public FeedAdapter(Context context,ArrayList<Category> feedList,ItemClickListener itemClickListener) {
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
        GroceryCategoryItemBinding dataBinding= DataBindingUtil.inflate(layoutInflater, R.layout.grocery_category_item, parent, false);
        return new ViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category feed=feedList.get(position);
        holder.feedDataBinding.setCategoryItem(feedList.get(position));
        holder.feedDataBinding.setClickListener(itemClickListener);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final GroceryCategoryItemBinding feedDataBinding;

        public ViewHolder(final GroceryCategoryItemBinding feedbind) {
            super(feedbind.getRoot());
            this.feedDataBinding=feedbind;
        }
    }
}
