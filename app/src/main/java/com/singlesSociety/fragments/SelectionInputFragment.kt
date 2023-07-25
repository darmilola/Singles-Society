package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.R
import com.singlesSociety.UiModels.SocietyLocationModel
import com.singlesSociety.databinding.FragmentSelectionInputBinding
import com.singlesSociety.databinding.FragmentSpacesMainBinding
import com.singlesSociety.databinding.SelectInputItemBinding
import com.singlesSociety.uiAdapters.LocationAdapter


class SelectionInputFragment : Fragment() {

    private lateinit var viewBinding: FragmentSelectionInputBinding
    private val locationList: ArrayList<SocietyLocationModel> = arrayListOf()
    private var adapter: LocationAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSelectionInputBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateView()
        viewBinding.cancelLocationSelection.setOnClickListener {
            activity?.finish()
        }
    }

    private fun populateView(){
        for (i in 0..30) {
            locationList.add(SocietyLocationModel())
        }
        adapter = LocationAdapter(locationList, requireContext())
        viewBinding.locationRecyclerview.adapter = adapter
    }

    companion object {


        fun newInstance(args: Bundle): SelectionInputFragment {
            return SelectionInputFragment().apply {
                arguments = args
            }
        }
    }

}