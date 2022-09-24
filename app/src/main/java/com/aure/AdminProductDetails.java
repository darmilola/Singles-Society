package com.aure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.aure.UiAdapters.MarketplaceProductDetailAdapter;
import com.aure.UiModels.ListingModel;
import com.aure.UiModels.MainActivityModel;
import com.aure.UiModels.PaymentModel;
import com.aure.UiModels.Utils.ListDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.limerse.iap.DataWrappers;
import com.limerse.iap.IapConnector;
import com.limerse.iap.PurchaseServiceListener;
import com.limerse.iap.SubscriptionServiceListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.alessandro.android.iab.BillingApi;
import jp.alessandro.android.iab.BillingContext;
import jp.alessandro.android.iab.BillingException;
import jp.alessandro.android.iab.BillingProcessor;
import jp.alessandro.android.iab.Purchase;
import jp.alessandro.android.iab.PurchaseType;
import jp.alessandro.android.iab.handler.PurchaseHandler;
import jp.alessandro.android.iab.handler.StartActivityHandler;
import jp.alessandro.android.iab.logger.SystemLogger;
import jp.alessandro.android.iab.response.PurchaseResponse;
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
    ListDialog listDialog;
    ArrayList<String> promotionType = new ArrayList<>();
    private BillingProcessor mBillingProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_details);
        initView();
    }


    private void showAlert(){
        new AlertDialog.Builder(AdminProductDetails.this)
                .setTitle("Sponsor Successful")
                .setMessage("You have successfully sponsored your product")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .show();
    }

    private final PurchaseHandler mPurchaseHandler = new PurchaseHandler() {
        @Override
        public void call(PurchaseResponse response) {
            if (response.isSuccess()) {
                Purchase purchase = response.getPurchase();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date currentDate = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);

                if(purchase.getSku().equalsIgnoreCase("aure.promote.3weeks")){
                    c.add(Calendar.DATE, 21);
                    Date endDate = c.getTime();
                    PaymentModel paymentModel = new PaymentModel(AdminProductDetails.this,dateFormat.format(endDate),mListingModel.getProductId());
                    paymentModel.sponsor();
                    paymentModel.setPaymentListener(new PaymentModel.PaymentListener() {
                        @Override
                        public void onSuccess() {

                            showAlert();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(AdminProductDetails.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                if(purchase.getSku().equalsIgnoreCase("aure.promote.1week")){

                    c.add(Calendar.DATE, 7);
                    Date endDate = c.getTime();
                    PaymentModel paymentModel = new PaymentModel(AdminProductDetails.this,dateFormat.format(endDate),mListingModel.getProductId());
                    paymentModel.sponsor();
                    paymentModel.setPaymentListener(new PaymentModel.PaymentListener() {
                        @Override
                        public void onSuccess() {

                            showAlert();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(AdminProductDetails.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
            else {

                // Handle the error
            }
        }
    };

    private void initBillingProcessor() {
        BillingContext.Builder builder = new BillingContext.Builder()
                .setContext(AdminProductDetails.this) // App context
                .setPublicKeyBase64("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2fDRqNLFSm7LYoCPZ/rG+8CpQXn/LCQNAxPtVRdt2ZNdpORpH0yvCm0vV8VOcSb6zWeM9s7dCt36wCLSJqllNw4fNkEWn/GcEV2iWNa3WT/I4JgDwstv4KGFq8FAYRA0Y+zICdvBUf833v/UuRWMAxUi2GfYmzJel+8uQtva1fzwHyzjRCYa1Od4F98IUebR0BfJ3Jp4KS3E5mr8GAuii61MxaR+n32YsEPC5gNCzkvpJO3PCbZr/XwiGG/l/sGPKQTEDapmLIhBOhnatwWj+Wmusww5RlsrEDwHnY6zRHQrwler1pW0IlqXzpyBDCKftGPa9N/o3KWof1WnUIGkXQIDAQAB") // Public key generated on the Google Play Console
                .setApiVersion(BillingApi.VERSION_5) // It also supports version 5
                .setLogger(new SystemLogger());

        mBillingProcessor = new BillingProcessor(builder.build(), mPurchaseHandler);
    }

    private void initView(){
        populateCategory();
        initBillingProcessor();
        listDialog = new ListDialog(promotionType,AdminProductDetails.this);
        ignorePromotion = getIntent().getStringExtra("ignore");
        promote = findViewById(R.id.admin_product_sponsor);
        delete = findViewById(R.id.admin_product_delete);
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
                listDialog.showListDialog();
                listDialog.setItemClickedListener(new ListDialog.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(String text) {
                        if(text.equalsIgnoreCase("1 week")){

                             init1weekSponsor();
                        }
                        else if(text.equalsIgnoreCase("3 weeks")){
                              init3weeksSponsor();

                        }
                    }
                });
            }
        });



        if((ignorePromotion != null) && (!ignorePromotion.equalsIgnoreCase(""))){
            promote.setVisibility(View.GONE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteAlert();
            }
        });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mBillingProcessor.onActivityResult(requestCode, resultCode, data)) {
            // The response will be sent through PurchaseHandler
            return;
        }
    }

    private void init1weekSponsor(){
        Activity activity = AdminProductDetails.this;
        int requestCode = 10; // YOUR REQUEST CODE
        String itemId = "aure.promote.1week";
        PurchaseType purchaseType = PurchaseType.IN_APP; // or PurchaseType.SUBSCRIPTION for subscriptions
        String developerPayload = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2fDRqNLFSm7LYoCPZ/rG+8CpQXn/LCQNAxPtVRdt2ZNdpORpH0yvCm0vV8VOcSb6zWeM9s7dCt36wCLSJqllNw4fNkEWn/GcEV2iWNa3WT/I4JgDwstv4KGFq8FAYRA0Y+zICdvBUf833v/UuRWMAxUi2GfYmzJel+8uQtva1fzwHyzjRCYa1Od4F98IUebR0BfJ3Jp4KS3E5mr8GAuii61MxaR+n32YsEPC5gNCzkvpJO3PCbZr/XwiGG/l/sGPKQTEDapmLIhBOhnatwWj+Wmusww5RlsrEDwHnY6zRHQrwler1pW0IlqXzpyBDCKftGPa9N/o3KWof1WnUIGkXQIDAQAB";

        mBillingProcessor.startPurchase(activity, requestCode, itemId, purchaseType, developerPayload,
                new StartActivityHandler() {
                    @Override
                    public void onSuccess() {
                        // Billing activity started successfully
                        // Do nothing
                    }

                    @Override
                    public void onError(BillingException e) {
                        // Handle the error
                    }
                });
    }

    private void init3weeksSponsor(){
        Activity activity = AdminProductDetails.this;
        int requestCode = 10; // YOUR REQUEST CODE
        String itemId = "aure.promote.3weeks";
        PurchaseType purchaseType = PurchaseType.IN_APP; // or PurchaseType.SUBSCRIPTION for subscriptions
        String developerPayload = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2fDRqNLFSm7LYoCPZ/rG+8CpQXn/LCQNAxPtVRdt2ZNdpORpH0yvCm0vV8VOcSb6zWeM9s7dCt36wCLSJqllNw4fNkEWn/GcEV2iWNa3WT/I4JgDwstv4KGFq8FAYRA0Y+zICdvBUf833v/UuRWMAxUi2GfYmzJel+8uQtva1fzwHyzjRCYa1Od4F98IUebR0BfJ3Jp4KS3E5mr8GAuii61MxaR+n32YsEPC5gNCzkvpJO3PCbZr/XwiGG/l/sGPKQTEDapmLIhBOhnatwWj+Wmusww5RlsrEDwHnY6zRHQrwler1pW0IlqXzpyBDCKftGPa9N/o3KWof1WnUIGkXQIDAQAB";

        mBillingProcessor.startPurchase(activity, requestCode, itemId, purchaseType, developerPayload,
                new StartActivityHandler() {
                    @Override
                    public void onSuccess() {
                        // Billing activity started successfully
                        // Do nothing
                    }

                    @Override
                    public void onError(BillingException e) {
                        // Handle the error
                    }
                });
    }

    private void populateCategory(){
        promotionType.add("1 week");
        promotionType.add("3 weeks");
    }

    private void showDeleteAlert(){
        new AlertDialog.Builder(AdminProductDetails.this)
                .setTitle("Delete Product")
                .setMessage("You are about to delete this product. You cannot undo this action")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ListingModel mListingModel = new ListingModel(listingModel.getProductId(),AdminProductDetails.this);
                        mListingModel.deletePrduct();
                        mListingModel.setCreateProductListener(new ListingModel.CreateProductListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(AdminProductDetails.this, "Product Deleted", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onError(String message) {

                            }
                        });
                    }
                })
                .show();
    }

}