package com.singlesSociety.uiAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.R
import com.singlesSociety.UiModels.EventSpeakerModel

class EventSpeakerAdapter(private val eventSpeakerList: ArrayList<EventSpeakerModel>): RecyclerView.Adapter<EventSpeakerAdapter.ItemViewholder>() {

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.live_event_speaker_item, parent, false)
        return ItemViewholder(view)
    }

    override fun getItemCount(): Int {
        return eventSpeakerList.size
    }

    class ItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {}
}