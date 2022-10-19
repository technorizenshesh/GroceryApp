package com.user.grocery.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.user.grocery.HelpAct;
import com.user.grocery.R;
import com.user.grocery.activities.AddLocationAct;
import com.user.grocery.activities.ItemQualityIssue;
import com.user.grocery.activities.ItsSomethingElse;
import com.user.grocery.activities.LoginAct;
import com.user.grocery.activities.MissingOrIncorrectItemAct;
import com.user.grocery.activities.OrderArrievedLateAct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class BottomSheetReturn extends BottomSheetDialogFragment {

    ImageView bottomSheet_cancelId;

    private Context context;

    private ItemClickListener itemClickListener;

    public BottomSheetReturn(Context context,ItemClickListener itemClickListener)
    {
        this.context = context;
         this.itemClickListener = itemClickListener;
    }

    @Override public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setWhiteNavigationBar(@NonNull Dialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            GradientDrawable dimDrawable = new GradientDrawable();
            // ...customize your dim effect here

            GradientDrawable navigationBarDrawable = new GradientDrawable();
            navigationBarDrawable.setShape(GradientDrawable.RECTANGLE);
            navigationBarDrawable.setColor(Color.WHITE);

            Drawable[] layers = {dimDrawable, navigationBarDrawable};

            LayerDrawable windowBackground = new LayerDrawable(layers);
            windowBackground.setLayerInsetTop(1, metrics.heightPixels);

            window.setBackgroundDrawable(windowBackground);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_return, null);
        dialog.setContentView(contentView);

        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setWhiteNavigationBar(dialog);
        }

        TextView tv1 =  dialog.findViewById(R.id.tvOrders);

        TextView tv2 =  dialog.findViewById(R.id.tvItemQuality);

        TextView tv3 =  dialog.findViewById(R.id.tvMissing);

        TextView tv4 = dialog.findViewById(R.id.tvOrderLate);

        TextView tv5 = dialog.findViewById(R.id.tvSomethingsElse);



        tv1.setOnClickListener(v ->
                {
//                    startActivity(new Intent(context, AddLocationAct.class));

                    itemClickListener.imageItemClick(v,tv1.getText().toString(),"");
                }
                );

        tv2.setOnClickListener(v ->
                {
//                    startActivity(new Intent(context, ItemQualityIssue.class));

                    itemClickListener.imageItemClick(v,tv2.getText().toString(),"");

                }
        );

        tv3.setOnClickListener(v ->
                {
//                    startActivity(new Intent(context, MissingOrIncorrectItemAct.class));

                    itemClickListener.imageItemClick(v,tv3.getText().toString(),"");

                }
        );

        tv4.setOnClickListener(v ->
                {
//                    startActivity(new Intent(context, OrderArrievedLateAct.class));

                    itemClickListener.imageItemClick(v,tv4.getText().toString(),"");

                }
        );

        tv5.setOnClickListener(v ->
                {
//                    startActivity(new Intent(context, ItsSomethingElse.class));

                    itemClickListener.imageItemClick(v,tv4.getText().toString(),"");

                }
        );

//        tv6.setOnClickListener(v ->
//                {
//                    startActivity(new Intent(context, HelpAct.class));
//                }
//        );

    }


}