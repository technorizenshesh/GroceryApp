package com.user.grocery.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.user.grocery.R;
import com.user.grocery.activities.ProductDetailAct;
import com.user.grocery.adapters.BestSellerAdapters;
import com.user.grocery.adapters.CartAdapters;
import com.user.grocery.adapters.ItemListAdapters;
import com.user.grocery.databinding.FragmentListBinding;
import com.user.grocery.models.FavProducts;
import com.user.grocery.models.SuccessResGetFavProduct;
import com.user.grocery.models.SuccessResSignup;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.ItemClickListener;
import com.user.grocery.utility.SharedPreferenceUtility;
import com.user.grocery.viewmodel.FavouriteProductsViewModel;
import com.user.grocery.viewmodel.UpdateFavViewModel;

import java.util.ArrayList;

import static com.user.grocery.retrofit.Constant.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements ItemClickListener {

    FragmentListBinding binding;

    private FavouriteProductsViewModel favouriteProductsViewModel;
    private SuccessResSignup data;
    private ArrayList<FavProducts> favProductArrayList = new ArrayList<>();
    private UpdateFavViewModel updateFavViewModel;
    private ItemListAdapters itemListAdapters;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list, container, false);
        favouriteProductsViewModel = ViewModelProviders.of(this).get(FavouriteProductsViewModel.class);
        updateFavViewModel = ViewModelProviders.of(this).get(UpdateFavViewModel.class);

        getList();

        return binding.getRoot();
    }

    private void getList()
    {
        String userId = SharedPreferenceUtility.getInstance(getActivity()).getString(USER_ID);

        DataManager.getInstance().showProgressMessage(getActivity(), getString(R.string.please_wait));
        favouriteProductsViewModel.getUserProfile(userId).observe(getViewLifecycleOwner(), articleResponse -> {

            DataManager.getInstance().hideProgressMessage();

            if (articleResponse != null) {
                int status = articleResponse.getSuccess();
                String message = articleResponse.getMessage();
                DataManager.getInstance().hideProgressMessage();
                if(status==1)
                {
                    favProductArrayList.clear();
                    favProductArrayList.addAll(articleResponse.getProductData());
                    itemListAdapters = new ItemListAdapters(getActivity(),favProductArrayList,ListFragment.this);
                    binding.setListAdapter(itemListAdapters);
                }
                else
                {
                    Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    @Override
    public void imageItemClick(View v, String id, String category) {

        updateFav(id);

    }

    public void updateFav(String productId)
    {

        String userId = SharedPreferenceUtility.getInstance(getActivity()).getString(USER_ID);

        DataManager.getInstance().showProgressMessage(getActivity(), getString(R.string.please_wait));

        updateFavViewModel.getProductDetailsLiveData(userId,productId,getActivity()).observe(this, articleResponse -> {

            DataManager.getInstance().hideProgressMessage();

            if (articleResponse != null) {

                data = articleResponse;

                if (data.success==1) {

                    getList();

                } else if (data.success==0) {
                    getList();
                }

            }

        });

    }


}