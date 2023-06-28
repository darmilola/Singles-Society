package com.singlesSociety.uiAdapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.R
import com.singlesSociety.UiModels.VideoModel
import com.singlesSociety.fragments.PostFullView
import com.singlesSociety.fragments.viewTypeVideo


class VideoLibraryAdapter(private val videoList: ArrayList<VideoModel>, val context: Context): RecyclerView.Adapter<VideoLibraryAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view2: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_media_item, parent, false)
        return ItemViewHolder(view2)
    }

    override fun getItemCount(): Int {
       return videoList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

    }

   inner class ItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            ItemView.setOnClickListener {
                context.startActivity(Intent(context, PostFullView::class.java).putExtra("type",
                    viewTypeVideo))
            }
        }
    }

}