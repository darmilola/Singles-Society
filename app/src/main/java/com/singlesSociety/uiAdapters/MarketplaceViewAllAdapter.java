package com.singlesSociety.uiAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singlesSociety.ProductDetails;
import com.singlesSociety.R;
import com.singlesSociety.UiModels.ListingModel;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarketplaceViewAllAdapter extends RecyclerView.Adapter<MarketplaceViewAllAdapter.MarketplaceViewViewAllHolder> {


    private Context context;
    private ArrayList<ListingModel> listingModels = new ArrayList<>();

    public MarketplaceViewAllAdapter(Context context,ArrayList<ListingModel> listingModelArrayList){
        this.context = context;
        this.listingModels = listingModelArrayList;
    }

    @NonNull
    @Override
    public MarketplaceViewViewAllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketplace_item, parent, false);
        return new MarketplaceViewViewAllHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketplaceViewViewAllHolder holder, int position) {
        ListingModel listingModel = listingModels.get(position);
        //holder.price.setText(listingModel.getPrice());
        Locale NigerianLocale = new Locale("en","ng");
        String unFormattedPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(listingModel.getPrice()));
        String formattedPrice = unFormattedPrice.replaceAll("\\.00","");
        holder.price.setText(formattedPrice);
        holder.description.setText(listingModel.getDescription());
        holder.name.setText(listingModel.getName());
        Glide.with(context)
                .load(listingModel.getDisplayImage())
                .placeholder(R.drawable.profileplaceholder)
                .error(R.drawable.profileplaceholder)
                .into(holder.imageView);
        if(listingModel.getIsSponsored().equalsIgnoreCase("true")){
            holder.badge.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return listingModels.size();
    }

    public class MarketplaceViewViewAllHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         ImageView imageView;
         TextView name,description,price;
         LinearLayout badge;

        public MarketplaceViewViewAllHolder(View ItemView){
            super(ItemView);
             imageView = ItemView.findViewById(R.id.listing_item_image);
            name = ItemView.findViewById(R.id.listing_item_name);
            description = ItemView.findViewById(R.id.list_item_description);
            price = ItemView.findViewById(R.id.listing_item_price);
            badge = ItemView.findViewById(R.id.sponsored_badge);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ProductDetails.class);
            intent.putExtra("info",listingModels.get(getAdapterPosition()));
            context.startActivity(intent);
        }

    }

}
