package com.singlesSociety.uiAdapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.R
import com.singlesSociety.UiModels.ImageModel
import com.singlesSociety.UiModels.SocietyLocationModel
import com.singlesSociety.fragments.PostFullView
import com.singlesSociety.fragments.viewTypeImage

class LocationAdapter(private val locationList: ArrayList<SocietyLocationModel>, val context: Context): RecyclerView.Adapter<LocationAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view2: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.select_item_layout, parent, false)
        return ItemViewHolder(view2)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

    }

    class ItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        init {
            ItemView.setOnClickListener {

            }
        }

    }
}