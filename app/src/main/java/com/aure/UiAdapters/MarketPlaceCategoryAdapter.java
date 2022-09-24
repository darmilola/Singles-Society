package com.aure.UiAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aure.MarketPlaceDetailView;
import com.aure.ProductDetails;
import com.aure.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarketPlaceCategoryAdapter extends RecyclerView.Adapter<MarketPlaceCategoryAdapter.CategoryViewholder> {


    private Context context;
    private ArrayList<String> categoryList = new ArrayList<>();

    public MarketPlaceCategoryAdapter(Context context,ArrayList<String> categoryList){
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketplace_category_item, parent, false);
        return new CategoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CategoryViewholder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, MarketPlaceDetailView.class));
        }

    }

}
