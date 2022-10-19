package com.user.grocery.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.activities.AddLocationAct;
import com.user.grocery.adapters.CartAdapters;
import com.user.grocery.adapters.ItemListAdapters;
import com.user.grocery.databinding.FragmentCartBinding;
import com.user.grocery.models.SuccessResGetMyOrders;
import com.user.grocery.models.SuccessResUpdateAddress;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.ItemClickListener;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements ItemClickListener {

    FragmentCartBinding binding;

    private GetMyOrdersViewModel myOrdersViewModel;

    private ArrayList<SuccessResGetMyOrders.Result> myOrdersList = new ArrayList<>();

    private GroceryInterface apiInterface;

    private CartAdapters cartAdapters;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false);
        myOrdersViewModel = ViewModelProviders.of(getActivity()).get(GetMyOrdersViewModel.class);
        apiInterface = ApiClient.getClient().create(GroceryInterface.class);
        getList();
        return binding.getRoot();
    }

    private void getList()
    {

        DataManager.getInstance().showProgressMessage(getActivity(), getString(R.string.please_wait));
        myOrdersViewModel.getUserProfile().observe(getViewLifecycleOwner(), articleResponse -> {

            DataManager.getInstance().hideProgressMessage();

            if (articleResponse != null) {
                int status = articleResponse.getSuccess();
                String message = articleResponse.getMessage();
                DataManager.getInstance().hideProgressMessage();
                if(status==1)
                {
                    myOrdersList.clear();
                    myOrdersList.addAll(articleResponse.getResult());
                    cartAdapters = new CartAdapters(getActivity(),myOrdersList,CartFragment.this);
                    binding.setCartAdapter(cartAdapters);
                }
                else
                {
                    Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void imageItemClick(View v, String position, String category) {

        Toast.makeText(getActivity(), "Item Click : Hurry", Toast.LENGTH_SHORT).show();

        new AlertDialog.Builder(getActivity())
                .setTitle("Reorder")
                .setMessage("Are you sure you want to reorder this product?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        reOrder(position);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void reOrder(String orderId)
    {
        DataManager.getInstance().showProgressMessage(getActivity(), getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("booking_id",orderId);

        Call<SuccessResUpdateAddress> call = apiInterface.reorderProduct(map);

        call.enqueue(new Callback<SuccessResUpdateAddress>() {
            @Override
            public void onResponse(Call<SuccessResUpdateAddress> call, Response<SuccessResUpdateAddress> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResUpdateAddress data = response.body();
                    if (data.success == 1 ) {
                        showToast(getActivity(), data.message);
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                    } else if (data.success == 0) {
                        showToast(getActivity(), data.message);
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
}