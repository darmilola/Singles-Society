package com.aure.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aure.ExploreSearch
import com.aure.R
import com.aure.UiAdapters.ExploreItemAdapter
import com.aure.UiAdapters.ExploreLiveAdapter
import com.aure.UiAdapters.TrendingHeaderAdapter
import com.aure.UiModels.ExploreItem
import kotlinx.android.synthetic.main.fragment_trending.*


class TrendingFragment(private var enterSpaceListener: Function0<Unit>? = null, private var createSpaceListener: Function0<Unit>? = null) : Fragment() {

    private var itemList: ArrayList<ExploreItem>  = arrayListOf()
    private lateinit var itemView: View
    private lateinit var itemAdapter: ExploreItemAdapter

    private val images = ArrayList<Int>().apply {
        add(R.drawable.asian_lady)
        add(R.drawable.asian_lady)
        add(R.drawable.asian_lady)
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

       /* exploreSearchIcon.setOnClickListener {
            context?.startActivity(Intent(requireActivity(),ExploreSearch::class.java))
        }*/

        createNewSpaceCta.setOnClickListener {
            createSpaceListener?.invoke()
        }
    }

    private fun populateView(){
        for (i in 0..3) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreItemAdapter(itemList,requireContext())
        itemAdapter.setSpacesClickedListener {
            enterSpaceListener?.invoke()
        }
        exploreItemRecyclerview.adapter = itemAdapter
        mySpacesRecyclerview.adapter = itemAdapter
        exploreUpcomingLiveRecyclerview.adapter = ExploreLiveAdapter(itemList)

        exploreHeader.setAdapter(headerAdapter)
        exploreHeader.setItems(images)
    }

}