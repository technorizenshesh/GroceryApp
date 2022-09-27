package com.user.grocery.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.grocery.R;
import com.user.grocery.activities.OrderStatusAct;
import com.user.grocery.databinding.GroceryCategoryItemBinding;
import com.user.grocery.databinding.GroceryCategoryItemBinding;

/**
 * Created by Ravindra Birla on 01,July,2022
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.OffersViewHolder>{


    public GroceryCategoryItemBinding binding;

     private Context context;

     public CategoryAdapter(Context context)
     {
         this.context = context;
     }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = GroceryCategoryItemBinding.inflate(LayoutInflater.from(context));

       return new OffersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

        RelativeLayout rlCategory = holder.itemView.findViewById(R.id.rlCategory);


if(position==1)
{
    rlCategory.setBackgroundColor(context.getResources().getColor(R.color.pink));
} else if (position==2)
{
    rlCategory.setBackgroundColor(context.getResources().getColor(R.color.darkvoilet));
}else if (position==2)
{
    rlCategory.setBackgroundColor(context.getResources().getColor(R.color.red));
}else if (position==3)
{
    rlCategory.setBackgroundColor(context.getResources().getColor(R.color.red));
}else if (position==4)
{
    rlCategory.setBackgroundColor(context.getResources().getColor(R.color.voilet));
}else if (position==5)
{
    rlCategory.setBackgroundColor(context.getResources().getColor(R.color.yellow));
}



        TextView tvTitle = holder.itemView.findViewById(R.id.tvtitle);

        tvTitle.setOnClickListener(v ->
                {
                    context.startActivity(new Intent(context, OrderStatusAct.class));
                }
                );

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder
    {

        public OffersViewHolder(GroceryCategoryItemBinding binding1) {
            super(binding1.getRoot());
        }
    }

}
