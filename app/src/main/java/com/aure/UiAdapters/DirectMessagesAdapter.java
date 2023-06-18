package com.aure.UiAdapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaCodec;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aure.ChatActivity;
import com.aure.R;
import com.aure.UiModels.MessageConnectionModel;
import com.bumptech.glide.Glide;

import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;


public class DirectMessagesAdapter extends RecyclerView.Adapter<DirectMessagesAdapter.itemViewHolder> {

    ArrayList<MessageConnectionModel> directMessagesItemList;
    Context context;


    public DirectMessagesAdapter(ArrayList<MessageConnectionModel> directMessagesItemList, Context context){
        this.directMessagesItemList = directMessagesItemList;
        this.context = context;
    }


    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.direct_message_item, parent, false);
        return new itemViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        MessageConnectionModel messageConnectionModel = directMessagesItemList.get(position);
        holder.lastMessage.setText(StringEscapeUtils.unescapeJava(messageConnectionModel.getLastMessage()));
        holder.timestamp.setText(messageConnectionModel.getTimestamp());
        holder.displayName.setText(messageConnectionModel.getReceiverFirstname()+" "+messageConnectionModel.getReceiverLastname());


        Glide.with(context)
                .load(messageConnectionModel.getReceiverProfileImage())
                .placeholder(R.drawable.profileplaceholder)
                .error(R.drawable.profileplaceholder)
                .into(holder.profilePicture);
    }


    @Override
    public int getItemCount() {
        return directMessagesItemList.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       CircleImageView profilePicture;
       TextView displayName;
       TextView lastMessage;
       TextView timestamp;

        public itemViewHolder(View ItemView){
            super(ItemView);
            profilePicture = ItemView.findViewById(R.id.message_connection_sender_profile_image);
            displayName = ItemView.findViewById(R.id.message_connection_sender_name);
            lastMessage = ItemView.findViewById(R.id.message_connection_last_message);
            timestamp = ItemView.findViewById(R.id.message_connection_timestamp);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("receiverId",directMessagesItemList.get(getAdapterPosition()).getmReceiverId());
            intent.putExtra("receiverFirstname",directMessagesItemList.get(getAdapterPosition()).getReceiverFirstname());
            intent.putExtra("receiverLastname",directMessagesItemList.get(getAdapterPosition()).getReceiverLastname());
            intent.putExtra("receiverImageUrl",directMessagesItemList.get(getAdapterPosition()).getReceiverProfileImage());
            context.startActivity(intent);

        }
    }
}
