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

public class MarketplaceNewProductAdapter  extends RecyclerView.Adapter<MarketplaceNewProductAdapter.MarketplaceNewProductViewHolder> {


    private Context context;
    private ArrayList<String> newProductList = new ArrayList<>();

    public MarketplaceNewProductAdapter(Context context,ArrayList<String> newProductList){
        this.context = context;
        this.newProductList = newProductList;
    }

    @NonNull
    @Override
    public MarketplaceNewProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketplace_item, parent, false);
        return new MarketplaceNewProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketplaceNewProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return newProductList.size();
    }

    public class MarketplaceNewProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MarketplaceNewProductViewHolder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, MarketPlaceDetailView.class));
        }

    }

}
