package com.aure.UiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aure.R;
import com.aure.UiModels.ShowCaseModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int SHOWCASE_TYPE_MAIN = 1;
    private static int SHOWCASE_TYPE_CAREER = 2;
    private static int SHOWCASE_TYPE_QUOTE = 3;
    private static int SHOWCASE_TYPE_PICTURE = 4;
    private static int SHOWCASE_TYPE_ABOUT_ME = 5;
    private static int SHOWCASE_TYPE_MY_RELIGION = 6;
    private static int SHOWCASE_TYPE_MARRIAGE_GOALS = 7;
    private static int SHOWCASE_TYPE_TAKE_ACTION = 8;
    private static int SHOWCASE_TYPE_ABOUT_TEXT = 9;
    private static int SHOWCASE_TYPE_TYPE = 10;
    private static int SHOWCASE_TYPE_ADDITIONAL_INFO = 11;
    Context context;
    ArrayList<ShowCaseModel> showcaseList = new ArrayList<>();
    private ViewAddedListener listener;

    public interface ViewAddedListener{
        void onViewAdded(int size);
        void onUserSwiped();
        void onBottomReached();
    }

    public void setListener(ViewAddedListener listener) {
        this.listener = listener;
    }

    public ViewProfileAdapter(Context context, ArrayList<ShowCaseModel> showCaseList){
        this.context = context;
        this.showcaseList = showCaseList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SHOWCASE_TYPE_MAIN) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_main, parent, false);
            return new ShowcaseMainItemViewHolder(view);
        }
        if (viewType == SHOWCASE_TYPE_QUOTE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_quote, parent, false);
            return new ShowcaseQuoteItemViewHolder(view);
        }
        if (viewType == SHOWCASE_TYPE_CAREER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_career, parent, false);
            return new ShowcaseCareerItemViewHolder(view);
        }
        if (viewType == SHOWCASE_TYPE_PICTURE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_picture, parent, false);
            return new ShowcasePictureItemViewHolder(view);
        }
        if (viewType == SHOWCASE_TYPE_ABOUT_ME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_about_me, parent, false);
            return new ShowcaseAboutMeItemViewHolder(view);
        }

        if (viewType == SHOWCASE_TYPE_MARRIAGE_GOALS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_marriage_goals, parent, false);
            return new ShowcaseMarriageGoalsItemViewHolder(view);
        }

        if (viewType == SHOWCASE_TYPE_MY_RELIGION) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_religion, parent, false);
            return new ShowcaseMyReligionItemViewHolder(view);
        }
        if (viewType == SHOWCASE_TYPE_TAKE_ACTION) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_fav_report_block, parent, false);
            return new ShowcaseTakeActionItemViewHolder(view);
        }

        if (viewType == SHOWCASE_TYPE_ABOUT_TEXT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_about_text, parent, false);
            return new ShowcaseAboutTextViewholder(view);
        }
        if (viewType == SHOWCASE_TYPE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_type, parent, false);
            return new ShowcaseTypeTypeViewholder(view);
        }
        if (viewType == SHOWCASE_TYPE_ADDITIONAL_INFO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_additional_info, parent, false);
            return new ShowcaseAdditionalInfoViewholder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return showcaseList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_MAIN){
            return SHOWCASE_TYPE_MAIN;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_CAREER){
            return SHOWCASE_TYPE_CAREER;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_QUOTE){
            return SHOWCASE_TYPE_QUOTE;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_PICTURE){
            return SHOWCASE_TYPE_PICTURE;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_ABOUT_ME){
            return SHOWCASE_TYPE_ABOUT_ME;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_MY_RELIGION){
            return SHOWCASE_TYPE_MY_RELIGION;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_MARRIAGE_GOALS){
            return SHOWCASE_TYPE_MARRIAGE_GOALS;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_TAKE_ACTION){
            return SHOWCASE_TYPE_TAKE_ACTION;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_TYPE){
            return SHOWCASE_TYPE_TYPE;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_ABOUT_TEXT){
            return SHOWCASE_TYPE_ABOUT_TEXT;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_ADDITIONAL_INFO){
            return SHOWCASE_TYPE_ADDITIONAL_INFO;
        }
        else{
            return SHOWCASE_TYPE_MAIN;
        }
    }


    public class ShowcaseMainItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ShowcaseMainItemViewHolder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

    public class ShowcaseAboutTextViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ShowcaseAboutTextViewholder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }

    }
    public class ShowcaseTypeTypeViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ShowcaseTypeTypeViewholder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }
    public class ShowcaseAdditionalInfoViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ShowcaseAdditionalInfoViewholder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }



    public class ShowcaseTakeActionItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        public ShowcaseTakeActionItemViewHolder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

    public class ShowcaseMarriageGoalsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ShowcaseMarriageGoalsItemViewHolder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

    public class ShowcaseMyReligionItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ShowcaseMyReligionItemViewHolder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }


    public class ShowcaseAboutMeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ShowcaseAboutMeItemViewHolder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

    public class ShowcasePictureItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ShowcasePictureItemViewHolder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }



    public class ShowcaseQuoteItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ShowcaseQuoteItemViewHolder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

    public class ShowcaseCareerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ShowcaseCareerItemViewHolder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }


}


