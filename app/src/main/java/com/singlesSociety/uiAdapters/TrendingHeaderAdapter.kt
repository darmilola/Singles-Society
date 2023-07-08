package com.singlesSociety.uiAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.R
import com.mindinventory.CircularAdapter

class TrendingHeaderAdapter(private val itemList: ArrayList<Int>): RecyclerView.Adapter<TrendingHeaderAdapter.ItemViewholder>() {
    class ItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.explore_header_card_item, parent, false)
        return ItemViewholder(view)
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        val item = itemList[position]
        holder.itemView.findViewById<ImageView>(R.id.headerView).setImageResource(item)
    }
}