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

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PreviewProfileModel implements Parcelable {

    private String firstname,about,status,language,city,occupation,marriageGoals,educationLevel,workplace,drinking,smoking,gender,quote,religion;
    private int age;

    private String isProfileCompleted,isSubscribed,lastname,imageUrl,phonenumber;
    private String baseUrl = new URL().getBaseUrl();
    private String getProfileUrl = baseUrl+"users/search";
    private Context context;
    private String image1Url,image2Url,image3Url;
    private InfoReadyListener infoReadyListener;
    private String userEmail,userId;
    private String isMatched;

    private ArrayList<String> likeIds;

    public PreviewProfileModel(String userId,String firstname,String about, String status, String language, String city, String occupation, String marriageGoals, String educationLevel, String workplace, String drinking, String smoking, String gender, String quote, int age, String image1Url, String image2Url, String image3Url,String religion, String isMatched){
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
        this.firstname = firstname;
        this.userId = userId;
        this.isMatched = isMatched;
    }

    public PreviewProfileModel(String isProfileCompleted, String isSubscribed, String firstname, String lastname, String imageUrl, String phonenumber, String about, String status, String language, String city, String occupation, String marriageGoals, String educationLevel, String workplace, String drinking, String smoking, String gender, String quote, int age, String image1Url, String image2Url, String image3Url, String religion, String isMatched, String userId, ArrayList<String> likeIds){
        this.isProfileCompleted = isProfileCompleted;
        this.isSubscribed = isSubscribed;
        this.lastname = lastname;
        this.imageUrl = imageUrl;
        this.phonenumber   = phonenumber;
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
        this.firstname = firstname;
        this.userId = userId;
        this.isMatched = isMatched;
        this.likeIds = likeIds;
    }



    public PreviewProfileModel(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        userEmail = preferences.getString("userEmail","");
    }

    protected PreviewProfileModel(Parcel in) {
        firstname = in.readString();
        about = in.readString();
        status = in.readString();
        language = in.readString();
        city = in.readString();
        occupation = in.readString();
        marriageGoals = in.readString();
        educationLevel = in.readString();
        workplace = in.readString();
        drinking = in.readString();
        smoking = in.readString();
        gender = in.readString();
        quote = in.readString();
        religion = in.readString();
        age = in.readInt();
        isProfileCompleted = in.readString();
        isSubscribed = in.readString();
        lastname = in.readString();
        imageUrl = in.readString();
        phonenumber = in.readString();
        baseUrl = in.readString();
        getProfileUrl = in.readString();
        image1Url = in.readString();
        image2Url = in.readString();
        image3Url = in.readString();
        userEmail = in.readString();
        userId = in.readString();
        isMatched = in.readString();
        likeIds = in.createStringArrayList();
    }

    public static final Creator<PreviewProfileModel> CREATOR = new Creator<PreviewProfileModel>() {
        @Override
        public PreviewProfileModel createFromParcel(Parcel in) {
            return new PreviewProfileModel(in);
        }

        @Override
        public PreviewProfileModel[] newArray(int size) {
            return new PreviewProfileModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstname);
        parcel.writeString(about);
        parcel.writeString(status);
        parcel.writeString(language);
        parcel.writeString(city);
        parcel.writeString(occupation);
        parcel.writeString(marriageGoals);
        parcel.writeString(educationLevel);
        parcel.writeString(workplace);
        parcel.writeString(drinking);
        parcel.writeString(smoking);
        parcel.writeString(gender);
        parcel.writeString(quote);
        parcel.writeString(religion);
        parcel.writeInt(age);
        parcel.writeString(isProfileCompleted);
        parcel.writeString(isSubscribed);
        parcel.writeString(lastname);
        parcel.writeString(imageUrl);
        parcel.writeString(phonenumber);
        parcel.writeString(baseUrl);
        parcel.writeString(getProfileUrl);
        parcel.writeString(image1Url);
        parcel.writeString(image2Url);
        parcel.writeString(image3Url);
        parcel.writeString(userEmail);
        parcel.writeString(userId);
        parcel.writeString(isMatched);
        parcel.writeStringList(likeIds);
    }

    public interface InfoReadyListener{
        void onReady(PreviewProfileModel previewProfileModel);
        void onError(String message);
    }

    public String getIsProfileCompleted() {
        return isProfileCompleted;
    }

    public String getIsSubscribed() {
        return isSubscribed;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getReligion() {
        return religion;
    }

    public String getAbout() {
        return about;
    }

    public String getIsMatched() {
        return isMatched;
    }

    public String getCity() {
        return city;
    }

    public String getUserId() {
        return userId;
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

    public void setInfoReadyListener(InfoReadyListener infoReadyListener) {
        this.infoReadyListener = infoReadyListener;
    }

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
                    String userId = jsonArray.getJSONObject(0).getString("email");
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
                    String isMatched = jsonArray.getJSONObject(0).getString("isMatched");
                    String mReligion = jsonArray.getJSONObject(0).getString("religion");
                    String firstname = jsonArray.getJSONObject(0).getString("firstname");
                    PreviewProfileModel previewProfileModel = new PreviewProfileModel(userId,firstname,about,mStatus,language,city,occupation,marriageGoals,education,workplace,drinking,smoking,gender,quote,age,firstImage,secondImage,thirdImage,mReligion,isMatched);
                    infoReadyListener.onReady(previewProfileModel);
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

    private String buildGetUserInfo(String email){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


}
