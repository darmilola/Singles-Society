package com.singlesSociety.uiAdapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.Spaces
import com.singlesSociety.R
import com.singlesSociety.UiModels.ExploreItem

class ExploreSearchAdapter(val exploreItems: ArrayList<ExploreItem>?, val context: Context?): RecyclerView.Adapter<ExploreSearchAdapter.ItemViewholder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.explore_search_item, parent, false)
        return ItemViewholder(view)
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {}

    override fun getItemCount(): Int {
        return exploreItems!!.size
    }


    class ItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var textView: TextView? = null

        init {
            ItemView.setOnClickListener {
                ItemView.context.startActivity(
                    Intent(
                        ItemView.context,
                        Spaces::class.java
                    )
                )
            }
        }
    }

}