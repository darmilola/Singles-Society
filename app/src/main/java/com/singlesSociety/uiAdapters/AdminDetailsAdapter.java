package com.singlesSociety.uiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.singlesSociety.R;
import com.singlesSociety.UiModels.ListingModel;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdminDetailsAdapter extends RecyclerView.Adapter<AdminDetailsAdapter.MarketplaceViewViewAllHolder> {


    private Context context;
    private ArrayList<ListingModel> listingModels = new ArrayList<>();
    private ItemClickedListener itemClickedListener;

    public interface ItemClickedListener{
        void onItemClicked(int position);
    }

    public AdminDetailsAdapter(Context context,ArrayList<ListingModel> listingModelArrayList){
        this.context = context;
        this.listingModels = listingModelArrayList;
    }

    public void setItemClickedListener(ItemClickedListener itemClickedListener) {
        this.itemClickedListener = itemClickedListener;
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
            holder.sponsoredBadge.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return listingModels.size();
    }

    public class MarketplaceViewViewAllHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView name,description,price;
        LinearLayout sponsoredBadge;
        public MarketplaceViewViewAllHolder(View ItemView){
            super(ItemView);
            imageView = ItemView.findViewById(R.id.listing_item_image);
            name = ItemView.findViewById(R.id.listing_item_name);
            description = ItemView.findViewById(R.id.list_item_description);
            price = ItemView.findViewById(R.id.listing_item_price);
            sponsoredBadge = ItemView.findViewById(R.id.sponsored_badge);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            itemClickedListener.onItemClicked(getAdapterPosition());

        }

    }

}
