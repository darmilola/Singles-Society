package com.singlesSociety.UiModels;

import java.util.ArrayList;

public class ShowCaseModel {
    private int showcaseType;
    private ArrayList<String> modelInfoList;

    private String userId;

    private ArrayList<String> likeList;

    public ShowCaseModel(ArrayList<String> modelInfoList,int showcaseType, ArrayList<String> likeList, String userId){
           this.modelInfoList = modelInfoList;
           this.showcaseType = showcaseType;
           this.likeList = likeList;
           this.userId = userId;
    }

    public ArrayList<String> getModelInfoList() {
        return modelInfoList;
    }

    public void setModelInfoList(ArrayList<String> modelInfoList) {
        this.modelInfoList = modelInfoList;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ShowCaseModel(int showcaseType){
        this.showcaseType = showcaseType;
    }

    public int getShowcaseType() {
        return showcaseType;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<String> getLikeList() {
        return likeList;
    }

    public void setShowcaseType(int showcaseType) {
        this.showcaseType = showcaseType;
    }
}
