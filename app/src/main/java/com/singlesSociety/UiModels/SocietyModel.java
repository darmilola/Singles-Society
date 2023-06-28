package com.singlesSociety.UiModels;

import java.util.ArrayList;

public class SocietyModel {

    private CommunityPostModel communityPostModel;
    private int itemViewType;
    private ArrayList<PreviewProfileModel> userProfileToPreview;
    private ArrayList<String> usersLikedList;



    public SocietyModel(ArrayList<PreviewProfileModel> userProfileToPreview, ArrayList<String> usersLikedList, int itemViewType){
        this.userProfileToPreview = userProfileToPreview;
        this.usersLikedList = usersLikedList;
        this.itemViewType = itemViewType;
    }

    public SocietyModel(CommunityPostModel communityPostModel, int itemViewType){
        this.communityPostModel = communityPostModel;
        this.itemViewType = itemViewType;
    }

    public SocietyModel(int itemViewType){
        this.itemViewType = itemViewType;
    }


    public ArrayList<PreviewProfileModel> getUserProfileToPreview() {
        return userProfileToPreview;
    }

    public ArrayList<String> getUsersLikedList() {
        return usersLikedList;
    }


    public int getItemViewType() {
        return itemViewType;
    }
}
