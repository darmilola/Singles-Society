package com.aure.UiAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aure.ProductDetails;
import com.aure.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarketplaceViewAllAdapter extends RecyclerView.Adapter<MarketplaceViewAllAdapter.MarketplaceViewViewAllHolder> {


    private Context context;
    private ArrayList<String> viewAllList = new ArrayList<>();

    public MarketplaceViewAllAdapter(Context context,ArrayList<String> viewAllList){
        this.context = context;
        this.viewAllList = viewAllList;
    }

    @NonNull
    @Override
    public MarketplaceViewViewAllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketplace_item, parent, false);
        return new MarketplaceViewViewAllHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketplaceViewViewAllHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return viewAllList.size();
    }

    public class MarketplaceViewViewAllHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MarketplaceViewViewAllHolder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            context.startActivity(new Intent(context, ProductDetails.class));
        }

    }

}
