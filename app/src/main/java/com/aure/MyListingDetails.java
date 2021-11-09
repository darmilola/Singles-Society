package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aure.UiAdapters.MarketplaceProductDetailAdapter;
import com.aure.UiModels.ListingModel;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator2;

public class MyListingDetails extends AppCompatActivity {


    RecyclerView detailRecyclerview;
    MarketplaceProductDetailAdapter detailAdapter;
    ArrayList<String> detailList = new ArrayList<>();
    CircleIndicator2 detailIndicator;
    TextView name,description,price;
    ListingModel listingModel,mListingModel;
    String productId, retailerId;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_listing_details);
        initView();
    }

    private void initView(){
        progressBar = findViewById(R.id.user_detail_progressbar);
        nestedScrollView = findViewById(R.id.user_details_scroller);
        mListingModel = getIntent().getParcelableExtra("info");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        retailerId = preferences.getString("retailerId","");
        detailRecyclerview = findViewById(R.id.detail_recyclerview);
        name = findViewById(R.id.listing_detail_name);
        description = findViewById(R.id.listing_detail_description);
        price = findViewById(R.id.listing_detail_price);
        detailIndicator = findViewById(R.id.detail_indicator);
        listingModel = new ListingModel(mListingModel.getProductId(),retailerId);
        listingModel.getProductDetail();
        name.setText(mListingModel.getName());
        description.setText(mListingModel.getDescription());
        price.setText(mListingModel.getPrice());

        listingModel.setDetailsReadyListener(new ListingModel.DetailsReadyListener() {
            @Override
            public void onDetailsReady(ArrayList<String> imagesList, String phone) {
                nestedScrollView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                LinearLayoutManager detailamanager = new LinearLayoutManager(MyListingDetails.this, LinearLayoutManager.HORIZONTAL,false);
                detailAdapter = new MarketplaceProductDetailAdapter(MyListingDetails.this, imagesList);
                detailRecyclerview.setAdapter(detailAdapter);
                detailRecyclerview.setLayoutManager(detailamanager);
                PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
                pagerSnapHelper.attachToRecyclerView(detailRecyclerview);
                detailIndicator.attachToRecyclerView(detailRecyclerview, pagerSnapHelper);
                detailAdapter.registerAdapterDataObserver(detailIndicator.getAdapterDataObserver());

            }

            @Override
            public void onError(String message) {
                nestedScrollView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MyListingDetails.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}