package com.singlesSociety.uiAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.singlesSociety.R;
import com.singlesSociety.UiModels.ExploreHeader;
import com.mindinventory.CircularAdapter;

import java.util.ArrayList;

public class ExploreHeaderAdapter extends CircularAdapter {

    ArrayList<ExploreHeader> exploreHeaderItems;

    public ExploreHeaderAdapter(ArrayList<ExploreHeader> exploreHeaders){
        this.exploreHeaderItems = exploreHeaders;
    }

    @NonNull
    @Override
    public ExploreHeaderAdapter.ItemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_header_type_main, parent, false);
        return new ItemViewholder(view);
    }


    @Override
    public int getItemCount() {
        return exploreHeaderItems.size();
    }

    @Override
    public void bindItemViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, Object o, int i, int i1) {
         // viewHolder.ItemView
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder createItemViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.explore_header_type_main, viewGroup, false);
        return new ItemViewholder(view);
    }

    public class ItemViewholder extends RecyclerView.ViewHolder{

        ImageView headerView;
        public ItemViewholder(View ItemView){
            super(ItemView);
            headerView = ItemView.findViewById(R.id.headerView);
        }

    }
}
