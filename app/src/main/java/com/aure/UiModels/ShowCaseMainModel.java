package com.aure.UiModels;

import java.util.ArrayList;

public class ShowCaseMainModel {

    private ArrayList<ShowCaseModel> showCaseModelArrayList;
    private ShowcaseMetadata showcaseMetadata;

    public ShowCaseMainModel(ArrayList<ShowCaseModel> showCaseModelArrayList, ShowcaseMetadata showcaseMetadata){
        this.showCaseModelArrayList = showCaseModelArrayList;
        this.showcaseMetadata = showcaseMetadata;
    }

    public ArrayList<ShowCaseModel> getShowCaseModelArrayList() {
        return showCaseModelArrayList;
    }

    public void setShowCaseModelArrayList(ArrayList<ShowCaseModel> showCaseModelArrayList) {
        this.showCaseModelArrayList = showCaseModelArrayList;
    }

    public ShowcaseMetadata getShowcaseMetadata() {
        return showcaseMetadata;
    }

    public void setShowcaseMetadata(ShowcaseMetadata showcaseMetadata) {
        this.showcaseMetadata = showcaseMetadata;
    }
}
