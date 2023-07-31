package com.singlesSociety.uiAdapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.singlesSociety.ChatActivity
import com.singlesSociety.R
import com.singlesSociety.UiModels.MatchesModel
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage

class ConnectionMatchesAdapter(private val context: Context, matchesModelArrayList: ArrayList<MatchesModel>) :
    RecyclerView.Adapter<ConnectionMatchesAdapter.ItemViewHolder?>() {
    private var matchesModelArrayList = ArrayList<MatchesModel>()

    init {
        this.matchesModelArrayList = matchesModelArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.matches_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val matchesModel = matchesModelArrayList[position]
        holder.imageView.loadImage("https://img.freepik.com/free-photo/cute-small-height-african-american-girl-with-dreadlocks-wear-coloured-yellow-dress-posed-sunset_627829-2760.jpg?w=2000&t=st=1685801484~exp=1685802084~hmac=9ce68a821ca77872d4dfea597051d151aa6e827fe5b21620e797d246956b2882")
    }

    override fun getItemCount(): Int {
        return matchesModelArrayList.size
    }

    inner class ItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView),
        View.OnClickListener {
        var imageView: AvatarView

        init {
            imageView = ItemView.findViewById(R.id.userProfileImageViewWithIndicator)
            ItemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("receiverId", matchesModelArrayList[adapterPosition].userId)
            intent.putExtra(
                "receiverFirstname",
                matchesModelArrayList[adapterPosition].userFirstname
            )
            intent.putExtra("receiverLastname", matchesModelArrayList[adapterPosition].userLastname)
            intent.putExtra("receiverImageUrl", matchesModelArrayList[adapterPosition].userImageUrl)
            context.startActivity(intent)
        }
    }
}