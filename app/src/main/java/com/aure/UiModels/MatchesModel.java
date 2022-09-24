package com.aure.UiModels;

public class MatchesModel {

    private String userId;
    private String userFirstname;
    private String userLastname;
    private String userImageUrl;

    public MatchesModel(String userId, String userFirstname, String userLastname, String userImageUrl){
        this.userId = userId;
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
        this.userImageUrl = userImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

}
