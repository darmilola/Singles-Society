package com.aure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.aure.UiAdapters.MarketplaceProductDetailAdapter;
import com.aure.UiModels.ListingModel;
import com.google.android.material.button.MaterialButton;
import com.limerse.iap.DataWrappers;
import com.limerse.iap.IapConnector;
import com.limerse.iap.PurchaseServiceListener;
import com.limerse.iap.SubscriptionServiceListener;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

        List<String> nonConsumablesList = Collections.singletonList("aure.promote");
        List<String> consumablesList = Arrays.asList("aure.promote");
        List<String> subsList = Collections.singletonList("subscriptions");

        IapConnector iapConnector = new IapConnector(
                this,
                nonConsumablesList,
                consumablesList,
                subsList,
                  "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2fDRqNLFSm7LYoCPZ/rG+8CpQXn/LCQNAxPtVRdt2ZNdpORpH0yvCm0vV8VOcSb6zWeM9s7dCt36wCLSJqllNw4fNkEWn/GcEV2iWNa3WT/I4JgDwstv4KGFq8FAYRA0Y+zICdvBUf833v/UuRWMAxUi2GfYmzJel+8uQtva1fzwHyzjRCYa1Od4F98IUebR0BfJ3Jp4KS3E5mr8GAuii61MxaR+n32YsEPC5gNCzkvpJO3PCbZr/XwiGG/l/sGPKQTEDapmLIhBOhnatwWj+Wmusww5RlsrEDwHnY6zRHQrwler1pW0IlqXzpyBDCKftGPa9N/o3KWof1WnUIGkXQIDAQAB",
                  true
        );


        iapConnector.addPurchaseListener(new PurchaseServiceListener() {
            public void onPricesUpdated(@NotNull Map iapKeyPrices) {

            }

            public void onProductPurchased(@NonNull DataWrappers.PurchaseInfo purchaseInfo) {
                if (purchaseInfo.getSku().equals("plenty")) {

                } else if (purchaseInfo.getSku().equals("yearly")) {

                } else if (purchaseInfo.getSku().equals("moderate")) {

                } else if (purchaseInfo.getSku().equals("base")) {

                } else if (purchaseInfo.getSku().equals("quite")) {

                }
            }

            public void onProductRestored(@NonNull DataWrappers.PurchaseInfo purchaseInfo) {

            }
        });
        iapConnector.addSubscriptionListener(new SubscriptionServiceListener() {
            public void onSubscriptionRestored(@NonNull DataWrappers.PurchaseInfo purchaseInfo) {
            }

            public void onSubscriptionPurchased(@NonNull DataWrappers.PurchaseInfo purchaseInfo) {
                if (purchaseInfo.getSku().equals("subscription")) {

                }
            }

            public void onPricesUpdated(@NotNull Map iapKeyPrices) {

            }
        });




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
        Locale NigerianLocale = new Locale("en","ng");
        String unFormattedPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(mListingModel.getPrice()));
        String formattedPrice = unFormattedPrice.replaceAll("\\.00","");
        price.setText(formattedPrice);
        if(mListingModel.getIsSponsored().equalsIgnoreCase("true")){
            promote.setVisibility(View.GONE);
        }

        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //iapConnector.subscribe(AdminProductDetails.this, "subscriptions");

                iapConnector.purchase(AdminProductDetails.this, "aure.promote");

            }
        });

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