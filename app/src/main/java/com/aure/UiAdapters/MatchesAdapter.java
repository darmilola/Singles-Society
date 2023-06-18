package com.aure.UiAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aure.ChatActivity;
import com.aure.R;
import com.aure.UiModels.MatchesModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<MatchesModel> matchesModelArrayList = new ArrayList<>();

    public MatchesAdapter(Context context,ArrayList<MatchesModel> matchesModelArrayList){
        this.context = context;
        this.matchesModelArrayList = matchesModelArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
          MatchesModel matchesModel = matchesModelArrayList.get(position);
          Glide.with(context)
                .load("https://img.freepik.com/free-photo/cute-small-height-african-american-girl-with-dreadlocks-wear-coloured-yellow-dress-posed-sunset_627829-2760.jpg?w=2000&t=st=1685801484~exp=1685802084~hmac=9ce68a821ca77872d4dfea597051d151aa6e827fe5b21620e797d246956b2882")
                .placeholder(R.drawable.profileplaceholder)
                .error(R.drawable.profileplaceholder)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return matchesModelArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        public ItemViewHolder(View ItemView){
            super(ItemView);
            imageView = ItemView.findViewById(R.id.matches_imageview);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("receiverId",matchesModelArrayList.get(getAdapterPosition()).getUserId());
            intent.putExtra("receiverFirstname",matchesModelArrayList.get(getAdapterPosition()).getUserFirstname());
            intent.putExtra("receiverLastname",matchesModelArrayList.get(getAdapterPosition()).getUserLastname());
            intent.putExtra("receiverImageUrl",matchesModelArrayList.get(getAdapterPosition()).getUserImageUrl());
            context.startActivity(intent);
        }

    }

}
