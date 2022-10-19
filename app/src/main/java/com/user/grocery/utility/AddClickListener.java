package com.user.grocery.utility;

import android.view.View;

import com.user.grocery.models.SuccessResGetAddress;

/**
 * Created by Ravindra Birla on 29,July,2022
 */

 public interface AddClickListener {
     public void addressItemClick(View v, SuccessResGetAddress.Address address, String category);
}
