package com.aure.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.aure.R
import com.aure.UiAdapters.ExploreHeaderAdapter
import com.aure.UiAdapters.ExploreItemAdapter
import com.aure.UiAdapters.TrendingHeaderAdapter
import com.aure.UiModels.ExploreHeader
import com.aure.UiModels.ExploreItem
import kotlinx.android.synthetic.main.fragment_trending.*


class TrendingFragment : Fragment() {

    private var itemList: ArrayList<ExploreItem>  = arrayListOf()
    private lateinit var itemView: View
    private lateinit var itemAdapter: ExploreItemAdapter

    private val images = ArrayList<Int>().apply {
        add(R.drawable.header)
        add(R.drawable.header)
        add(R.drawable.header)
        add(R.drawable.header)
        add(R.drawable.header)
    }

    private val headerAdapter by lazy { TrendingHeaderAdapter() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_trending, container, false)
        return itemView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateView()
    }

    private fun populateView(){
        for (i in 0..5) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreItemAdapter(itemList,requireContext())
        exploreItemRecyclerview.adapter = itemAdapter

        exploreHeader.setAdapter(headerAdapter)
        exploreHeader.setItems(images)
    }

}