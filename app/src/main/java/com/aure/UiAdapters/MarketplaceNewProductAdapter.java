package com.aure.UiAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aure.MarketPlaceDetailView;
import com.aure.MyListingDetails;
import com.aure.ProductDetails;
import com.aure.R;
import com.aure.UiModels.ListingModel;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarketplaceNewProductAdapter  extends RecyclerView.Adapter<MarketplaceNewProductAdapter.MarketplaceNewProductViewHolder> {


    private Context context;
    private ArrayList<ListingModel> newProductList = new ArrayList<>();

    public MarketplaceNewProductAdapter(Context context,ArrayList<ListingModel> newProductList){
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
        ListingModel listingModel = newProductList.get(position);
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
    }

    public void addItems(ArrayList<ListingModel> listingModelArrayList){
           listingModelArrayList.addAll(listingModelArrayList);
           notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newProductList.size();
    }

    public class MarketplaceNewProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView name,description,price;
        public MarketplaceNewProductViewHolder(View ItemView){
            super(ItemView);
            imageView = ItemView.findViewById(R.id.listing_item_image);
            name = ItemView.findViewById(R.id.listing_item_name);
            description = ItemView.findViewById(R.id.list_item_description);
            price = ItemView.findViewById(R.id.listing_item_price);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ProductDetails.class);
            intent.putExtra("info",newProductList.get(getAdapterPosition()));
            context.startActivity(intent);
        }

    }

}
