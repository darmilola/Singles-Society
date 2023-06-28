package com.singlesSociety.uiAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singlesSociety.ChatActivity;
import com.singlesSociety.R;
import com.singlesSociety.UiModels.MessageConnectionModel;
import com.bumptech.glide.Glide;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Timestamp timestamp = null;
        Date currentDate = new Date();
        dateFormat.format(currentDate);

     /*   try {

            Date parsedDate = dateFormat.parse(messageConnectionModel.getTimestamp());
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch(Exception e) { //this generic but you can control another types of exception
            Log.e("TAG ", e.getLocalizedMessage());
        }
        Date chatDate = new Date(timestamp.getTime());
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(chatDate);               // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 1);      // adds one hour
        if(DateFormatter.isSameDay(currentDate,chatDate)){
            holder.timestamp.setText(DateFormatter.format(cal.getTime(), DateFormatter.Template.TIME));
        }
        else{
            holder.timestamp.setText(DateFormatter.format(cal.getTime(), DateFormatter.Template.STRING_DAY_MONTH));
        }

        holder.lastMessage.setText(StringEscapeUtils.unescapeJava(messageConnectionModel.getLastMessage()));

        holder.displayName.setText(messageConnectionModel.getReceiverFirstname()+" "+messageConnectionModel.getReceiverLastname());
        if(messageConnectionModel.getUnreadCount() == 0){
            holder.unreadLayout.setVisibility(View.GONE);
        }
        else{
            holder.unreadCount.setText(Integer.toString(messageConnectionModel.getUnreadCount()));
        }*/
        Glide.with(context)
                .load("https://img.freepik.com/free-photo/african-american-business-woman-by-window_1303-10869.jpg?w=2000&t=st=1685802798~exp=1685803398~hmac=579550482f3d0bcdd91eebdfd983b837d9ed35d3587e8f1fe14be3e8eced05f1")
                .placeholder(R.drawable.profileplaceholder)
                .error(R.drawable.profileplaceholder)
                .into(holder.profilePicture);

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MessagesViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView profilePicture;
        TextView displayName;
        TextView lastMessage;
        TextView timestamp;

        public MessagesViewholder(View ItemView){
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
            intent.putExtra("receiverId",messagesList.get(getAdapterPosition()).getmReceiverId());
            intent.putExtra("receiverFirstname",messagesList.get(getAdapterPosition()).getReceiverFirstname());
            intent.putExtra("receiverLastname",messagesList.get(getAdapterPosition()).getReceiverLastname());
            intent.putExtra("receiverImageUrl",messagesList.get(getAdapterPosition()).getReceiverProfileImage());
            context.startActivity(intent);
        }

    }

}
