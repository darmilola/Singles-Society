package com.aure.UiModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;


import com.aure.UiModels.Utils.LoadingDialogUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivityModel {
    private String isProfileCompleted,isSubscribed,firstname,lastname,imageUrl,phonenumber;
    private String baseUrl = new URL().getBaseUrl();
    private String getProfileUrl = baseUrl+"users/search";
    private String getShowProfileUrl = baseUrl+"users/show";
    private String setMatchUrl = baseUrl+"matches";
    private String setLikeUrl = baseUrl+"like/user";
    private String deleteAccountUrl = baseUrl+"users/delete";
    private String userEmail;
    private InfoReadyListener infoReadyListener;
    private ShowcaseInfoReadyListener showcaseInfoReadyListener;
    private ArrayList<PreviewProfileModel> previewProfileModels = new ArrayList<>();
    private ArrayList<String> likedUserId = new ArrayList<>();
    private Context context;
    private String userId;
    private String isMatched;
    private LoadingDialogUtils loadingDialogUtils;
    private DeletionListener deletionListener;

    public interface InfoReadyListener{
        void onReady(MainActivityModel mainActivityModel);
        void onError(String message);
    }

    public interface ShowcaseInfoReadyListener{
        void onReady(ArrayList<PreviewProfileModel> previewProfileModels,ArrayList<String> likedUserId);
        void onError(String message);
        void onEmptyResponse();
    }


    public interface DeletionListener{
        void onSuccess();
        void onError();
    }

    public void setDeletionListener(DeletionListener deletionListener) {
        this.deletionListener = deletionListener;
    }

    public MainActivityModel(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        userEmail = preferences.getString("userEmail","");
        this.context = context;
        loadingDialogUtils = new LoadingDialogUtils(context);
    }

    public MainActivityModel(String userId,Context context){
        this.userId = userId;
        this.context = context;
        loadingDialogUtils = new LoadingDialogUtils(context);
    }

    public MainActivityModel(String isProfileCompleted, String isSubscribed, String firstname, String lastname, String imageUrl, String phonenumber, String isMatched){
           this.isProfileCompleted = isProfileCompleted;
           this.isSubscribed = isSubscribed;
           this.firstname = firstname;
           this.lastname = lastname;
           this.imageUrl = imageUrl;
           this.phonenumber   = phonenumber;
           this.isMatched = isMatched;
    }

    public String getIsProfileCompleted() {
        return isProfileCompleted;
    }

    public String getIsSubscribed() {
        return isSubscribed;
    }

    public String getIsMatched() {
        return isMatched;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
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
                    JSONArray jsonArray1 = jsonObject.getJSONArray("likes");
                   // JSONArray jsonArray2 = jsonObject.getJSONArray("likedYou");
                    if(jsonArray1.length() >= 1) {
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            String userId = jsonArray1.getJSONObject(j).getString("userId");
                            likedUserId.add(userId);
                        }
                    }

                    for(int i = 0; i < jsonArray.length(); i++) {
                        int age = jsonArray.getJSONObject(i).getInt("age");
                        String userId = jsonArray.getJSONObject(i).getString("email");
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
                        String isMatched = jsonArray.getJSONObject(i).getString("isMatched");
                        PreviewProfileModel previewProfileModel = new PreviewProfileModel(userId,firstname, about, mStatus, language, city, occupation, marriageGoals, education, workplace, drinking, smoking, gender, quote, age, firstImage, secondImage, thirdImage, mReligion,isMatched);
                        previewProfileModels.add(previewProfileModel);
                    }

              /*      for(int i = 0; i < jsonArray2.length(); i++) {
                        int age = jsonArray2.getJSONObject(i).getInt("age");
                        String userId = jsonArray2.getJSONObject(i).getString("email");
                        String city = jsonArray2.getJSONObject(i).getString("city");
                        String mStatus = jsonArray2.getJSONObject(i).getString("status");
                        String language = jsonArray2.getJSONObject(i).getString("language");
                        String workplace = jsonArray2.getJSONObject(i).getString("workplace");
                        String occupation = jsonArray2.getJSONObject(i).getString("occupation");
                        String education = jsonArray2.getJSONObject(i).getString("education");
                        String quote = jsonArray2.getJSONObject(i).getString("quote");
                        String firstImage = jsonArray2.getJSONObject(i).getString("firstImage");
                        String secondImage = jsonArray2.getJSONObject(i).getString("secondImage");
                        String thirdImage = jsonArray2.getJSONObject(i).getString("thirdImage");
                        String about = jsonArray2.getJSONObject(i).getString("about");
                        String marriageGoals = jsonArray2.getJSONObject(i).getString("marriageGoals");
                        String drinking = jsonArray2.getJSONObject(i).getString("drinking");
                        String smoking = jsonArray2.getJSONObject(i).getString("smoking");
                        String gender = jsonArray2.getJSONObject(i).getString("gender");
                        String mReligion = jsonArray2.getJSONObject(i).getString("religion");
                        String firstname = jsonArray2.getJSONObject(i).getString("firstname");
                        String isMatched = jsonArray2.getJSONObject(i).getString("isMatched");
                        PreviewProfileModel previewProfileModel = new PreviewProfileModel(userId,firstname, about, mStatus, language, city, occupation, marriageGoals, education, workplace, drinking, smoking, gender, quote, age, firstImage, secondImage, thirdImage, mReligion,isMatched);
                        previewProfileModels.add(previewProfileModel);
                    }*/
                    Collections.shuffle(previewProfileModels);
                    showcaseInfoReadyListener.onReady(previewProfileModels,likedUserId);

                    }
                else if(status.equalsIgnoreCase("failure")){
                    showcaseInfoReadyListener.onEmptyResponse();
                }
                else{
                    showcaseInfoReadyListener.onError("Error Occurred");
                }
            } catch (JSONException e) {
                //Log.e("error occured  ", e.getLocalizedMessage());
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
                    String firstname = jsonArray.getJSONObject(0).getString("firstname");
                    String lastname = jsonArray.getJSONObject(0).getString("lastname");
                    String imageUrl = jsonArray.getJSONObject(0).getString("profileImage");
                    String phonenumber = jsonArray.getJSONObject(0).getString("phonenumber");
                    String isMatched = jsonArray.getJSONObject(0).getString("isMatched");
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    preferences.edit().putString("gender",gender).apply();

                    MainActivityModel mainActivityModel = new MainActivityModel(isProfileCompleted,isSubscribed,firstname,lastname,imageUrl,phonenumber,isMatched);
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
            jsonObject.put("religion",preferences.getString("religion","Christianity"));
            jsonObject.put("drinking",preferences.getString("drinking","d'ont drink"));
            jsonObject.put("smoking",preferences.getString("smoking","d'ont smoke"));
            jsonObject.put("language",preferences.getString("language","English"));
            jsonObject.put("education",preferences.getString("education","Undergraduate"));
            jsonObject.put("goal",preferences.getString("goal","Ready to marry in 1-2 years"));
            jsonObject.put("max_age",Integer.parseInt(preferences.getString("max_age","70")));
            jsonObject.put("min_age",Integer.parseInt(preferences.getString("min_age","18")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }



    private Handler matchHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            loadingDialogUtils.cancelLoadingDialog();
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("success")){

                }
                else if(status.equalsIgnoreCase("failure")){

                }
                else{

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    };


    public void setLiked(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildSetLikeInfo(this.userId));
            Request request = new Request.Builder()
                    .url(setLikeUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = matchHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            matchHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();

    }

    public void setMatched(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildSetMatchInfo(this.userId));
            Request request = new Request.Builder()
                    .url(setMatchUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = matchHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            matchHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();

    }

    public void deleteAccount(){
        loadingDialogUtils.showLoadingDialog("Deleting...");
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildDeleteAccount());
            Request request = new Request.Builder()
                    .url(deleteAccountUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = matchHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            matchHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();

    }


    private String buildSetLikeInfo(String likedUserId){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",preferences.getString("userEmail",""));
            jsonObject.put("likedUserId",likedUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    private String buildSetMatchInfo(String likedUserId){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",preferences.getString("userEmail",""));
            jsonObject.put("matchId",likedUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private String buildDeleteAccount(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",preferences.getString("userEmail",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


}
