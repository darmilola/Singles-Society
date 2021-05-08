package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import me.relex.circleindicator.CircleIndicator2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aure.UiAdapters.MarketPlaceCategoryAdapter;
import com.aure.UiAdapters.MarketplaceBestSellerAdapter;
import com.aure.UiAdapters.MarketplaceNewProductAdapter;

import java.util.ArrayList;

public class Marketplace extends AppCompatActivity {

    RecyclerView categoryReyclerview,bestSellerRecyclerview,newProductRecyclerview;
    TextView viewAllNewProduct;
    MarketPlaceCategoryAdapter marketPlaceCategoryAdapter;
    MarketplaceNewProductAdapter marketplaceNewProductAdapter;
    MarketplaceBestSellerAdapter marketplaceBestSellerAdapter;
    ArrayList<String> bestSellers,categorys,newProducts;
    CircleIndicator2 bestSellerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);
        initView();
    }

    private void initView(){
        categoryReyclerview = findViewById(R.id.category_recyclerview);
        bestSellerRecyclerview = findViewById(R.id.best_seller_recylerview);
        newProductRecyclerview = findViewById(R.id.market_place_newproduct_recyclerview);
        viewAllNewProduct = findViewById(R.id.market_place_view_all);
        bestSellerIndicator = findViewById(R.id.bestseller_indicator);
        bestSellers = new ArrayList<>();
        categorys = new ArrayList<>();
        newProducts = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            bestSellers.add("");
            newProducts.add("");
        }
        for(int i = 0; i < 5; i++){
            categorys.add("");
        }
        marketplaceBestSellerAdapter = new MarketplaceBestSellerAdapter(this,bestSellers);
        marketPlaceCategoryAdapter = new MarketPlaceCategoryAdapter(this,categorys);
        marketplaceNewProductAdapter = new MarketplaceNewProductAdapter(this,newProducts);

        LinearLayoutManager bestSellerManger = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager categoryManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager newProductsManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        categoryReyclerview.setAdapter(marketPlaceCategoryAdapter);
        newProductRecyclerview.setAdapter(marketplaceNewProductAdapter);
        bestSellerRecyclerview.setAdapter(marketplaceBestSellerAdapter);
        categoryReyclerview.setLayoutManager(categoryManager);
        newProductRecyclerview.setLayoutManager(newProductsManager);
        bestSellerRecyclerview.setLayoutManager(bestSellerManger);

        viewAllNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Marketplace.this,ProductDetails.class));
            }
        });
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(bestSellerRecyclerview);
        bestSellerIndicator.attachToRecyclerView(bestSellerRecyclerview, pagerSnapHelper);
        marketplaceBestSellerAdapter.registerAdapterDataObserver(bestSellerIndicator.getAdapterDataObserver());

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
