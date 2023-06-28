package com.singlesSociety.uiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.singlesSociety.R;
import com.singlesSociety.UiModels.ExploreItem;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class ExploreCommunityAdapter extends RecyclerView.Adapter<ExploreCommunityAdapter.ItemViewholder> {

    ArrayList<ExploreItem> exploreItems;
    private Function0<Unit> spacesClickedListener;
    Context context;

    public ExploreCommunityAdapter(ArrayList<ExploreItem> exploreItems, Context context) {
            this.exploreItems = exploreItems;
            this.context = context;
    }

    public void setSpacesClickedListener(Function0<Unit> spacesClickedListener) {
        this.spacesClickedListener = spacesClickedListener;
    }

    @NonNull
    @Override
    public ItemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_item, parent, false);
        return new ItemViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return exploreItems.size();
    }

    public class ItemViewholder extends RecyclerView.ViewHolder{

        TextView textView;
        public ItemViewholder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            spacesClickedListener.invoke();
                        }
                    }
            );
        }

    }
}