package com.singlesSociety.uiAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singlesSociety.ProductDetails;
import com.singlesSociety.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarketplaceBestSellerAdapter extends RecyclerView.Adapter<MarketplaceBestSellerAdapter.MarketplaceBestSellerViewholder > {


    private Context context;
    private ArrayList<String> bestSellersList = new ArrayList<>();

    public MarketplaceBestSellerAdapter(Context context,ArrayList<String> bestSellersList){
        this.context = context;
        this.bestSellersList = bestSellersList;
    }

    @NonNull
    @Override
    public MarketplaceBestSellerViewholder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketplace_item, parent, false);
        return new MarketplaceBestSellerViewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketplaceBestSellerViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return bestSellersList.size();
    }

    public class MarketplaceBestSellerViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MarketplaceBestSellerViewholder (View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, ProductDetails.class));
        }

    }

}
