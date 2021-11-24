package com.aure.UiModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;

import com.aure.UiModels.Utils.ImageUploadDialog;
import com.aure.UiModels.Utils.LoadingDialogUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ListingModel implements Parcelable {
    private Context context;
    private String baseUrl = new URL().getBaseUrl();
    private String uploadImageUrl = baseUrl+"products/image/upload";
    private String createProductUrl = baseUrl+"products";
    private String pendingUrl = baseUrl+"products/retailer/pending";
    private String allProductsUrl = baseUrl+"products/retailer/show";
    private String sponsoredUrl = baseUrl+"products/retailer/sponsored";
    private String rejectedUrl = baseUrl+"products/retailer/rejected";
    private String detailsUrl = baseUrl+"products/details";
    private String marketplaceDisplayUrl = baseUrl+"products/display";
    private String searchUrl = baseUrl+"products/search";
    private String searchCatgoryUrl = baseUrl+"products/category";
    private String name,price,displayImage,category,retailerId,description,isSponsored;
    private ImageUploadDialog imageUploadDialog;
    private String encodedImage, productId;
    private ImageUploadListener imageUploadListener;
    private CreateProductListener createProductListener;
    private ListingListener listingListener;
    private LoadingDialogUtils loadingDialogUtils;
    private ArrayList<ListingModel> listingModelArrayList = new ArrayList<>();
    private ArrayList<String> imagesList = new ArrayList<>();
    private String sellersPhone = "";
    private DetailsReadyListener detailsReadyListener;
    private MarketplaceReadyListsner marketplaceReadyListsner;
    private String searchQuery;
    private int firstCategoryCount, secondCategoryCount, thirdCategoryCount, fourthCategoryCount;
    private ArrayList<ListingModel> bestSellers = new ArrayList<>();
    private ArrayList<ListingModel> newListings = new ArrayList<>();


    protected ListingModel(Parcel in) {
        baseUrl = in.readString();
        uploadImageUrl = in.readString();
        createProductUrl = in.readString();
        pendingUrl = in.readString();
        detailsUrl = in.readString();
        name = in.readString();
        price = in.readString();
        displayImage = in.readString();
        category = in.readString();
        retailerId = in.readString();
        description = in.readString();
        isSponsored = in.readString();
        encodedImage = in.readString();
        productId = in.readString();
        listingModelArrayList = in.createTypedArrayList(ListingModel.CREATOR);
        imagesList = in.createStringArrayList();
        sellersPhone = in.readString();
    }

    public static final Creator<ListingModel> CREATOR = new Creator<ListingModel>() {
        @Override
        public ListingModel createFromParcel(Parcel in) {
            return new ListingModel(in);
        }

        @Override
        public ListingModel[] newArray(int size) {
            return new ListingModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(baseUrl);
        parcel.writeString(uploadImageUrl);
        parcel.writeString(createProductUrl);
        parcel.writeString(pendingUrl);
        parcel.writeString(detailsUrl);
        parcel.writeString(name);
        parcel.writeString(price);
        parcel.writeString(displayImage);
        parcel.writeString(category);
        parcel.writeString(retailerId);
        parcel.writeString(description);
        parcel.writeString(isSponsored);
        parcel.writeString(encodedImage);
        parcel.writeString(productId);
        parcel.writeTypedList(listingModelArrayList);
        parcel.writeStringList(imagesList);
        parcel.writeString(sellersPhone);
    }

    public interface MarketplaceReadyListsner{
        void onReady(ArrayList<ListingModel> bestSellers, ArrayList<ListingModel> newListing, int cat1, int cat2, int cat3, int cat4);
        void onNextpageReady(ArrayList<ListingModel> listingModelArrayList);
        void onError(String message);
    }

    public interface DetailsReadyListener{
        void onDetailsReady(ArrayList<String> imagesList, String phone);
        void onError(String message);
    }

    public interface ListingListener{
        void onListingReady(ArrayList<ListingModel> modelArrayList);
        void onEmpty();
        void onError(String message);
    }

    public interface CreateProductListener{
        void onSuccess();
        void onError(String message);
    }

    public interface ImageUploadListener{
        void onUploadSuccessful(ProductImageModel productImageModel);
        void onError(String message);
    }


    public void setDetailsReadyListener(DetailsReadyListener detailsReadyListener) {
        this.detailsReadyListener = detailsReadyListener;
    }

    public void setListingListener(ListingListener listingListener) {
        this.listingListener = listingListener;
    }

    public void setImageUploadListener(ImageUploadListener imageUploadListener) {
        this.imageUploadListener = imageUploadListener;
    }

    public void setCreateProductListener(CreateProductListener createProductListener) {
        this.createProductListener = createProductListener;
    }

    public void setMarketplaceReadyListsner(MarketplaceReadyListsner marketplaceReadyListsner) {
        this.marketplaceReadyListsner = marketplaceReadyListsner;
    }

    public ListingModel(String productId, String retailerId){
           this.productId = productId;
           this.retailerId = retailerId;
    }

    public ListingModel(String productId, String retailerId, String name, String price, String description, String isSponsored, String displayImage){
        this.retailerId = retailerId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.displayImage = displayImage;
        this.productId = productId;
        this.isSponsored = isSponsored;
    }

    public ListingModel(){

    }

    public ListingModel(String query){
           this.searchQuery = query;
    }

    public ListingModel(Context context, String productId, String retailerId, String name, String price, String category, String description, String isSponsored, String displayImage){
        this.retailerId = retailerId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.displayImage = displayImage;
        this.productId = productId;
        this.context = context;
        this.isSponsored = isSponsored;
        loadingDialogUtils = new LoadingDialogUtils(context);
    }

    public ListingModel(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        retailerId = preferences.getString("userEmail","");
    }

    public ListingModel(String encodedImage,String productId, Context context){
        this.context = context;
        imageUploadDialog = new ImageUploadDialog(context);
        this.encodedImage = encodedImage;
        this.productId = productId;
    }

    private String buildUploadImage(String productId, String encodedImage){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("image",encodedImage);
            jsonObject.put("productId",productId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private String buildDetails(String productId, String retailerId){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("productId",productId);
            jsonObject.put("retailerId",retailerId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private String buildSearch(String query){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("query",query);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    public void createProduct(){
        loadingDialogUtils.showLoadingDialog("Creating Listing...");
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildCreateProduct(productId,retailerId,name,price,category,description,displayImage));
            Request request = new Request.Builder()
                    .url(createProductUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = createProductHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            createProductHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    public void searchProduct(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildSearch(searchQuery));
            Request request = new Request.Builder()
                    .url(searchUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = listingHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            listingHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }

    public void searchCategory(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildSearch(searchQuery));
            Request request = new Request.Builder()
                    .url(searchCatgoryUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = listingHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            listingHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    public void getPendingListing(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildDisplay(this.retailerId));
            Request request = new Request.Builder()
                    .url(pendingUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = listingHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            listingHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }

    public void getSponsoredListing(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildDisplay(this.retailerId));
            Request request = new Request.Builder()
                    .url(sponsoredUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = listingHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            listingHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }

    public void getAllListing(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildDisplay(this.retailerId));
            Request request = new Request.Builder()
                    .url(allProductsUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = listingHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            listingHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }

    public void getRejectedListing(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildDisplay(this.retailerId));
            Request request = new Request.Builder()
                    .url(rejectedUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = listingHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            listingHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }

    public void getMarketplace(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildMarketplaceDisplay("Grocery","Grocery","Grocery","Grocery"));
            Request request = new Request.Builder()
                    .url(marketplaceDisplayUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = marketplaceHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            marketplaceHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    public void getMarketplaceNextpage(String url){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildMarketplaceDisplay("Grocery","Grocery","Grocery","Grocery"));
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = marketplaceHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            marketplaceHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    public void getProductDetail(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildDetails(this.productId, this.retailerId));
            Request request = new Request.Builder()
                    .url(detailsUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = detailHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            detailHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    private Handler createProductHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            loadingDialogUtils.cancelLoadingDialog();
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("success")){
                    createProductListener.onSuccess();
                }
                else if(status.equalsIgnoreCase("failure")){
                    createProductListener.onError("Error Occurred");
                }
                else{

                }
            } catch (JSONException e) {
                e.printStackTrace();
                createProductListener.onError(e.getLocalizedMessage());

            }

        }
    };

    private Handler listingHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("success")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i < jsonArray.length(); i++){
                        String productId = jsonArray.getJSONObject(i).getString("id");
                        String name = jsonArray.getJSONObject(i).getString("name");
                        String price = jsonArray.getJSONObject(i).getString("price");
                        String displayImg = jsonArray.getJSONObject(i).getString("displayImg");
                        String retailerId = jsonArray.getJSONObject(i).getString("retailerId");
                        String description = jsonArray.getJSONObject(i).getString("description");
                        String isSponsored = jsonArray.getJSONObject(i).getString("isSponsored");

                        ListingModel listingModel = new ListingModel(productId,retailerId,name,price,description,isSponsored,displayImg);
                        listingModelArrayList.add(listingModel);
                    }
                    listingListener.onListingReady(listingModelArrayList);


                }
                else if(status.equalsIgnoreCase("failure")){
                    listingListener.onError("Error Occurred");
                }
                else{

                }
            } catch (JSONException e) {
                e.printStackTrace();
                listingListener.onError(e.getLocalizedMessage());

            }

        }
    };


    private Handler detailHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("success")){
                    JSONArray images = jsonObject.getJSONArray("images");
                    JSONArray retailerInfo = jsonObject.getJSONArray("retailerInfo");
                    for(int i = 0; i < images.length(); i++){
                        String imageUrl = images.getJSONObject(i).getString("imageUrl");
                        imagesList.add(imageUrl);
                    }
                    for(int i = 0; i < retailerInfo.length(); i++){
                        String phone = retailerInfo.getJSONObject(i).getString("phonenumber");
                        sellersPhone = phone;
                    }
                    detailsReadyListener.onDetailsReady(imagesList,sellersPhone);


                }
                else if(status.equalsIgnoreCase("failure")){
                    detailsReadyListener.onError("Error Occurred");
                }
                else{

                }
            } catch (JSONException e) {
                e.printStackTrace();
                detailsReadyListener.onError(e.getLocalizedMessage());

            }

        }
    };


    private Handler marketplaceHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("success")){
                    JSONArray products = jsonObject.getJSONArray("products");
                    JSONArray sponsored = jsonObject.getJSONArray("sponsored");
                    JSONArray firstCat = jsonObject.getJSONArray("firstCategory");
                    JSONArray secondCat = jsonObject.getJSONArray("secondCategory");
                    JSONArray thirdCat = jsonObject.getJSONArray("thirdCategory");
                    JSONArray fourthCat = jsonObject.getJSONArray("fourthCategory");

                    for(int i = 0; i < products.length(); i++){
                        String productId = products.getJSONObject(i).getString("id");
                        String name = products.getJSONObject(i).getString("name");
                        String price = products.getJSONObject(i).getString("price");
                        String displayImg = products.getJSONObject(i).getString("displayImg");
                        String retailerId = products.getJSONObject(i).getString("retailerId");
                        String description = products.getJSONObject(i).getString("description");
                        String isSponsored = products.getJSONObject(i).getString("isSponsored");

                        ListingModel listingModel = new ListingModel(productId,retailerId,name,price,description,isSponsored,displayImg);
                        newListings.add(listingModel);
                    }

                    for(int i = 0; i < sponsored.length(); i++){
                        String productId = sponsored.getJSONObject(i).getString("id");
                        String name = sponsored.getJSONObject(i).getString("name");
                        String price = sponsored.getJSONObject(i).getString("price");
                        String displayImg = sponsored.getJSONObject(i).getString("displayImg");
                        String retailerId = sponsored.getJSONObject(i).getString("retailerId");
                        String description = sponsored.getJSONObject(i).getString("description");
                        String isSponsored = sponsored.getJSONObject(i).getString("isSponsored");

                        ListingModel listingModel = new ListingModel(productId,retailerId,name,price,description,isSponsored,displayImg);
                        bestSellers.add(listingModel);
                    }
                    marketplaceReadyListsner.onReady(bestSellers,newListings,firstCat.length(),secondCat.length(),thirdCat.length(),fourthCat.length());



                }
                else if(status.equalsIgnoreCase("failure")){
                    marketplaceReadyListsner.onError("Error Occurred");
                }
                else{

                }
            } catch (JSONException e) {
                e.printStackTrace();
                marketplaceReadyListsner.onError(e.getLocalizedMessage());

            }

        }
    };



    public void uploadImage(){
        imageUploadDialog.showDialog();
        String mResponse = "";
        Runnable runnable = () -> {
            //client
            OkHttpClient okHttpClient = new OkHttpClient();
            //request builder
            Request.Builder builder = new Request.Builder();
            builder.url(uploadImageUrl);

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildUploadImage(this.productId,this.encodedImage));
            RequestBody requestBody1 = ProgressHelper.withProgress(requestBody, new ProgressUIListener() {

                @Override
                public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                    imageUploadDialog.updateProgress(100 * percent);
                    Log.e(String.valueOf(percent), "onUIProgressChanged: ");

                }

                //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
                @Override
                public void onUIProgressFinish() {
                    super.onUIProgressFinish();
                    Log.e("TAG", "onUIProgressFinish:");
                }
            });

            //post the wrapped request body
            builder.post(requestBody1);
            Call call = okHttpClient.newCall(builder.build());
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("TAG", "=============onFailure===============");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response != null){
                        String  mResponse =  response.body().string();
                        Message msg = imageUploadHandler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("response", mResponse);
                        msg.setData(bundle);
                        imageUploadHandler.sendMessage(msg);
                    }
                }
            });
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }

    private Handler imageUploadHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            imageUploadDialog.cancelDialog();
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("success")){
                    JSONObject data = jsonObject.getJSONObject("data");
                    int id = data.getInt("id");
                    String imageUrl = data.getString("imageUrl");
                    String productId = data.getString("productId");

                    ProductImageModel productImageModel = new ProductImageModel(id,imageUrl,productId);
                    imageUploadListener.onUploadSuccessful(productImageModel);
                }
                else{
                    imageUploadListener.onError("Error Uploading image please try again");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                imageUploadListener.onError("Error Occurred please try again");
            }
        }
    };



    private String buildCreateProduct(String productId, String retailerId, String title, String price, String category, String description, String displayImage){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("retailerId",retailerId);
            jsonObject.put("name",title);
            jsonObject.put("price",price);
            jsonObject.put("category",category);
            jsonObject.put("description",description);
            jsonObject.put("displayImg",displayImage);
            jsonObject.put("id",productId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    private String buildMarketplaceDisplay(String category1, String category2, String category3, String category4){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("category1",category1);
            jsonObject.put("category2",category2);
            jsonObject.put("category3",category3);
            jsonObject.put("category4",category4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private String buildDisplay(String retailerId){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("retailerId",retailerId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayImage() {
        return displayImage;
    }

    public String getIsSponsored() {
        return isSponsored;
    }

    public String getName() {
        return name;
    }

    public String getProductId() {
        return productId;
    }

    public String getPrice() {
        return price;
    }
}
