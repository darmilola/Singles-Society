package com.aure.UiAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aure.R;
import com.aure.UiModels.ProductImageModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AddProductImageAdapter extends RecyclerView.Adapter<AddProductImageAdapter.itemViewHolder> {

    ArrayList<ProductImageModel> imagesList;
    Context context;


    public AddProductImageAdapter(ArrayList<ProductImageModel> imagesList, Context context){
        this.imagesList = imagesList;
        this.context = context;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_image_recycler_item, parent, false);
        return new itemViewHolder(view2);
    }

    public void addItem(ProductImageModel imageModel){
        imagesList.add(imageModel);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        Glide.with(context)
                .load(imagesList.get(position).getImageUrl())
                .placeholder(R.drawable.background_image)
                .error(R.drawable.background_image)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        public itemViewHolder(View ItemView){
            super(ItemView);
            imageView = ItemView.findViewById(R.id.recycler_image_item);
            ItemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {

        }
    }
}
