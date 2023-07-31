package com.singlesSociety.uiAdapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.singlesSociety.ChatActivity
import com.singlesSociety.R
import com.singlesSociety.UiModels.MessageConnectionModel
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

class ConnectionsAdapter(
    private val context: Context,
    messagesList: ArrayList<MessageConnectionModel>
) :
    RecyclerView.Adapter<ConnectionsAdapter.MessagesViewholder?>() {
    private var messagesList = ArrayList<MessageConnectionModel>()

    init {
        this.messagesList = messagesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.direct_message_item, parent, false)
        return MessagesViewholder(view)
    }

    override fun onBindViewHolder(holder: MessagesViewholder, position: Int) {
        val messageConnectionModel = messagesList[position]
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val timestamp: Timestamp? = null
        val currentDate = Date()
        dateFormat.format(currentDate)

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
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    inner class MessagesViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView),
        View.OnClickListener {
        var profilePicture: AvatarView
        var displayName: TextView
        var lastMessage: TextView
        var timestamp: TextView

        init {
            profilePicture = ItemView.findViewById(R.id.userProfileImageViewWithIndicator)
            displayName = ItemView.findViewById(R.id.message_connection_sender_name)
            lastMessage = ItemView.findViewById(R.id.message_connection_last_message)
            timestamp = ItemView.findViewById(R.id.message_connection_timestamp)
            ItemView.setOnClickListener(this)
            profilePicture.loadImage(itemView.context.resources.getDrawable(R.drawable.woman_official))
        }

        override fun onClick(view: View) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("receiverId", messagesList[adapterPosition].getmReceiverId())
            intent.putExtra("receiverFirstname", messagesList[adapterPosition].receiverFirstname)
            intent.putExtra("receiverLastname", messagesList[adapterPosition].receiverLastname)
            intent.putExtra("receiverImageUrl", messagesList[adapterPosition].receiverProfileImage)
            context.startActivity(intent)
        }
    }
}