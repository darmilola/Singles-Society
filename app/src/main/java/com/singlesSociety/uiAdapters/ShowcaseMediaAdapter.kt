package com.singlesSociety.uiAdapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.singlesSociety.R
import com.singlesSociety.UiModels.ImageModel

class ShowcaseMediaAdapter(private val imageList: ArrayList<ImageModel>, val context: Context): RecyclerView.Adapter<ShowcaseMediaAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view2: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_showcase_type_picture, parent, false)
        return ItemViewHolder(view2)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val imageModel: ImageModel = imageList[position]
        Glide.with(context)
            .load(imageModel.url)
            .placeholder(R.drawable.profileplaceholder)
            .error(R.drawable.profileplaceholder)
            .into(holder.imageView)
    }

    inner class ItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = ItemView.findViewById(R.id.showcaseImage)
    }
}