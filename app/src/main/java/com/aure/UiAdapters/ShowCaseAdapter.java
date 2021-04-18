package com.aure.UiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aure.R;
import com.aure.UiModels.ShowCaseModel;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ShowCaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    ArrayList<ShowCaseModel> showcaseList = new ArrayList<>();
    private static int SHOWCASE_TYPE_MAIN = 1;
    private static int SHOWCASE_TYPE_CAREER = 2;
    private static int SHOWCASE_TYPE_QUOTE = 3;
    private static int SHOWCASE_TYPE_PICTURE = 4;
    private static int SHOWCASE_TYPE_ABOUT_ME = 5;
    private static int SHOWCASE_TYPE_MY_RELIGION = 6;
    private static int SHOWCASE_TYPE_MARRIAGE_GOALS = 7;
    private static int SHOWCASE_TYPE_TAKE_ACTION = 8;


    public ShowCaseAdapter(Context context, ArrayList<ShowCaseModel> showCaseList){
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


        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position == SHOWCASE_TYPE_MAIN) {
            ShowcaseMainItemViewHolder mHolder = (ShowcaseMainItemViewHolder)holder;
            //ArrayList<JcAudio> jcAudios = new ArrayList<>();
           // jcAudios.add(JcAudio.createFromURL("user audio", ));jkjk
            //mHolder.userPlayerView.initPlaylist(jcAudios, null);
            mHolder.userPlayerView.playAudio(JcAudio.createFromURL("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"));
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
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_MY_RELIGION){
            return SHOWCASE_TYPE_MY_RELIGION;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_MARRIAGE_GOALS){
            return SHOWCASE_TYPE_MARRIAGE_GOALS;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_TAKE_ACTION){
            return SHOWCASE_TYPE_TAKE_ACTION;
        }
        else{
            return SHOWCASE_TYPE_MAIN;
        }
    }


    public class ShowcaseMainItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       JcPlayerView userPlayerView;
        public ShowcaseMainItemViewHolder(View ItemView){
            super(ItemView);
            userPlayerView = ItemView.findViewById(R.id.user_info_player);
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
