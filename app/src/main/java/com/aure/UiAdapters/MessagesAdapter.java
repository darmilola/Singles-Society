package com.aure.UiAdapters;

import android.content.Context;
import android.content.Intent;
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

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewholder> {

    private Context context;
    private ArrayList<MessageConnectionModel> messagesList = new ArrayList<>();

    public MessagesAdapter(Context context,ArrayList<MessageConnectionModel> messagesList){
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
        MessageConnectionModel messageConnectionModel = messagesList.get(position);
        holder.lastMessage.setText(StringEscapeUtils.unescapeJava(messageConnectionModel.getLastMessage()));
        holder.timestamp.setText(messageConnectionModel.getTimestamp());
        holder.displayName.setText(messageConnectionModel.getReceiverFirstname()+" "+messageConnectionModel.getReceiverLastname());
        if(messageConnectionModel.getUnreadCount() == 0){
            holder.unreadLayout.setVisibility(View.GONE);
        }
        else{
            holder.unreadCount.setText(Integer.toString(messageConnectionModel.getUnreadCount()));
        }
        Glide.with(context)
                .load(messageConnectionModel.getReceiverProfileImage())
                .placeholder(R.drawable.profileplaceholder)
                .error(R.drawable.profileplaceholder)
                .into(holder.profilePicture);

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MessagesViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView profilePicture;
        TextView displayName;
        TextView lastMessage;
        TextView timestamp;
        TextView unreadCount;
        LinearLayout unreadLayout;

        public MessagesViewholder(View ItemView){
            super(ItemView);
            profilePicture = ItemView.findViewById(R.id.message_connection_sender_profile_image);
            displayName = ItemView.findViewById(R.id.message_connection_sender_name);
            lastMessage = ItemView.findViewById(R.id.message_connection_last_message);
            timestamp = ItemView.findViewById(R.id.message_connection_timestamp);
            unreadCount = ItemView.findViewById(R.id.message_connection_unread_count);
            unreadLayout = ItemView.findViewById(R.id.message_connection_unread_count_layout);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("receiverId",messagesList.get(getAdapterPosition()).getmReceiverId());
            intent.putExtra("receiverFirstname",messagesList.get(getAdapterPosition()).getReceiverFirstname());
            intent.putExtra("receiverLastname",messagesList.get(getAdapterPosition()).getReceiverLastname());
            intent.putExtra("receiverImageUrl",messagesList.get(getAdapterPosition()).getReceiverProfileImage());
            context.startActivity(intent);
        }

    }

}
