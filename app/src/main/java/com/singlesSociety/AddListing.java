package com.singlesSociety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.singlesSociety.uiAdapters.AddProductImageAdapter;
import com.singlesSociety.UiModels.ListingModel;
import com.singlesSociety.UiModels.ProductImageModel;
import com.singlesSociety.UiModels.Utils.ListDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator2;

public class AddListing extends AppCompatActivity {

    private static int PICK_IMAGE = 1;
    RecyclerView imagesRecyclerview;
    EditText title,price,description;
    MaterialCheckBox availability;
    String mTitle,mPrice,mDescription,mCategory,mAvailability;
    LinearLayout cancel,save;
    AddProductImageAdapter adapter;
    ArrayList<ProductImageModel> imagesList = new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<>();
    CircleIndicator2 imagesIndicator;
    ListDialog listDialog;
    String productId = "",displayImage = "";
    MaterialButton selectImage;
    String shopName, retailerId;
    LinearLayout scrollImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);
        initView();
    }

    private void initView() {
        populateCategory();
        selectImage = findViewById(R.id.upload_listing_image);
        title = findViewById(R.id.add_listing_product_name);
        price = findViewById(R.id.add_listing_product_price);
        description = findViewById(R.id.add_listing_product_desc);
        availability = findViewById(R.id.add_listing_product_availability);
        cancel = findViewById(R.id.add_listing_cancel);
        save = findViewById(R.id.add_listing_save);
        scrollImageLayout = findViewById(R.id.product_image_layout);
        imagesRecyclerview = findViewById(R.id.product_image_recyclerview);
        imagesIndicator = findViewById(R.id.product_image_indicator);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        adapter = new AddProductImageAdapter(imagesList, this);
        LinearLayoutManager imagesManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        imagesRecyclerview.setLayoutManager(imagesManager);
        imagesRecyclerview.setAdapter(adapter);
        pagerSnapHelper.attachToRecyclerView(imagesRecyclerview);
        imagesIndicator.attachToRecyclerView(imagesRecyclerview, pagerSnapHelper);
        adapter.registerAdapterDataObserver(imagesIndicator.getAdapterDataObserver());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        retailerId = preferences.getString("userEmail","");



        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitle = title.getText().toString().trim();
                mPrice = price.getText().toString().trim();
                mDescription = description.getText().toString().trim();
                if (availability.isChecked()) {
                    mAvailability = "true";
                } else {
                    mAvailability = "false";
                }
                if (isValidInput()) {
                    ListingModel listingModel = new ListingModel(AddListing.this, productId, retailerId, mTitle, mPrice, mDescription, mAvailability, displayImage);
                    listingModel.createProduct();
                    listingModel.setCreateProductListener(new ListingModel.CreateProductListener() {
                        @Override
                        public void onSuccess() {
                            setResult(1);
                            finish();
                            Toast.makeText(AddListing.this, "New Product Added", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(AddListing.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private boolean isValidInput(){
        boolean isValid = true;
        if(TextUtils.isEmpty(mTitle)){
            isValid = false;
            title.setError("Required");
            return isValid;
        }
        if(TextUtils.isEmpty(mPrice)){
            isValid = false;
            price.setError("Required");
            return isValid;
        }
        if(TextUtils.isEmpty(mDescription)){
            isValid = false;
            description.setError("Required");
            return isValid;
        }
        if(displayImage.equalsIgnoreCase("")){
            isValid = false;
            Toast.makeText(this, "Upload at least one image", Toast.LENGTH_SHORT).show();
            return isValid;
        }
        return  isValid;
    }

    private void populateCategory(){
        categoryList.add("Gadgets");
        categoryList.add("Grocery");
        categoryList.add("Wears");
    }


    // function to generate a random string of length n
    static String generateProductId()
    {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(50);

        for (int i = 0; i < 50; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public String BitmapToString(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        String imgString = Base64.encodeToString(b, Base64.DEFAULT);
        return imgString;
    }

    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.special_activity_background));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.special_activity_background));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                String imageString = BitmapToString(bitmap);

                if (productId.equalsIgnoreCase("")) {
                    productId = generateProductId();
                }
                ListingModel model = new ListingModel(imageString, productId, AddListing.this);
                model.uploadImage();
                model.setImageUploadListener(new ListingModel.ImageUploadListener() {
                    @Override
                    public void onUploadSuccessful(ProductImageModel productImageModel) {
                        if (displayImage.equalsIgnoreCase("")) {
                            displayImage = productImageModel.getImageUrl();
                        }
                        adapter.addItem(productImageModel);
                        adapter.notifyDataSetChanged();
                        scrollImageLayout.setVisibility(View.VISIBLE);
                        imagesRecyclerview.scrollToPosition(adapter.getItemCount() - 1);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
