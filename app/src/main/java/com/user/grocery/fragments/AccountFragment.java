package com.user.grocery.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.PerformanceHintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.user.grocery.R;
import com.user.grocery.activities.AddressBookAct;
import com.user.grocery.activities.LoginAct;
import com.user.grocery.activities.One2OneChatAct;
import com.user.grocery.activities.OrderActivity;
import com.user.grocery.activities.ProductReviewAct;

import com.user.grocery.activities.ProfileAct;
import com.user.grocery.databinding.FragmentAccountBinding;
import com.user.grocery.retrofit.Constant;
import com.user.grocery.utility.BottomSheetReturn;
import com.user.grocery.utility.SharedPreferenceUtility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;

    private BottomSheetReturn bottomSheetFragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_account, container, false);

        init();

        return binding.getRoot();
    }

    private void init()
    {
        binding.rlOrders.setOnClickListener(v ->
        {
            startActivity(new Intent(getActivity(), OrderActivity.class));
        });

//        binding.rlReturn.setOnClickListener(v ->
//                {
//                    bottomSheetFragment= new BottomSheetReturn(getActivity());
//
//                    bottomSheetFragment.show(getActivity().getSupportFragmentManager(),"ModalBottomSheet");
//                }
//                );

        binding.rlPersonalDetail.setOnClickListener(v ->
        {
            startActivity(new Intent(getActivity(), ProfileAct.class));
        });

        binding.rlProductReview.setOnClickListener(v ->
        {
            startActivity(new Intent(getActivity(), ProductReviewAct.class));
        });

        binding.rlChat.setOnClickListener(v ->
        {
            startActivity(new Intent(getActivity(), One2OneChatAct.class));
        });

        binding.tvAddressBook.setOnClickListener(v ->
        {
            Intent intent = new Intent(getActivity(), AddressBookAct.class);
            intent.putExtra("arg","account"); // getText() SHOULD NOT be static!!!
            startActivity(intent);
        });

        binding.btnLogout.setOnClickListener(v ->
                {
                    SharedPreferenceUtility.getInstance(getActivity().getApplication()).putBoolean(Constant.IS_USER_LOGGED_IN, false);
                    Intent intent = new Intent(getActivity(), LoginAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                );

    }

}