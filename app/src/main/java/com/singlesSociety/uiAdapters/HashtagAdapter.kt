package com.singlesSociety.uiAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.R
import com.singlesSociety.UiModels.PopularHashtagModel

class HashtagAdapter(private val hashtagList: ArrayList<PopularHashtagModel>) : RecyclerView.Adapter<HashtagAdapter.ItemViewHolder>() {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            if(hashtagList[position].isGrowing){
                holder.itemView.findViewById<ImageView>(R.id.hashtagTrending).setImageResource(R.drawable.trend_up_icon)
            }
        else{
                holder.itemView.findViewById<ImageView>(R.id.hashtagTrending).setImageResource(R.drawable.trend_down_icon)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.trending_hashtags, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hashtagList.size
    }


    class ItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){}
}