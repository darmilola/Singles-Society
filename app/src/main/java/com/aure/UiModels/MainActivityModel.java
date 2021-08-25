package com.aure.UiModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.aure.PreferenceFilter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import androidx.preference.PreferenceManager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivityModel {
    private String isProfileCompleted,isSubscribed;
    private String baseUrl = new URL().getBaseUrl();
    private String getProfileUrl = baseUrl+"users/search";
    private String getShowProfileUrl = baseUrl+"users/show";
    private String userEmail;
    private InfoReadyListener infoReadyListener;
    private ShowcaseInfoReadyListener showcaseInfoReadyListener;
    private ArrayList<PreviewProfileModel> previewProfileModels = new ArrayList<>();
    private Context context;

    public interface InfoReadyListener{
        void onReady(MainActivityModel mainActivityModel);
        void onError(String message);
    }
    public interface ShowcaseInfoReadyListener{
        void onReady(ArrayList<PreviewProfileModel> previewProfileModels);
        void onError(String message);
    }

    public MainActivityModel(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        userEmail = preferences.getString("userEmail","");
        this.context = context;
    }

    public MainActivityModel(String isProfileCompleted, String isSubscribed){
           this.isProfileCompleted = isProfileCompleted;
           this.isSubscribed = isSubscribed;
    }

    public String getIsProfileCompleted() {
        return isProfileCompleted;
    }

    public String getIsSubscribed() {
        return isSubscribed;
    }

    public void setShowcaseInfoReadyListener(ShowcaseInfoReadyListener showcaseInfoReadyListener) {
        this.showcaseInfoReadyListener = showcaseInfoReadyListener;
    }

    private Handler getShowcaseInfoHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("success")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        int age = jsonArray.getJSONObject(i).getInt("age");
                        String city = jsonArray.getJSONObject(i).getString("city");
                        String mStatus = jsonArray.getJSONObject(i).getString("status");
                        String language = jsonArray.getJSONObject(i).getString("language");
                        String workplace = jsonArray.getJSONObject(i).getString("workplace");
                        String occupation = jsonArray.getJSONObject(i).getString("occupation");
                        String education = jsonArray.getJSONObject(i).getString("education");
                        String quote = jsonArray.getJSONObject(i).getString("quote");
                        String firstImage = jsonArray.getJSONObject(i).getString("firstImage");
                        String secondImage = jsonArray.getJSONObject(i).getString("secondImage");
                        String thirdImage = jsonArray.getJSONObject(i).getString("thirdImage");
                        String about = jsonArray.getJSONObject(i).getString("about");
                        String marriageGoals = jsonArray.getJSONObject(i).getString("marriageGoals");
                        String drinking = jsonArray.getJSONObject(i).getString("drinking");
                        String smoking = jsonArray.getJSONObject(i).getString("smoking");
                        String gender = jsonArray.getJSONObject(i).getString("gender");
                        String mReligion = jsonArray.getJSONObject(i).getString("religion");
                        String firstname = jsonArray.getJSONObject(i).getString("firstname");
                        PreviewProfileModel previewProfileModel = new PreviewProfileModel(firstname, about, mStatus, language, city, occupation, marriageGoals, education, workplace, drinking, smoking, gender, quote, age, firstImage, secondImage, thirdImage, mReligion);
                        previewProfileModels.add(previewProfileModel);
                    }
                    showcaseInfoReadyListener.onReady(previewProfileModels);

                    }
                else if(status.equalsIgnoreCase("failure")){
                    showcaseInfoReadyListener.onError("Error Occurred");
                }
                else{
                    showcaseInfoReadyListener.onError("Error Occurred");
                }
            } catch (JSONException e) {
                showcaseInfoReadyListener.onError(e.getLocalizedMessage());
            }

        }
    };



    private Handler getInfoHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("success")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    String isSubscribed = jsonArray.getJSONObject(0).getString("isSubscribed");
                    String isProfileCompleted = jsonArray.getJSONObject(0).getString("isProfileCompleted");
                    String gender = jsonArray.getJSONObject(0).getString("gender");

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    preferences.edit().putString("gender",gender).apply();

                    MainActivityModel mainActivityModel = new MainActivityModel(isProfileCompleted,isSubscribed);
                    infoReadyListener.onReady(mainActivityModel);
                }
                else if(status.equalsIgnoreCase("failure")){
                    infoReadyListener.onError("Error Occurred please try again");
                }
                else{
                    infoReadyListener.onError("Error Occurred please try again");
                }
            } catch (JSONException e) {
                    infoReadyListener.onError(e.getLocalizedMessage());
            }

        }
    };


    public void setInfoReadyListener(InfoReadyListener infoReadyListener) {
        this.infoReadyListener = infoReadyListener;
    }

    public void GetUserInfo(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildGetUserInfo(userEmail));
            Request request = new Request.Builder()
                    .url(getProfileUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = getInfoHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            getInfoHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    public void GetShowUserInfo(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildGetShowCaseInfo(userEmail));
            Request request = new Request.Builder()
                    .url(getShowProfileUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = getShowcaseInfoHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            getShowcaseInfoHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    private String buildGetUserInfo(String email){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private String buildGetShowCaseInfo(String email){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",email);
            jsonObject.put("gender",preferences.getString("gender","male"));
            jsonObject.put("religion",preferences.getString("religion","Christian"));
            jsonObject.put("drinking",preferences.getString("drinking","d'ont drink"));
            jsonObject.put("smoking",preferences.getString("smoking","d'ont smoke"));
            jsonObject.put("max_age",preferences.getInt("max_age",70));
            jsonObject.put("min_age",preferences.getInt("min_age",18));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
