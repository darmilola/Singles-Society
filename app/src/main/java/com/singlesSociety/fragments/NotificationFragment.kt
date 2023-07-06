package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.R
import com.singlesSociety.databinding.FragmentNotificationBinding
import com.singlesSociety.uiAdapters.NotificationAdapter


class NotificationFragment : Fragment() {

    var adapter: NotificationAdapter? = null
    private var notificationList: ArrayList<Int> = ArrayList()
    private lateinit var viewBinding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentNotificationBinding.inflate(layoutInflater)
        return viewBinding.root
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
        viewBinding.notificationRecyclerView.adapter = adapter
    }

}