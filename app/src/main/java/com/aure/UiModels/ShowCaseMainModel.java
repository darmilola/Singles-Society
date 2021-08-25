package com.aure.UiModels;

import java.util.ArrayList;

public class ShowCaseMainModel {

    private ArrayList<ShowCaseModel> showCaseModelArrayList;


    public ShowCaseMainModel(ArrayList<ShowCaseModel> showCaseModelArrayList){
        this.showCaseModelArrayList = showCaseModelArrayList;
    }

    public ArrayList<ShowCaseModel> getShowCaseModelArrayList() {
        return showCaseModelArrayList;
    }

    public void setShowCaseModelArrayList(ArrayList<ShowCaseModel> showCaseModelArrayList) {
        this.showCaseModelArrayList = showCaseModelArrayList;
    }

}
