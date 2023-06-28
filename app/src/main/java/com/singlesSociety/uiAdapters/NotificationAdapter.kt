package com.singlesSociety.uiAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.R

class NotificationAdapter(context: Context, private val notificationItems: ArrayList<Int>): RecyclerView.Adapter<NotificationAdapter.ItemViewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.notification_type_default, parent, false)
        return ItemViewholder(view)
    }

    override fun getItemCount(): Int {
        return notificationItems.size
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
    }

    class ItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {}


    }