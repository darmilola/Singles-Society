package com.aure.UiModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;

import com.aure.UiModels.Utils.ImageUploadDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
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

public class CompleteProfileModel {

    private String about,status,language,city,occupation,marriageGoals,educationLevel,workplace,drinking,smoking,gender,quote,religion;
    private int age;
    private String baseUrl = new URL().getBaseUrl();
    private String completeProfileUrl = baseUrl+"users/create";
    private String uploadImageUrl = baseUrl+"users/upload/profile/image";
    private String getProfileUrl = baseUrl+"users/search";
    private Context context;
    private String jsonKey,value,userEmail;
    private String image1Url,image2Url,image3Url;
    private SaveInfoListener saveInfoListener;
    private InfoReadyListener infoReadyListener;
    private String uploadedImageUrl = "";
    private ImageUploadDialog imageUploadDialog;
    private String encodedImage;
    private String imageType;
    private int ageValue;

    public interface SaveInfoListener{
        void onSuccess();
        void onImageUploaded(String imaeUrl);
    }
    public interface InfoReadyListener{
        void onReady(CompleteProfileModel completeProfileModel);
        void onError(String message);
    }
    public CompleteProfileModel(String jsonKey, String value, Context context){
           this.jsonKey = jsonKey;
           this.value = value;
           this.context = context;
           SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
           userEmail = preferences.getString("userEmail","");
    }

    public CompleteProfileModel(String jsonKey, int value, Context context){
        this.jsonKey = jsonKey;
        this.ageValue = value;
        this.context = context;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        userEmail = preferences.getString("userEmail","");
    }

    public CompleteProfileModel(String encodedImage,String imageType,Context context,int typeImage){
        this.context = context;
        this.encodedImage = encodedImage;
        imageUploadDialog = new ImageUploadDialog(context);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        userEmail = preferences.getString("userEmail","");
        this.imageType = imageType;
    }


    public CompleteProfileModel(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        userEmail = preferences.getString("userEmail","");
    }

    public CompleteProfileModel(String about, String status, String language, String city, String occupation, String marriageGoals, String educationLevel, String workplace, String drinking, String smoking, String gender, String quote, int age, String image1Url, String image2Url, String image3Url,String religion){
           this.about = about;
           this.status = status;
           this.language = language;
           this.city = city;
           this.occupation = occupation;
           this.marriageGoals = marriageGoals;
           this.educationLevel = educationLevel;
           this.workplace = workplace;
           this.drinking = drinking;
           this.smoking = smoking;
           this.gender = gender;
           this.quote = quote;
           this.image1Url = image1Url;
           this.image2Url = image2Url;
           this.image3Url = image3Url;
           this.religion = religion;
           this.age = age;
    }

    public void setSaveInfoListener(SaveInfoListener saveInfoListener) {
        this.saveInfoListener = saveInfoListener;
    }

    public void setInfoReadyListener(InfoReadyListener infoReadyListener) {
        this.infoReadyListener = infoReadyListener;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getReligion() {
        return religion;
    }

    public String getAbout() {
        return about;
    }

    public String getCity() {
        return city;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public String getLanguage() {
        return language;
    }

    public String getMarriageGoals() {
        return marriageGoals;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getStatus() {
        return status;
    }

    public String getWorkplace() {
        return workplace;
    }


    public String getDrinking() {
        return drinking;
    }

    public Context getContext() {
        return context;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getImage1Url() {
        return image1Url;
    }

    public String getImage2Url() {
        return image2Url;
    }

    public String getImage3Url() {
        return image3Url;
    }

    public String getSmoking() {
        return smoking;
    }

    public String getQuote() {
        return quote;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public void setMarriageGoals(String marriageGoals) {
        this.marriageGoals = marriageGoals;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setDrinking(String drinking) {
        this.drinking = drinking;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setImage1Url(String image1Url) {
        this.image1Url = image1Url;
    }

    public void setImage2Url(String image2Url) {
        this.image2Url = image2Url;
    }

    public void setImage3Url(String image3Url) {
        this.image3Url = image3Url;
    }

    private Handler saveInfoHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("success")){
                    saveInfoListener.onSuccess();
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
                    int age = jsonArray.getJSONObject(0).getInt("age");
                    String city = jsonArray.getJSONObject(0).getString("city");
                    String mStatus = jsonArray.getJSONObject(0).getString("status");
                    String language = jsonArray.getJSONObject(0).getString("language");
                    String workplace = jsonArray.getJSONObject(0).getString("workplace");
                    String occupation = jsonArray.getJSONObject(0).getString("occupation");
                    String education = jsonArray.getJSONObject(0).getString("education");
                    String quote = jsonArray.getJSONObject(0).getString("quote");
                    String firstImage = jsonArray.getJSONObject(0).getString("firstImage");
                    String secondImage = jsonArray.getJSONObject(0).getString("secondImage");
                    String thirdImage = jsonArray.getJSONObject(0).getString("thirdImage");
                    String about = jsonArray.getJSONObject(0).getString("about");
                    String marriageGoals = jsonArray.getJSONObject(0).getString("marriageGoals");
                    String drinking = jsonArray.getJSONObject(0).getString("drinking");
                    String smoking = jsonArray.getJSONObject(0).getString("smoking");
                    String gender = jsonArray.getJSONObject(0).getString("gender");
                    String mReligion = jsonArray.getJSONObject(0).getString("religion");
                    CompleteProfileModel completeProfileModel = new CompleteProfileModel(about,mStatus,language,city,occupation,marriageGoals,education,workplace,drinking,smoking,gender,quote,age,firstImage,secondImage,thirdImage,mReligion);
                    infoReadyListener.onReady(completeProfileModel);
                }
                else if(status.equalsIgnoreCase("failure")){
                    infoReadyListener.onError("Error Occurred");
                }
                else{
                    infoReadyListener.onError("Error Occurred");
                }
            } catch (JSONException e) {
                infoReadyListener.onError(e.getMessage());
            }

        }
    };


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
            RequestBody requestBody = RequestBody.create(JSON,buildImageUploadJson(encodedImage,imageType));
            RequestBody requestBody1 = ProgressHelper.withProgress(requestBody, new ProgressUIListener() {
                @Override
                public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                    imageUploadDialog.updateProgress(100 * percent);
                }
                //if you don't need this method, don't override this method. It isn't an abstract method, just an empty method.
                @Override
                public void onUIProgressFinish() {
                    super.onUIProgressFinish();
                }
            });

            //post the wrapped request body
            builder.post(requestBody1);
            Call call = okHttpClient.newCall(builder.build());
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
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
                    uploadedImageUrl = jsonObject.getString("data");
                    saveInfoListener.onImageUploaded(uploadedImageUrl);
                }
                else{

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    };


    public void SaveUserInfo(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildSaveUserInfo());
            Request request = new Request.Builder()
                    .url(completeProfileUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = saveInfoHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            saveInfoHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }

    public void SaveUserAgeInfo(){
        Runnable runnable = () -> {
            String mResponse = "";
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON,buildSaveUserAgeInfo());
            Request request = new Request.Builder()
                    .url(completeProfileUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if(response != null){
                    mResponse =  response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = saveInfoHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("response", mResponse);
            msg.setData(bundle);
            saveInfoHandler.sendMessage(msg);
        };
        Thread myThread = new Thread(runnable);
        myThread.start();
    }



    private String buildSaveUserInfo(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(jsonKey,value);
            jsonObject.put("email",userEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private String buildSaveUserAgeInfo(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(jsonKey,ageValue);
            jsonObject.put("email",userEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
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


    private String buildImageUploadJson(String encodedImage, String imageType){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("image",encodedImage);
            jsonObject.put("email",userEmail);
            jsonObject.put("imageType",imageType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
