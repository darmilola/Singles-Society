package com.aure.UiModels;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Display;

import com.aure.UiModels.Utils.ImageUploadDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ListingModel {
    private Context context;
    private String baseUrl = new URL().getBaseUrl();
    private String uploadImageUrl = baseUrl+"products/image/upload";
    private String createProductUrl = baseUrl+"products";
    private ImageUploadDialog imageUploadDialog;
    private String encodedImage, productId;
    private ImageUploadListener imageUploadListener;
    private String retailerId, title, price, category, description,availability,displayImage,orderStatus;
    private int orderId;
    private CreateProductListener createProductListener;

    public interface CreateProductListener{
        void onSuccess();
        void onError(String message);
    }

    public interface ImageUploadListener{
        void onUploadSuccessful(ProductImageModel productImageModel);
        void onError(String message);
    }


    public void setImageUploadListener(ImageUploadListener imageUploadListener) {
        this.imageUploadListener = imageUploadListener;
    }

    public void setCreateProductListener(CreateProductListener createProductListener) {
        this.createProductListener = createProductListener;
    }

    public ListingModel(String productId, String retailerId, String title, String price, String category, String description, String availability, String displayImage){
        this.retailerId = retailerId;
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.displayImage = displayImage;
        this.availability = availability;
        this.productId = productId;
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


    public void createProduct(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildCreateProduct(productId,retailerId,title,price,category,description,availability,displayImage));
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

    private Handler createProductHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
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



    private String buildCreateProduct(String productId, String retailerId, String title, String price, String category, String description, String availability, String displayImage){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("retailerId",retailerId);
            jsonObject.put("name",title);
            jsonObject.put("price",price);
            jsonObject.put("category",category);
            jsonObject.put("description",description);
            jsonObject.put("isAvailable",availability);
            jsonObject.put("displayImg",displayImage);
            jsonObject.put("id",productId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
