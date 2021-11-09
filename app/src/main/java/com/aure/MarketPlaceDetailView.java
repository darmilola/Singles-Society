package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aure.UiAdapters.MarketplaceViewAllAdapter;
import com.aure.UiModels.ListingModel;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

public class MarketPlaceDetailView extends AppCompatActivity {

    RecyclerView recyclerView;
    MarketplaceViewAllAdapter viewAllAdapter;
    TextView title;
    ProgressBar progressBar;
    String searchQuery, searchType;
    ListingModel listingModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place_detail_view);
        iniView();
    }

    private void iniView(){
        searchQuery = getIntent().getStringExtra("query");
        searchType = getIntent().getStringExtra("type");
        progressBar = findViewById(R.id.detail_view_progress);
        recyclerView = findViewById(R.id.all_products_recycler_view);
        title = findViewById(R.id.marketplace_detaillist_title);
        title.setText(searchQuery);

        listingModel = new ListingModel(searchQuery);
        if(searchType.equalsIgnoreCase("1")){
            listingModel.searchProduct();
        }
        else if(searchType.equalsIgnoreCase("2")){
             listingModel.searchCategory();
        }

        listingModel.setListingListener(new ListingModel.ListingListener() {
            @Override
            public void onListingReady(ArrayList<ListingModel> modelArrayList) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                viewAllAdapter = new MarketplaceViewAllAdapter(MarketPlaceDetailView.this, modelArrayList);
                LinearLayoutManager manager = new LinearLayoutManager(MarketPlaceDetailView.this,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(viewAllAdapter);
            }

            @Override
            public void onEmpty() {

            }

            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });





    }


    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.ixpecial));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ixpecial));
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }


}
