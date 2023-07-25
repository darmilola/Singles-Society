package com.singlesSociety.uiAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.R
import com.singlesSociety.UiModels.SocietyHeaderModel
import com.singlesSociety.uiAdapters.HomeMainAdapter.EventItemViewholder
import com.singlesSociety.uiAdapters.HomeMainAdapter.ShowcaseItemViewHolder

class TrendingHeaderAdapter(private val itemList: ArrayList<SocietyHeaderModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_MAIN = 0

    private val TYPE_SPONSORED = 1
    class ItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {}

    class SponsoredItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == TYPE_MAIN) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.explore_header_type_main, parent, false)
            ItemViewholder(view)
        } else if(viewType == TYPE_SPONSORED){
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.explore_header_type_sponsored, parent, false)
            SponsoredItemViewholder(view)
        } else{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.explore_header_type_main, parent, false)
            ItemViewholder(view)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if(itemList[position].viewType == TYPE_MAIN){
            TYPE_MAIN
        } else{
            TYPE_SPONSORED
        }
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(itemList[position].viewType == TYPE_MAIN){
            val item = itemList[position].imgResource
            holder.itemView.findViewById<ImageView>(R.id.headerView).setImageResource(item)
        }
    }
}