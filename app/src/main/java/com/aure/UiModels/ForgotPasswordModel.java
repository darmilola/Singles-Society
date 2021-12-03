package com.aure.UiModels;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.aure.UiModels.Utils.LoadingDialogUtils;
import com.aure.UiModels.Utils.LottieLoadingDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgotPasswordModel {
    private String mEmail;
    private Context context;
    private LottieLoadingDialog loadingDialogUtils;
    private String baseUrl = new URL().getBaseUrl();
    private String getLink = baseUrl+"resetpassword";
    private String gmailLoginUrl = baseUrl+"users/auth/gmail/login/credential";
    private getLinkListener LinkListener;

    public ForgotPasswordModel(String mEmail, Context context){
           this.context = context;
           this.mEmail = mEmail;
           loadingDialogUtils = new LottieLoadingDialog(context);
    }

    public interface getLinkListener{
        void isSuccess();
        void isFailure();
    }

    public void setGetLinkListener(ForgotPasswordModel.getLinkListener getLinkListener) {
        this.LinkListener = getLinkListener;
    }

    public void getResetLink(){
        loadingDialogUtils.showLoadingDialog();
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildGetLink(this.mEmail));
            Request request = new Request.Builder()
                    .url(getLink)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = gmailLoginHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            gmailLoginHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    private Handler gmailLoginHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            loadingDialogUtils.cancelLoadingDialog();
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("success")){
                    LinkListener.isSuccess();

                }
                else if(status.equalsIgnoreCase("failure")){
                    LinkListener.isFailure();
                }
                else{
                    LinkListener.isFailure();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                LinkListener.isFailure();
            }

        }
    };

    private String buildGetLink(String email){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
