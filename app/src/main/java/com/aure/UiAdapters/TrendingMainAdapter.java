package com.aure.UiAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aure.R;
import com.aure.UiModels.LinePagerIndicator;
import com.aure.UiModels.ShowCaseMainModel;





import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TrendingMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<ShowCaseMainModel> showCaseMainModelArrayList;
    private ShowCaseAdapter showCaseAdapter;

    private static int TYPE_MAIN = 0;
    private static int TYPE_POST = 1;


    private CommunityPostItemViewHolder communityPostItemViewHolder;

    public TrendingMainAdapter(Context context, ArrayList<ShowCaseMainModel> showCaseMainModelArrayList){
        this.context = context;
        this.showCaseMainModelArrayList = showCaseMainModelArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == TYPE_MAIN){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showcase_layout, parent, false);
            return new ShowcaseMainGeneralItemViewHolder(view);
        }

        if(viewType == TYPE_POST){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_post, parent, false);
            communityPostItemViewHolder = new CommunityPostItemViewHolder(view);
            return communityPostItemViewHolder;
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position == TYPE_MAIN){
            ShowcaseMainGeneralItemViewHolder holder1 = (ShowcaseMainGeneralItemViewHolder)holder;
            showCaseAdapter = new ShowCaseAdapter(context,showCaseMainModelArrayList.get(position).getShowCaseModelArrayList());
            LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            holder1.showcaseRecyclerview.setLayoutManager(layoutManager);
            holder1.showcaseRecyclerview.setAdapter(showCaseAdapter);
        }
       else if(position == TYPE_POST){
            CommunityPostItemViewHolder communityPostItemViewHolder = (CommunityPostItemViewHolder) holder;
        }
    }



    @Override
    public int getItemCount() {
        return showCaseMainModelArrayList.size();
    }

    public class ShowcaseMainGeneralItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        RecyclerView showcaseRecyclerview;

        public ShowcaseMainGeneralItemViewHolder(View ItemView){
            super(ItemView);
            showcaseRecyclerview = ItemView.findViewById(R.id.showcase_recyclerview);
            showcaseRecyclerview.addItemDecoration(new LinePagerIndicator());
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

    @Override
    public int getItemViewType(int position) {
        if(showCaseMainModelArrayList.get(position).getItemViewType() == TYPE_MAIN){
            return TYPE_MAIN;
        }

        return TYPE_POST;
    }


    public class CommunityPostItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public CommunityPostItemViewHolder(View ItemView){
            super(ItemView);
          //  ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }


}
