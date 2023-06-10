package com.aure.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aure.R
import com.aure.UiAdapters.ExploreItemAdapter
import com.aure.UiAdapters.NotificationAdapter
import com.aure.UiModels.ExploreItem
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_trending.*


class NotificationFragment : Fragment() {

    var adapter: NotificationAdapter? = null
    private var notificationList: ArrayList<Int> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateView()

    }

    private fun populateView(){
        for (i in 0..30) {
            notificationList.add(i)
        }
        adapter = NotificationAdapter(requireContext(), notificationList)
        notificationRecyclerView.adapter = adapter
    }

}