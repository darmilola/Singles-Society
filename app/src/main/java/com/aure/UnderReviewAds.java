package com.aure;


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

import com.aure.UiAdapters.AdminDetailsAdapter;
import com.aure.UiModels.ListingModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UnderReviewAds extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<String> deatailList = new ArrayList<>();
    AdminDetailsAdapter viewAllAdapter;
    ProgressBar progressBar;
    TextView nothingToShow;

    public UnderReviewAds() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_under_review_ads, container, false);
        initView();
        return  view;
    }

    private void initView(){
        nothingToShow = view.findViewById(R.id.empty_listing);
        recyclerView = view.findViewById(R.id.under_review_recyclerview);
        progressBar = view.findViewById(R.id.pending_progressbar);
        ListingModel listingModel = new ListingModel(getContext());
        listingModel.getPendingListing();
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
                nothingToShow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onError(String message) {
                 progressBar.setVisibility(View.GONE);
                nothingToShow.setVisibility(View.GONE);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
