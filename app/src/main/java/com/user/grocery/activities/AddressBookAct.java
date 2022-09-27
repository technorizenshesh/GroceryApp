package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.user.grocery.R;
import com.user.grocery.adapters.AddressAdapter;
import com.user.grocery.adapters.ItemListAdapters;
import com.user.grocery.databinding.ActivityAddressBookBinding;
import com.user.grocery.fragments.ListFragment;
import com.user.grocery.models.SuccessResGetAddress;
import com.user.grocery.retrofit.NetworkAvailablity;
import com.user.grocery.utility.AddClickListener;
import com.user.grocery.utility.AddressItemClickListener;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.ItemClickListener;
import com.user.grocery.utility.SharedPreferenceUtility;
import com.user.grocery.viewmodel.AddressViewModel;

import java.util.ArrayList;

import static com.user.grocery.retrofit.Constant.USER_ID;
import static com.user.grocery.retrofit.Constant.showToast;

public class AddressBookAct extends AppCompatActivity implements AddClickListener, AddressItemClickListener {

    ActivityAddressBookBinding binding;
    private AddressViewModel addressViewModel;
    private ArrayList<SuccessResGetAddress.Address> addressesList = new ArrayList<>();
    private int selectedAddress = -1;
    private String productId="",packagingSize="",strProductPrice="",strProductName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_address_book);
       binding.header.imgHeader.setOnClickListener(v -> finish());
       binding.header.tvtitle.setText(getString(R.string.address));
       addressViewModel = ViewModelProviders.of(this).get(AddressViewModel.class);
        String passedArg = getIntent().getExtras().getString("arg");
        if(passedArg.equalsIgnoreCase("account"))
        {
            binding.btnNext.setVisibility(View.GONE);
            binding.btnSignup.setVisibility(View.VISIBLE);
        } else
        {
            productId = getIntent().getExtras().getString("productId");
            packagingSize = getIntent().getExtras().getString("packagingSize");
            strProductName = getIntent().getExtras().getString("productName");
            strProductPrice = getIntent().getExtras().getString("productPrice");
            binding.btnNext.setVisibility(View.VISIBLE);
            binding.btnSignup.setVisibility(View.VISIBLE);
        }
        binding.btnSignup.setOnClickListener(v ->
                {
                    startActivity(new Intent(AddressBookAct.this,AddLocationAct.class).putExtra("from","book"));
                }
                );
        binding.btnNext.setOnClickListener(v ->
                {
                    if(selectedAddress!=-1)
                    {
                        Intent intent = new Intent(AddressBookAct.this, OrderDetailAct.class);
                        intent.putExtra("arg","product");
                        intent.putExtra("productId",productId);
                        intent.putExtra("productName",strProductName);
                        intent.putExtra("productPrice",strProductPrice);
                        intent.putExtra("packagingSize",packagingSize);
                        intent.putExtra("addressId",addressesList.get(selectedAddress).getId());
                        intent.putExtra("address",addressesList.get(selectedAddress).getAddress());
                        // getText() SHOULD NOT be static!!!
                        startActivity(intent);
                    }
                    else
                    {
                        showToast(AddressBookAct.this,"Please select a address.");
                    }
                }
                );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkAvailablity.checkNetworkStatus(this)) {
            selectedAddress = -1;
            getAddress();
        } else {
            Toast.makeText(this, getResources().getString(R.string.msg_noInternet), Toast.LENGTH_SHORT).show();
        }
    }

    public void getAddress()
    {
        String userId = SharedPreferenceUtility.getInstance(AddressBookAct.this).getString(USER_ID);
        DataManager.getInstance().showProgressMessage(AddressBookAct.this, getString(R.string.please_wait));
        addressViewModel.getUserProfile(userId).observe(AddressBookAct.this, articleResponse -> {
           DataManager.getInstance().hideProgressMessage();
            if (articleResponse != null) {
                int status = articleResponse.getSuccess();
                String message = articleResponse.getMessage();
                DataManager.getInstance().hideProgressMessage();
                if(status==1)
                {
                    addressesList.clear();
                    addressesList.addAll(articleResponse.getAddressList());
                    AddressAdapter itemListAdapters = new AddressAdapter(AddressBookAct.this,addressesList, AddressBookAct.this,AddressBookAct.this);
                    binding.setAddressAdapter(itemListAdapters);
                }
                else
                {
                    Toast.makeText(AddressBookAct.this, ""+message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void addressItemClick(View v, SuccessResGetAddress.Address address, String category) {
        startActivity(new Intent(AddressBookAct.this,AddLocationAct.class).putExtra("from","edit")
                .putExtra("addressId",address.getId())
                .putExtra("address",address.getAddress())
                .putExtra("lat",address.getLat())
                .putExtra("lng",address.getLong())
        );
    }

    @Override
    public void addItemPosition(int position) {
        selectedAddress = position;
    }
}