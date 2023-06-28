package com.singlesSociety.uiAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.R
import com.singlesSociety.UiModels.ExploreItem

class ExploreLiveAdapter(val exploreItemList: ArrayList<ExploreItem>): RecyclerView.Adapter<ExploreLiveAdapter.ItemViewholder>() {
    
    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.explore_item_type_live, parent, false)
        return ItemViewholder(view)
    }

    override fun getItemCount(): Int {
        return exploreItemList.size
    }

    class ItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
}