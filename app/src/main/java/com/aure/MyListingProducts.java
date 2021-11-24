package com.aure;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aure.UiAdapters.MarketplaceViewAllAdapter;
import com.aure.UiModels.ListingModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyListingProducts extends Fragment {


    View view;
    RecyclerView recyclerView;
    ArrayList<String> deatailList = new ArrayList<>();
    MarketplaceViewAllAdapter viewAllAdapter;
    MaterialButton addProduct;
    String mPhone;
    ProgressBar progressBar;
    NestedScrollView rootView;
    public MyListingProducts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_listing_products, container, false);

        initView();
        return view;
    }

    private void initView(){
        rootView = view.findViewById(R.id.my_products_root_view);
        progressBar = view.findViewById(R.id.my_products_laoding);
        recyclerView = view.findViewById(R.id.listings_myproducts_recyclerview);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mPhone = preferences.getString("phonenumber","");
        addProduct = view.findViewById(R.id.dashboard_add_product);


        ListingModel listingModel = new ListingModel(getContext());
        listingModel.getAllListing();
        listingModel.setListingListener(new ListingModel.ListingListener() {
            @Override
            public void onListingReady(ArrayList<ListingModel> modelArrayList) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                rootView.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                viewAllAdapter = new MarketplaceViewAllAdapter(getContext(),modelArrayList);
                recyclerView.setAdapter(viewAllAdapter);
                recyclerView.setLayoutManager(manager);
            }

            @Override
            public void onEmpty() {
                progressBar.setVisibility(View.GONE);
                rootView.setVisibility(View.GONE);
            }

            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                rootView.setVisibility(View.GONE);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPhone.equalsIgnoreCase("null")) {
                    Toast.makeText(getContext(), "Provide contact phone", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getContext(), AddListing.class));
                }
            }
        });



    }


}
