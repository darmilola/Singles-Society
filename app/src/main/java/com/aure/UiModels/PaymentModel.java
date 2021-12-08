package com.aure.UiModels;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.aure.MainActivity;
import com.aure.UiModels.Utils.LoadingDialogUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PaymentModel {
    private String baseUrl = new URL().getBaseUrl();
    private String subscribeUrl = baseUrl+"users/update";
    private String promoteProductUrl = baseUrl+"products/sponsor";
    private String userId;
    private Context context;
    private LoadingDialogUtils loadingDialogUtils;
    private PaymentListener paymentListener;
    private String sponsorshipEnds;
    private String productId;


    public interface PaymentListener{
        void onSuccess();
        void onFailure();
    }

    public PaymentModel(Context context, String userId){
           this.context = context;
           this.userId = userId;
           loadingDialogUtils = new LoadingDialogUtils(context);
    }

    public PaymentModel(Context context, String sponsorshipEnds, String productId){
           this.sponsorshipEnds = sponsorshipEnds;
           this.productId = productId;
           loadingDialogUtils = new LoadingDialogUtils(context);
    }

    public void setPaymentListener(PaymentListener paymentListener) {
        this.paymentListener = paymentListener;
    }



    private Handler subscribeHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            loadingDialogUtils.cancelLoadingDialog();
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("success")){
                    paymentListener.onSuccess();
                }
                else if(status.equalsIgnoreCase("failure")){
                    paymentListener.onFailure();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                paymentListener.onFailure();
            }
        }
    };


    public void subscribe(){
        loadingDialogUtils.showLoadingDialog("Subscribing...");
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildSubscribe(this.userId));
            Request request = new Request.Builder()
                    .url(subscribeUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = subscribeHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            subscribeHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    public void sponsor(){
        loadingDialogUtils.showLoadingDialog("processing...");
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildSponsorship(this.sponsorshipEnds, this.productId));
            Request request = new Request.Builder()
                    .url(promoteProductUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = subscribeHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            subscribeHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    private String buildSubscribe(String userEmail){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",userEmail);
            jsonObject.put("isSubscribed","true");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private String buildSponsorship(String sponsorshipEnds, String productId){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("endDate",sponsorshipEnds);
            jsonObject.put("id",productId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
