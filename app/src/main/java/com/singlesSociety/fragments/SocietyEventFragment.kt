package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.UiModels.ExploreEvent
import com.singlesSociety.databinding.FragmentSocietyEventBinding
import com.singlesSociety.uiAdapters.SocietyEventsAdapter


class SocietyEventFragment : Fragment() {

    private lateinit var viewBinding: FragmentSocietyEventBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentSocietyEventBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateSocietyEvents()
    }

    private fun populateSocietyEvents(){
        var itemList = ArrayList<ExploreEvent>()
        itemList.add(ExploreEvent(1))
        for (i in 0..5) {
            itemList.add(ExploreEvent(0))
        }
        itemList.add(ExploreEvent(1))

        viewBinding.eventsRecyclerview.adapter = SocietyEventsAdapter(requireContext(),itemList)
    }
}