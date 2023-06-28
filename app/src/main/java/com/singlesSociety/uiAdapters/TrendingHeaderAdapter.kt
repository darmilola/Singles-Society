package com.singlesSociety.uiAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.R
import com.mindinventory.CircularAdapter
import kotlinx.android.synthetic.main.explore_header_card_item.view.*

class TrendingHeaderAdapter(): CircularAdapter<Int>() {
    override fun bindItemViewHolder(
        holder: RecyclerView.ViewHolder,
        item: Int,
        actualPosition: Int,
        position: Int
    ) {
       holder.itemView.headerView.setImageResource(item)
    }

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.explore_header_card_item, parent, false)
        return ItemViewholder(view)
    }

    class ItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {}
}