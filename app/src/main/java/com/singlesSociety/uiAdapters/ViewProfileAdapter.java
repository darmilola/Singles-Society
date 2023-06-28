package com.singlesSociety.uiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singlesSociety.R;
import com.singlesSociety.UiModels.ShowCaseModel;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int SHOWCASE_TYPE_MAIN = 1;
    private static int SHOWCASE_TYPE_QUOTE = 2;
    private static int SHOWCASE_TYPE_ABOUT_ME = 3;
    private static int SHOWCASE_TYPE_CAREER = 4;
    private static int SHOWCASE_TYPE_ABOUT_TEXT = 5;
    private static int SHOWCASE_TYPE_PICTURE = 6;
    private static int SHOWCASE_TYPE_MARRIAGE_GOALS = 7;
    Context context;
    ArrayList<ShowCaseModel> showcaseList = new ArrayList<>();

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

        if (viewType == SHOWCASE_TYPE_ABOUT_TEXT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_about_text, parent, false);
            return new ShowcaseAboutTextViewholder(view);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShowCaseModel showCaseModel = showcaseList.get(position);
        if(showCaseModel.getShowcaseType() == 1){
            ShowcaseMainItemViewHolder showcaseMainItemViewHolder = (ShowcaseMainItemViewHolder) holder;
            showcaseMainItemViewHolder.nameAge.setText(showCaseModel.getModelInfoList().get(0)+", "+showCaseModel.getModelInfoList().get(1));
            showcaseMainItemViewHolder.city.setText(showCaseModel.getModelInfoList().get(2));
            showcaseMainItemViewHolder.occupation.setText(showCaseModel.getModelInfoList().get(3));
            Glide.with(context)
                    .load(showCaseModel.getModelInfoList().get(4))
                    .placeholder(R.drawable.profileplaceholder)
                    .error(R.drawable.profileplaceholder)
                    .into(showcaseMainItemViewHolder.imageView);

        }

        if(showCaseModel.getShowcaseType() == 2){
            ShowcaseQuoteItemViewHolder showcaseQuoteItemViewHolder = (ShowcaseQuoteItemViewHolder) holder;
            showcaseQuoteItemViewHolder.textView.setText(showCaseModel.getModelInfoList().get(0));
        }

        if(showCaseModel.getShowcaseType() == 3){
            ShowcaseAboutMeItemViewHolder showcaseAboutMeItemViewHolder = (ShowcaseAboutMeItemViewHolder) holder;
            showcaseAboutMeItemViewHolder.status.setText(showCaseModel.getModelInfoList().get(0));
            showcaseAboutMeItemViewHolder.smoking.setText(showCaseModel.getModelInfoList().get(1));
            showcaseAboutMeItemViewHolder.drinking.setText(showCaseModel.getModelInfoList().get(2));
            showcaseAboutMeItemViewHolder.language.setText(showCaseModel.getModelInfoList().get(3));
            showcaseAboutMeItemViewHolder.religion.setText(showCaseModel.getModelInfoList().get(4));
        }

        if(showCaseModel.getShowcaseType() == 4){
            ShowcaseCareerItemViewHolder showcaseCareerItemViewHolder  = (ShowcaseCareerItemViewHolder) holder;
            showcaseCareerItemViewHolder.degree.setText(showCaseModel.getModelInfoList().get(0));
            showcaseCareerItemViewHolder.occupation.setText(showCaseModel.getModelInfoList().get(1));
            showcaseCareerItemViewHolder.workplace.setText(showCaseModel.getModelInfoList().get(2));
            Glide.with(context)
                    .load(showCaseModel.getModelInfoList().get(3))
                    .placeholder(R.drawable.profileplaceholder)
                    .error(R.drawable.profileplaceholder)
                    .into(showcaseCareerItemViewHolder.imageView);
        }

        if(showCaseModel.getShowcaseType() == 5){
            ShowcaseAboutTextViewholder showcaseAboutTextViewholder = (ShowcaseAboutTextViewholder) holder;
            showcaseAboutTextViewholder.textView.setText(showCaseModel.getModelInfoList().get(0));
        }

        if(showCaseModel.getShowcaseType() == 6){
            ShowcasePictureItemViewHolder showcasePictureItemViewHolder = (ShowcasePictureItemViewHolder) holder;
            Glide.with(context)
                    .load(showCaseModel.getModelInfoList().get(0))
                    .placeholder(R.drawable.profileplaceholder)
                    .error(R.drawable.profileplaceholder)
                    .into(showcasePictureItemViewHolder.imageView);
        }

        if(showCaseModel.getShowcaseType() == 7){
             ShowcaseMarriageGoalsItemViewHolder showcaseMarriageGoalsItemViewHolder = (ShowcaseMarriageGoalsItemViewHolder) holder;
             showcaseMarriageGoalsItemViewHolder.goal.setText(showCaseModel.getModelInfoList().get(0));
        }


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
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_MARRIAGE_GOALS){
            return SHOWCASE_TYPE_MARRIAGE_GOALS;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_ABOUT_TEXT){
            return SHOWCASE_TYPE_ABOUT_TEXT;
        }
        else{
            return SHOWCASE_TYPE_MAIN;
        }
    }

    public class ShowcaseMainItemViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView city,nameAge;
        Chip occupation;
        public ShowcaseMainItemViewHolder(View ItemView){
            super(ItemView);
            imageView = ItemView.findViewById(R.id.type_main_image);
            city = ItemView.findViewById(R.id.type_main_city);
            nameAge = ItemView.findViewById(R.id.type_main_name_age);
            occupation = ItemView.findViewById(R.id.type_main_occupation);
        }
    }

    public class ShowcaseAboutTextViewholder extends RecyclerView.ViewHolder{

        TextView textView;
        public ShowcaseAboutTextViewholder(View ItemView){
            super(ItemView);
            textView = ItemView.findViewById(R.id.type_about_text_text);
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

    public class ShowcaseMarriageGoalsItemViewHolder extends RecyclerView.ViewHolder{


        Chip goal;
        public ShowcaseMarriageGoalsItemViewHolder(View ItemView){
            super(ItemView);
            goal = ItemView.findViewById(R.id.type_goals_year);
        }


    }



    public class ShowcaseAboutMeItemViewHolder extends RecyclerView.ViewHolder{

        Chip status,smoking,drinking,language,religion;

        public ShowcaseAboutMeItemViewHolder(View ItemView){
            super(ItemView);
            status = ItemView.findViewById(R.id.type_about_status);
            smoking = ItemView.findViewById(R.id.type_about_smoking);
            drinking = ItemView.findViewById(R.id.type_about_drinking);
            language = ItemView.findViewById(R.id.type_about_language);
            religion = ItemView.findViewById(R.id.type_about_religion);
        }

    }

    public class ShowcasePictureItemViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public ShowcasePictureItemViewHolder(View ItemView){
            super(ItemView);
            imageView = ItemView.findViewById(R.id.type_picture_image);
        }
    }



    public class ShowcaseQuoteItemViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public ShowcaseQuoteItemViewHolder(View ItemView){
            super(ItemView);
            textView = ItemView.findViewById(R.id.type_quote_text);

        }



    }

    public class ShowcaseCareerItemViewHolder extends RecyclerView.ViewHolder {

        Chip degree,occupation,workplace;
        ImageView imageView;
        public ShowcaseCareerItemViewHolder(View ItemView){
            super(ItemView);
            degree = ItemView.findViewById(R.id.type_career_education);
            occupation = ItemView.findViewById(R.id.type_career_occupation);
            workplace = ItemView.findViewById(R.id.type_career_workplace);
            imageView = ItemView.findViewById(R.id.type_career_image);
        }



    }



}


