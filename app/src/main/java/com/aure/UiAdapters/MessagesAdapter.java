package com.aure.UiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aure.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewholder> {


    private Context context;
    private ArrayList<String> messagesList = new ArrayList<>();

    public MessagesAdapter(Context context,ArrayList<String> messagesList){
        this.context = context;
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public MessagesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.direct_message_item, parent, false);
        return new MessagesViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MessagesViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MessagesViewholder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

}
