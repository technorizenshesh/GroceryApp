package com.user.grocery.utility;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.user.grocery.R;


public class BottomSheetFragment extends BottomSheetDialogFragment
   {
       private static String price;
       public EditText etPrice;
       public String strPrice = "";


       public BottomSheetFragment()
       {

       }

       private MaterialButton btn;

      /* public static BottomSheetFragment newInstance() {
        BottomSheetFragment fragment = new BottomSheetFragment(price);
        return fragment;
    }

*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);
        dialog.setContentView(view);
        //  dialog.getWindow().setDimAmount(0);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


    }

       @Override public int getTheme() {
           return R.style.AppBottomSheetDialogTheme;
       }

       @Override
       public void onStart() {
           super.onStart();
           if (getDialog() != null && getDialog().getWindow() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               Window window = getDialog().getWindow();
               window.findViewById(com.google.android.material.R.id.container).setFitsSystemWindows(false);
// dark navigation bar icons
               View decorView = window.getDecorView();
               decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
           }
       }

   }