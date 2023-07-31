package com.singlesSociety.uiAdapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.EventLandingActivity
import com.singlesSociety.R
import com.singlesSociety.EventLandingPage
import com.singlesSociety.UiModels.ExploreEvent

class SocietyEventsAdapter(private val context: Context, val exploreItemList: ArrayList<ExploreEvent>, private var visitEventListener: Function0<Unit>? = null): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_MAIN = 0

    private val TYPE_ONGOING = 1
    class OngoingItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            ItemView.setOnClickListener {

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         holder.itemView.setOnClickListener {
             context.startActivity(Intent(context, EventLandingActivity::class.java))
         }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == TYPE_MAIN) {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.explore_item_type_live, parent, false)
             ItemViewholder(view)
        } else if(viewType == TYPE_ONGOING){
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.event_layout_ongoing, parent, false)
            OngoingItemViewholder(view)
        }
        else{
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.explore_item_type_live, parent, false)
            ItemViewholder(view)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if(exploreItemList[position].viewType == TYPE_MAIN){
            TYPE_MAIN
        } else{
            TYPE_ONGOING
        }
    }


    override fun getItemCount(): Int {
        return exploreItemList.size
    }

    class ItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView){

    }
}