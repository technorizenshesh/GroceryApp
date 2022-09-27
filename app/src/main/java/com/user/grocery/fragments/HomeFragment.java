package com.user.grocery.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.user.grocery.R;
import com.user.grocery.activities.ProductDetailAct;
import com.user.grocery.activities.ProfileAct;
import com.user.grocery.activities.SearchAct;
import com.user.grocery.adapters.BestSellerAdapters;
import com.user.grocery.adapters.OffersAdapters;
import com.user.grocery.adapters.SliderAdapter;
import com.user.grocery.databinding.FragmentHomeBinding;
import com.user.grocery.models.Products;
import com.user.grocery.models.SuccessResGetBanner;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.ItemClickListener;
import com.user.grocery.viewmodel.BannerViewModel;
import com.user.grocery.viewmodel.ProductsViewModel;
import com.user.grocery.viewmodel.ProfileViewModel;

import java.util.ArrayList;

import static com.user.grocery.retrofit.Constant.showToast;

public class HomeFragment extends Fragment implements ItemClickListener {

   public FragmentHomeBinding binding;

    private BannerViewModel bannerViewModel;

    private ProductsViewModel productsViewModel;

    private BestSellerAdapters bestSellerAdapters;

    private ArrayList<Products> productsArrayList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    private SliderAdapter adapter;

    private ArrayList<SuccessResGetBanner.ProductDatum> bannersList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);

        binding.nss.setNestedScrollingEnabled(false);
        binding.rvOffers.setHasFixedSize(true);
        binding.rvOffers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvOffers.setAdapter(new OffersAdapters(getActivity()));

//        binding.rvBestSelling.setHasFixedSize(true);
//        binding.rvBestSelling.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        binding.rvBestSelling.setAdapter(new BestSellerAdapters(getActivity()));

        binding.etSearch.setOnClickListener(v ->
                {
                    startActivity(new Intent(getActivity(),SearchAct.class));
                }
                );

        adapter = new SliderAdapter(getContext(),bannersList);
        binding.imageSlider.setSliderAdapter(adapter);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. : WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSlider.setScrollTimeInSec(3);
        binding.imageSlider.setAutoCycle(true);
        binding.imageSlider.startAutoCycle();

        bannerViewModel = ViewModelProviders.of(this).get(BannerViewModel.class);
        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);

        getProductsList();
        getBannerList();

        return binding.getRoot();
    }

    public void getBannerList()
    {

        DataManager.getInstance().showProgressMessage(getActivity(), getString(R.string.please_wait));

        bannerViewModel.getUserProfile().observe(getViewLifecycleOwner(), articleResponse -> {
            if (articleResponse != null) {
                DataManager.getInstance().hideProgressMessage();
                bannersList.clear();
                bannersList.addAll(articleResponse.getProductData());
                adapter.notifyDataSetChanged();
            }
            else
            {
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    public void getProductsList()
    {

            DataManager.getInstance().showProgressMessage(getActivity(), getString(R.string.please_wait));

            productsViewModel.getUserProfile().observe(getViewLifecycleOwner(), articleResponse -> {
            DataManager.getInstance().hideProgressMessage();
            if (articleResponse != null) {
                DataManager.getInstance().hideProgressMessage();
                productsArrayList.clear();
                productsArrayList.addAll(articleResponse.getUserData());
                bestSellerAdapters = new BestSellerAdapters(getActivity(),productsArrayList,HomeFragment.this);
                binding.setProductsAdapter(bestSellerAdapters);

            }

        });
    }

    @Override
    public void imageItemClick(View v, String id,String categoryId) {

        startActivity(new Intent(getActivity(), ProductDetailAct.class).putExtra("productId", id).putExtra("categoryId", categoryId));

    }

}