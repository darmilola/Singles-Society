package com.aure.UiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aure.R;
import com.aure.UiModels.ShowCaseModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ShowCaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    ArrayList<ShowCaseModel> showcaseList = new ArrayList<>();
    private static int SHOWCASE_TYPE_MAIN = 1;


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

        private void populateMainChipGroup(){
            Chip occupation = new Chip(context);
            occupation.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
            occupation.setCheckable(false);

        }
    }
}
