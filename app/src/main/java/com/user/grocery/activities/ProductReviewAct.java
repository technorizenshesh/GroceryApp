package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.adapters.CartAdapters;
import com.user.grocery.adapters.ProductAdapter;
import com.user.grocery.adapters.ReviewAdapter;
import com.user.grocery.databinding.ActivityProductReviewBinding;
import com.user.grocery.fragments.CartFragment;
import com.user.grocery.models.SuccessResGetMyOrders;
import com.user.grocery.models.SuccessResGetReviews;
import com.user.grocery.models.SuccessResUpdateAddress;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.retrofit.NetworkAvailablity;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.PackagingClickListener;
import com.user.grocery.utility.SharedPreferenceUtility;
import com.user.grocery.viewmodel.GetMyOrdersViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.USER_ID;
import static com.user.grocery.retrofit.Constant.showToast;

public class ProductReviewAct extends AppCompatActivity implements PackagingClickListener {

    ActivityProductReviewBinding binding;

    private GetMyOrdersViewModel myOrdersViewModel;

    private ArrayList<SuccessResGetMyOrders.Result> myOrdersList = new ArrayList<>();

    private String myRating="",myReview="";
    private ArrayList<SuccessResGetReviews.Result> resultArrayList = new ArrayList<>();
    private GroceryInterface apiInterface;

    private int selectPosition = 0;

    private Dialog dialog;

    private String productId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_product_review);

        apiInterface = ApiClient.getClient().create(GroceryInterface.class);

        binding.header.imgHeader.setOnClickListener(v -> finish());
        binding.header.tvtitle.setText(getString(R.string.product_reviews));

        myOrdersViewModel = ViewModelProviders.of(ProductReviewAct.this).get(GetMyOrdersViewModel.class);

        if (NetworkAvailablity.checkNetworkStatus(this)) {
            getList();
        } else {
            Toast.makeText(this, getResources().getString(R.string.msg_noInternet), Toast.LENGTH_SHORT).show();
        }



        binding.btnLogin.setOnClickListener(v ->
                {
                    showImageSelection();
                }
                );

    }

    private void getList()
    {

        DataManager.getInstance().showProgressMessage(ProductReviewAct.this, getString(R.string.please_wait));
        myOrdersViewModel.getUserProfile().observe(this, articleResponse -> {

            DataManager.getInstance().hideProgressMessage();

            if (articleResponse != null) {
                int status = articleResponse.getSuccess();
                String message = articleResponse.getMessage();
                DataManager.getInstance().hideProgressMessage();
                if(status==1)
                {
                    myOrdersList.clear();
                    myOrdersList.addAll(articleResponse.getResult());
                    binding.rvItems.setHasFixedSize(true);
                    binding.rvItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                    binding.rvItems.setAdapter(new ProductAdapter(ProductReviewAct.this,myOrdersList,ProductReviewAct.this));
                }
                else
                {
                    binding.btnLogin.setVisibility(View.GONE);
                    Toast.makeText(ProductReviewAct.this, ""+message, Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    @Override
    public void packageItemClick(View v, int position, String category) {

        productId = myOrdersList.get(position).getBookingProductId();

        getRating();

    }

    public void showImageSelection() {

       dialog = new Dialog(ProductReviewAct.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Widget_Material_ListPopupWindow;
        dialog.setContentView(R.layout.dialog_show_image_selection);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        AppCompatButton btnSubmit = dialog.findViewById(R.id.btnLogin);
        EditText etReview = dialog.findViewById(R.id.etReview);
        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);

        btnSubmit.setOnClickListener(v ->
                {
                    String rating = String.valueOf(ratingBar.getRating());

                    myRating = rating;
                    myReview = etReview.getText().toString();

                    if(rating.equalsIgnoreCase(""))
                    {
                        Toast.makeText(ProductReviewAct.this,"Please select Star.",Toast.LENGTH_SHORT).show();
                    } else if(etReview.getText().toString().equalsIgnoreCase(""))
                    {
                        Toast.makeText(ProductReviewAct.this,"Please enter review.",Toast.LENGTH_SHORT).show();
                    } else
                    {
                        addRating();
                    }
                }
                );

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

public void addRating()
{

    String userId = SharedPreferenceUtility.getInstance(ProductReviewAct.this).getString(USER_ID);

    DataManager.getInstance().showProgressMessage(ProductReviewAct.this, getString(R.string.please_wait));
    Map<String,String> map = new HashMap<>();
    map.put("rating_product_id",productId);
    map.put("rating_points",myRating);
    map.put("rating_user_id",userId);
    map.put("rating_comment",myReview);
    Call<SuccessResUpdateAddress> call = apiInterface.addRating(map);
    call.enqueue(new Callback<SuccessResUpdateAddress>() {
        @Override
        public void onResponse(Call<SuccessResUpdateAddress> call, Response<SuccessResUpdateAddress> response) {

            DataManager.getInstance().hideProgressMessage();
            try {
                SuccessResUpdateAddress data = response.body();
                if (data.success==1) {
                    showToast(ProductReviewAct.this, data.message);
                    dialog.dismiss();
                } else if (data.success == 0) {
                    showToast(ProductReviewAct.this, data.message);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<SuccessResUpdateAddress> call, Throwable t) {
            call.cancel();
            DataManager.getInstance().hideProgressMessage();
        }
    });
}

    public void getRating()
    {

        DataManager.getInstance().showProgressMessage(ProductReviewAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("rating_product_id",productId);
        Call<SuccessResGetReviews> call = apiInterface.getRating(map);
        call.enqueue(new Callback<SuccessResGetReviews>() {
            @Override
            public void onResponse(Call<SuccessResGetReviews> call, Response<SuccessResGetReviews> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResGetReviews data = response.body();
                    if (data.success==1) {
                        showToast(ProductReviewAct.this, data.message);


                        resultArrayList.clear();
                        resultArrayList.addAll(data.getResult());

                        binding.rvReview.setHasFixedSize(true);
                        binding.rvReview.setLayoutManager(new LinearLayoutManager(ProductReviewAct.this));
                        binding.rvReview.setAdapter(new ReviewAdapter(ProductReviewAct.this,resultArrayList));


                    } else if (data.success == 0) {
                        showToast(ProductReviewAct.this, data.message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResGetReviews> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }




}