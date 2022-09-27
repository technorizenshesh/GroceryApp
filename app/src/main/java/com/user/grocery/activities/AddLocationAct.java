package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.databinding.ActivityAddLocationBinding;
import com.user.grocery.models.SuccessResUpdateAddress;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.Constant;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.SharedPreferenceUtility;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.user.grocery.retrofit.Constant.USER_ID;
import static com.user.grocery.retrofit.Constant.showToast;

public class AddLocationAct extends AppCompatActivity {

    ActivityAddLocationBinding binding;
    private static int AUTOCOMPLETE_REQUEST_CODE = 101;

    private GroceryInterface apiInterface;

    private String strAddress="",strLat="",strLng="",strAddressId="";

    String from = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_location);

        binding.header.imgHeader.setOnClickListener(v -> finish());
        binding.header.tvtitle.setText(getString(R.string.address));

        apiInterface = ApiClient.getClient().create(GroceryInterface.class);

        from = getIntent().getStringExtra("from");

        Places.initialize(AddLocationAct.this, "AIzaSyCFB3_0jJQm20H2cPiVfANMjDhAmxlpUb8");
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(AddLocationAct.this);

        binding.etLocation.setOnClickListener(v ->
                {
                    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.ADDRESS,Place.Field.LAT_LNG);
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                            .build(AddLocationAct.this);
                    startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
                }
        );

        if(from.equalsIgnoreCase("login"))
        {
            binding.btnContinue.setVisibility(View.VISIBLE);
            binding.btnAdd.setVisibility(View.GONE);
            binding.btnEdit.setVisibility(View.GONE);
            binding.forogtPass.setVisibility(View.GONE);
        } else  if(from.equalsIgnoreCase("edit"))
        {

            strAddressId = getIntent().getStringExtra("addressId");
            strAddress = getIntent().getStringExtra("address");
            strLat = getIntent().getStringExtra("lat");
            strLng = getIntent().getStringExtra("lng");
            binding.forogtPass.setVisibility(View.VISIBLE);
            binding.btnContinue.setVisibility(View.GONE);
            binding.btnAdd.setVisibility(View.GONE);
            binding.btnEdit.setVisibility(View.VISIBLE);
            binding.etLocation.setText(strAddress);
        }
        else
        {
            binding.btnContinue.setVisibility(View.GONE);
            binding.btnAdd.setVisibility(View.VISIBLE);
            binding.btnEdit.setVisibility(View.GONE);
            binding.forogtPass.setVisibility(View.GONE);
        }

        binding.btnContinue.setOnClickListener(v ->
                {
                    updateLocation();
                }
                );

        binding.btnAdd.setOnClickListener(v ->
                {
                    addLocation();
                }
                );

        binding.btnEdit.setOnClickListener(v ->
                {
                    editLocation();
                }
                );

        binding.forogtPass.setOnClickListener(v ->
                {
                    deleteLocation();
                }
                );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());

                strAddress = place.getAddress();
                LatLng latLng = place.getLatLng();

                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                strLat = Double.toString(latitude);
                strLng = Double.toString(longitude);

                String address = place.getAddress();

                strAddress = address;

                binding.etLocation.setText(address);

                binding.etLocation.post(new Runnable(){
                    @Override
                    public void run() {
                        binding.etLocation.setText(address);
                    }
                });

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }

    }

    public void updateLocation()
    {
        String strUserId = SharedPreferenceUtility.getInstance(AddLocationAct.this).getString(USER_ID);
        DataManager.getInstance().showProgressMessage(AddLocationAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("user_id",strUserId);
        map.put("address",strAddress);
        map.put("lat",strLat);
        map.put("long",strLng);

        Call<SuccessResUpdateAddress> call = apiInterface.updateAddress(map);

        call.enqueue(new Callback<SuccessResUpdateAddress>() {
            @Override
            public void onResponse(Call<SuccessResUpdateAddress> call, Response<SuccessResUpdateAddress> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResUpdateAddress data = response.body();

                    if (data.success == 1 ) {
                        showToast(AddLocationAct.this, data.message);
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        SharedPreferenceUtility.getInstance(getApplication()).putBoolean(Constant.IS_USER_LOGGED_IN, true);
                        startActivity(new Intent(AddLocationAct.this,HomeAct.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                    } else if (data.success == 0) {
                        showToast(AddLocationAct.this, data.message);
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

    public void addLocation()
    {
        String strUserId = SharedPreferenceUtility.getInstance(AddLocationAct.this).getString(USER_ID);
        DataManager.getInstance().showProgressMessage(AddLocationAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("user_id",strUserId);
        map.put("address",strAddress);
        map.put("lat",strLat);
        map.put("long",strLng);

        Call<SuccessResUpdateAddress> call = apiInterface.addAddress(map);

        call.enqueue(new Callback<SuccessResUpdateAddress>() {
            @Override
            public void onResponse(Call<SuccessResUpdateAddress> call, Response<SuccessResUpdateAddress> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResUpdateAddress data = response.body();

                    if (data.success == 1 ) {
                        showToast(AddLocationAct.this, data.message);
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        finish();
                    } else if (data.success == 0) {
                        showToast(AddLocationAct.this, data.message);
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

    public void editLocation()
    {
        String strUserId = SharedPreferenceUtility.getInstance(AddLocationAct.this).getString(USER_ID);
        DataManager.getInstance().showProgressMessage(AddLocationAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("user_id",strUserId);
        map.put("address",strAddress);
        map.put("address_id",strAddressId);
        map.put("lat",strLat);
        map.put("long",strLng);

        Call<SuccessResUpdateAddress> call = apiInterface.editAddress(map);

        call.enqueue(new Callback<SuccessResUpdateAddress>() {
            @Override
            public void onResponse(Call<SuccessResUpdateAddress> call, Response<SuccessResUpdateAddress> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResUpdateAddress data = response.body();

                    if (data.success == 1 ) {
                        showToast(AddLocationAct.this, data.message);
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        finish();
                    } else if (data.success == 0) {
                        showToast(AddLocationAct.this, data.message);
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


    public void deleteLocation()
    {
        DataManager.getInstance().showProgressMessage(AddLocationAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("address_id",strAddressId);

        Call<SuccessResUpdateAddress> call = apiInterface.deleteAddress(map);

        call.enqueue(new Callback<SuccessResUpdateAddress>() {
            @Override
            public void onResponse(Call<SuccessResUpdateAddress> call, Response<SuccessResUpdateAddress> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResUpdateAddress data = response.body();

                    if (data.success == 1 ) {
                        showToast(AddLocationAct.this, data.message);
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);

                        finish();

                    } else if (data.success == 0) {
                        showToast(AddLocationAct.this, data.message);
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