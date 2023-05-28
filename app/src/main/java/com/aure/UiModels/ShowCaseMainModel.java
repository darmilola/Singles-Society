package com.aure.UiModels;

import java.util.ArrayList;

public class ShowCaseMainModel {

    private ArrayList<ShowCaseModel> showCaseModelArrayList;
    private ArrayList<CommunityPostModel> communityPostModelArrayList;
    private int itemViewType;

    private static final int STATIC_SHOWCASE = 102;


    public ShowCaseMainModel(ArrayList<ShowCaseModel> showCaseModelArrayList, int itemViewType, int typeShowcase){
        this.showCaseModelArrayList = showCaseModelArrayList;
        this.itemViewType = itemViewType;
    }

    public ShowCaseMainModel(ArrayList<CommunityPostModel> communityPostModelArrayList, int itemViewType){
        this.communityPostModelArrayList = communityPostModelArrayList;
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
