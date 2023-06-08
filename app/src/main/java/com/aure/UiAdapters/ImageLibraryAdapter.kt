package com.aure.UiAdapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aure.ExplorePage
import com.aure.ImagePostFullView
import com.aure.R
import com.aure.UiModels.ImageModel

class ImageLibraryAdapter(val imageList: ArrayList<ImageModel>,val context: Context): RecyclerView.Adapter<ImageLibraryAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view2: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_media_item, parent, false)
        return ItemViewHolder(view2)
    }

    override fun getItemCount(): Int {
      return imageList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

    }

   inner class ItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        init {
            ItemView.setOnClickListener {
                context.startActivity(Intent(context, ImagePostFullView::class.java))
            }
        }

    }
}