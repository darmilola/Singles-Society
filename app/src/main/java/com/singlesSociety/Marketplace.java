package com.singlesSociety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import me.relex.circleindicator.CircleIndicator2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.singlesSociety.uiAdapters.MarketplaceNewProductAdapter;
import com.singlesSociety.UiModels.ListingModel;

import java.util.ArrayList;

public class Marketplace extends AppCompatActivity {

    RecyclerView bestSellerRecyclerview,newProductRecyclerview;
    TextView viewAllNewProduct;
    MarketplaceNewProductAdapter marketplaceNewProductAdapter;
    MarketplaceNewProductAdapter marketplaceBestSellerAdapter;
    ArrayList<String> bestSellers,categorys,newProducts;
    CircleIndicator2 bestSellerIndicator;
    EditText searchBar;
    ListingModel listingModel;
    ProgressBar loadingBar;
    NestedScrollView root;
    String nextPageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);
        initView();
    }

    private void initView(){
        searchBar = findViewById(R.id.marketplace_search_bar);
        loadingBar = findViewById(R.id.marketplace_progressbar);
        root = findViewById(R.id.marketplace_root);
        bestSellerRecyclerview = findViewById(R.id.marketplace_best_seller_recylerview);
        newProductRecyclerview = findViewById(R.id.market_place_newproduct_recyclerview);
        viewAllNewProduct = findViewById(R.id.market_place_view_all);
        bestSellerIndicator = findViewById(R.id.bestseller_indicator);
        bestSellers = new ArrayList<>();
        categorys = new ArrayList<>();
        newProducts = new ArrayList<>();

        listingModel = new ListingModel();
        listingModel.getMarketplace();



        listingModel.setMarketplaceReadyListsner(new ListingModel.MarketplaceReadyListsner() {
            @Override
            public void onReady(ArrayList<ListingModel> bestSellers, ArrayList<ListingModel> newListing, int cat1, int cat2, int cat3, int cat4) {
                loadingBar.setVisibility(View.GONE);
                root.setVisibility(View.VISIBLE);
                marketplaceBestSellerAdapter = new MarketplaceNewProductAdapter(Marketplace.this, bestSellers);
                marketplaceNewProductAdapter = new MarketplaceNewProductAdapter(Marketplace.this, newListing);

                LinearLayoutManager bestSellerManger = new LinearLayoutManager(Marketplace.this,LinearLayoutManager.HORIZONTAL,false);
                LinearLayoutManager newProductsManager = new LinearLayoutManager(Marketplace.this,LinearLayoutManager.VERTICAL,false);

                newProductRecyclerview.setLayoutManager(newProductsManager);
                bestSellerRecyclerview.setLayoutManager(bestSellerManger);
                newProductRecyclerview.setAdapter(marketplaceNewProductAdapter);
                bestSellerRecyclerview.setAdapter(marketplaceBestSellerAdapter);

                PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
                pagerSnapHelper.attachToRecyclerView(bestSellerRecyclerview);
                bestSellerIndicator.attachToRecyclerView(bestSellerRecyclerview, pagerSnapHelper);
                marketplaceBestSellerAdapter.registerAdapterDataObserver(bestSellerIndicator.getAdapterDataObserver());


            }

            @Override
            public void onNextpageReady(ArrayList<ListingModel> listingModelArrayList) {

            }

            @Override
            public void onError(String message) {
                loadingBar.setVisibility(View.GONE);
                root.setVisibility(View.GONE);
            }
        });

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(!searchBar.getText().toString().trim().equalsIgnoreCase("")){
                        Intent intent = new Intent(Marketplace.this, MarketPlaceDetailView.class);
                        intent.putExtra("query",searchBar.getText().toString().trim());
                        intent.putExtra("type","1");
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });



        viewAllNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Marketplace.this,ProductDetails.class));
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
