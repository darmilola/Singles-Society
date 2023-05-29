package com.aure.UiModels;

import java.util.ArrayList;

public class ShowCaseMainModel {

    private ArrayList<ShowCaseModel> showCaseModelArrayList = null;
    private CommunityPostModel communityPostModel;
    private int itemViewType;

    private static final int STATIC_SHOWCASE = 102;


    public ShowCaseMainModel(ArrayList<ShowCaseModel> showCaseModelArrayList, int itemViewType, boolean isTypeShowcase){
        this.showCaseModelArrayList = showCaseModelArrayList;
        this.itemViewType = itemViewType;
    }

    public ShowCaseMainModel(CommunityPostModel communityPostModel, int itemViewType){
        this.communityPostModel = communityPostModel;
        this.itemViewType = itemViewType;
    }

    public ArrayList<ShowCaseModel> getShowCaseModelArrayList() {
           return showCaseModelArrayList;
    }

    public void setShowCaseModelArrayList(ArrayList<ShowCaseModel> showCaseModelArrayList) {
        this.showCaseModelArrayList = showCaseModelArrayList;
    }

    public int getItemViewType() {
        return itemViewType;
    }
}
