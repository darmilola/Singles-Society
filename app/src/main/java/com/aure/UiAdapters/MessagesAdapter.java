package com.aure.UiAdapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aure.ChatActivity;
import com.aure.ChatKit.utils.DateFormatter;
import com.aure.R;
import com.aure.UiModels.MessageConnectionModel;
import com.bumptech.glide.Glide;

import org.apache.commons.text.StringEscapeUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Timestamp timestamp = null;
        Date currentDate = new Date();
        dateFormat.format(currentDate);

        try {

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
