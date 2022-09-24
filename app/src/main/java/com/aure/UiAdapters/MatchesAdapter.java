package com.aure.UiAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
          holder.firstname.setText(matchesModel.getUserFirstname());
          Glide.with(context)
                .load(matchesModel.getUserImageUrl())
                .placeholder(R.drawable.profileplaceholder)
                .error(R.drawable.profileplaceholder)
                .into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return matchesModelArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView circleImageView;
        TextView firstname;
        public ItemViewHolder(View ItemView){
            super(ItemView);
            circleImageView = ItemView.findViewById(R.id.matches_imageview);
            firstname = ItemView.findViewById(R.id.matches_firstname);
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
