package com.aure.UiModels;

import java.util.ArrayList;

public class ShowCaseModel {
    private int showcaseType;
    private ArrayList<String> modelInfoList;

    private ArrayList<String> likeList;

    public ShowCaseModel(ArrayList<String> modelInfoList,int showcaseType, ArrayList<String> likeList){
           this.modelInfoList = modelInfoList;
           this.showcaseType = showcaseType;
           this.likeList = likeList;
    }

    public ArrayList<String> getModelInfoList() {
        return modelInfoList;
    }

    public void setModelInfoList(ArrayList<String> modelInfoList) {
        this.modelInfoList = modelInfoList;
    }

    public ShowCaseModel(int showcaseType){
        this.showcaseType = showcaseType;
    }

    public int getShowcaseType() {
        return showcaseType;
    }

    public ArrayList<String> getLikeList() {
        return likeList;
    }

    public void setShowcaseType(int showcaseType) {
        this.showcaseType = showcaseType;
    }
}
