package com.aure.UiModels;

import java.util.ArrayList;

public class ShowCaseModel {
    private int showcaseType;
    private ArrayList<String> modelInfoList;

    public ShowCaseModel(ArrayList<String> modelInfoList,int showcaseType){
           this.modelInfoList = modelInfoList;
           this.showcaseType = showcaseType;
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

    public void setShowcaseType(int showcaseType) {
        this.showcaseType = showcaseType;
    }
}
