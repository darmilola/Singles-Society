package com.aure.UiAdapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.aure.ExplorePage
import com.aure.R
import com.aure.UiModels.VideoModel


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
                context.startActivity(Intent(context, ExplorePage::class.java))
            }
        }
    }

}