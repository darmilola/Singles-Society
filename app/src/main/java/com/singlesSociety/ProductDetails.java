package com.singlesSociety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import me.relex.circleindicator.CircleIndicator2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.singlesSociety.uiAdapters.MarketplaceProductDetailAdapter;
import com.singlesSociety.UiModels.ListingModel;
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductDetails extends AppCompatActivity {

    private  int MY_PERMISSIONS_REQUEST_CALL_PHONE = 20;
    RecyclerView detailRecyclerview;
    MarketplaceProductDetailAdapter detailAdapter;
    ArrayList<String> detailList = new ArrayList<>();
    CircleIndicator2 detailIndicator;
    TextView name,description,price;
    ListingModel listingModel,mListingModel;
    String productId, retailerId;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    MaterialButton contactSeller;
    String sellersPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initView();
    }

     private void initView(){
        contactSeller = findViewById(R.id.listing_detail_contact_seller);
        progressBar = findViewById(R.id.user_detail_progressbar);
        nestedScrollView = findViewById(R.id.user_details_scroller);
        mListingModel = getIntent().getParcelableExtra("info");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        retailerId = preferences.getString("userEmail","");
        detailRecyclerview = findViewById(R.id.detail_recyclerview);
        name = findViewById(R.id.listing_detail_name);
        description = findViewById(R.id.listing_detail_description);
        price = findViewById(R.id.listing_detail_price);
        detailIndicator = findViewById(R.id.detail_indicator);
        listingModel = new ListingModel(mListingModel.getProductId(),retailerId);
        listingModel.getProductDetail();
        name.setText(mListingModel.getName());
        description.setText(mListingModel.getDescription());
        //price.setText(mListingModel.getPrice());

         Locale NigerianLocale = new Locale("en","ng");
         String unFormattedPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(mListingModel.getPrice()));
         String formattedPrice = unFormattedPrice.replaceAll("\\.00","");
         price.setText(formattedPrice);


        listingModel.setDetailsReadyListener(new ListingModel.DetailsReadyListener() {
            @Override
            public void onDetailsReady(ArrayList<String> imagesList, String phone) {
                sellersPhone = phone;
                nestedScrollView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                LinearLayoutManager detailamanager = new LinearLayoutManager(ProductDetails.this, LinearLayoutManager.HORIZONTAL,false);
                detailAdapter = new MarketplaceProductDetailAdapter(ProductDetails.this, imagesList);
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
                Toast.makeText(ProductDetails.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        contactSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+sellersPhone));
                if (ContextCompat.checkSelfPermission(ProductDetails.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ProductDetails.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);

                    // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                } else {
                    //You already have permission
                    try {
                        startActivity(callIntent);
                    } catch(SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 20:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+sellersPhone));
                    startActivity(callIntent);

                } else {

                }
                return;


            // other 'case' lines to check for other
            // permissions this app might request
        }
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
