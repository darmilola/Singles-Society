package com.aure.UiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aure.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarketplaceProductDetailAdapter extends RecyclerView.Adapter<MarketplaceProductDetailAdapter.MarketplaceProductDetailViewholder> {


    private Context context;
    private ArrayList<String> productDetailList = new ArrayList<>();

    public MarketplaceProductDetailAdapter(Context context,ArrayList<String> productDetailList){
        this.context = context;
        this.productDetailList = productDetailList;
    }

    @NonNull
    @Override
    public MarketplaceProductDetailViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_detail_recycler_item, parent, false);
        return new MarketplaceProductDetailViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketplaceProductDetailViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productDetailList.size();
    }

    public class MarketplaceProductDetailViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MarketplaceProductDetailViewholder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

}
