package com.aure.UiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aure.R;
import com.aure.UiModels.MatchesModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MatchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static int TYPE_LIKES = 0;
    private static int TYPE_MATCHES  = 1;
    private Context context;
    private ArrayList<MatchesModel> matchesList = new ArrayList<>();


    public MatchesAdapter(Context context,ArrayList<MatchesModel> matchesList){
        this.context = context;
        this.matchesList = matchesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_LIKES) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_likes_layout, parent, false);
            return new LikesViewholder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_item, parent, false);
            return new MatchesViewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(matchesList.get(position).getType() == TYPE_LIKES){
            return TYPE_LIKES;
        }
        return TYPE_MATCHES;
    }

    public class MatchesViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MatchesViewholder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

    public class LikesViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public LikesViewholder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

}
