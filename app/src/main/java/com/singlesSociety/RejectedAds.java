package com.singlesSociety;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.singlesSociety.uiAdapters.AdminDetailsAdapter;
import com.singlesSociety.UiModels.ListingModel;

import java.util.ArrayList;


public class RejectedAds extends Fragment {


    View view;
    RecyclerView recyclerView;
    AdminDetailsAdapter viewAllAdapter;
    ProgressBar progressBar;
    TextView nothingToShow;

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
        nothingToShow = view.findViewById(R.id.empty_listing);
        progressBar = view.findViewById(R.id.rejected_listing_progress);
        recyclerView = view.findViewById(R.id.rejected_products_recyclerview);

        ListingModel listingModel = new ListingModel(getContext());
        listingModel.getRejectedListing();
        listingModel.setListingListener(new ListingModel.ListingListener() {
            @Override
            public void onListingReady(ArrayList<ListingModel> modelArrayList) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                nothingToShow.setVisibility(View.GONE);
                LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                viewAllAdapter = new AdminDetailsAdapter(getContext(),modelArrayList);
                recyclerView.setAdapter(viewAllAdapter);
                recyclerView.setLayoutManager(manager);

                viewAllAdapter.setItemClickedListener(new AdminDetailsAdapter.ItemClickedListener() {
                    @Override
                    public void onItemClicked(int position) {
                        Intent intent = new Intent(getContext(), AdminProductDetails.class);
                        intent.putExtra("ignore","ignore");
                        intent.putExtra("info",modelArrayList.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onEmpty() {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                nothingToShow.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                nothingToShow.setVisibility(View.GONE);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });


    }
}