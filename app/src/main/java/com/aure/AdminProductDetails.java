package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aure.UiAdapters.MarketplaceProductDetailAdapter;
import com.aure.UiModels.ListingModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator2;

public class AdminProductDetails extends AppCompatActivity {

    RecyclerView detailRecyclerview;
    MarketplaceProductDetailAdapter detailAdapter;
    ArrayList<String> detailList = new ArrayList<>();
    CircleIndicator2 detailIndicator;
    TextView name,description,price;
    ListingModel listingModel,mListingModel;
    String productId, retailerId;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    MaterialButton promote,delete;
    String ignorePromotion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_details);
        initView();
    }

    private void initView(){
        ignorePromotion = getIntent().getStringExtra("ignore");
        promote = findViewById(R.id.admin_product_sponsor);
        delete = findViewById(R.id.admin_product_delete);
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
        if(mListingModel.getIsSponsored().equalsIgnoreCase("true")){
            promote.setVisibility(View.GONE);
        }

        if((ignorePromotion != null) && (!ignorePromotion.equalsIgnoreCase(""))){
            promote.setVisibility(View.GONE);
        }

        listingModel.setDetailsReadyListener(new ListingModel.DetailsReadyListener() {
            @Override
            public void onDetailsReady(ArrayList<String> imagesList, String phone) {
                nestedScrollView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                LinearLayoutManager detailamanager = new LinearLayoutManager(AdminProductDetails.this, LinearLayoutManager.HORIZONTAL,false);
                detailAdapter = new MarketplaceProductDetailAdapter(AdminProductDetails.this, imagesList);
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
                Toast.makeText(AdminProductDetails.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.ixpecial));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ixpecial));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}