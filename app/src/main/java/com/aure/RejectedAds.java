package com.aure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aure.UiAdapters.MarketplaceViewAllAdapter;
import com.aure.UiModels.ListingModel;

import java.util.ArrayList;


public class RejectedAds extends Fragment {


    View view;
    RecyclerView recyclerView;
    MarketplaceViewAllAdapter viewAllAdapter;
    ProgressBar progressBar;

    public RejectedAds() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_rejected_ads, container, false);
        initView();
        return view;
    }

    private void initView(){
        progressBar = view.findViewById(R.id.rejected_listing_progress);
        recyclerView = view.findViewById(R.id.rejected_products_recyclerview);

        ListingModel listingModel = new ListingModel(getContext());
        listingModel.getRejectedListing();
        listingModel.setListingListener(new ListingModel.ListingListener() {
            @Override
            public void onListingReady(ArrayList<ListingModel> modelArrayList) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                viewAllAdapter = new MarketplaceViewAllAdapter(getContext(),modelArrayList);
                recyclerView.setAdapter(viewAllAdapter);
                recyclerView.setLayoutManager(manager);
            }

            @Override
            public void onEmpty() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });


    }
}