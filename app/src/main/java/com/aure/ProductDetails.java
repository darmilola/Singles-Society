package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import me.relex.circleindicator.CircleIndicator2;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.aure.UiAdapters.MarketplaceProductDetailAdapter;

import java.util.ArrayList;

public class ProductDetails extends AppCompatActivity {

    RecyclerView detailRecyclerview;
    MarketplaceProductDetailAdapter detailAdapter;
    ArrayList<String> detailList = new ArrayList<>();
    CircleIndicator2 detailIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initView();
    }

    private void initView(){
        detailRecyclerview = findViewById(R.id.detail_recyclerview);
        for(int i = 0; i < 5; i++){
            detailList.add("");
        }
        detailIndicator = findViewById(R.id.detail_indicator);
        LinearLayoutManager detailamanager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        detailAdapter = new MarketplaceProductDetailAdapter(this,detailList);
        detailRecyclerview.setAdapter(detailAdapter);
        detailRecyclerview.setLayoutManager(detailamanager);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(detailRecyclerview);
        detailIndicator.attachToRecyclerView(detailRecyclerview, pagerSnapHelper);
        detailAdapter.registerAdapterDataObserver(detailIndicator.getAdapterDataObserver());
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
