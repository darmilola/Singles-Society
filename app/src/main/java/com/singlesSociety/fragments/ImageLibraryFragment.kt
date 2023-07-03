package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.R
import com.singlesSociety.uiAdapters.ImageLibraryAdapter
import com.singlesSociety.UiModels.ImageModel
import com.singlesSociety.databinding.FragmentImageLibraryBinding


class ImageLibraryFragment : Fragment() {

    var imageList = ArrayList<ImageModel>()
    var imagesAdapter: ImageLibraryAdapter? = null
    var imageModel: ImageModel = ImageModel()
    private lateinit var viewBinding: FragmentImageLibraryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentImageLibraryBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..50) {
            imageList.add(imageModel)

        }
        imagesAdapter = ImageLibraryAdapter(imageList,requireContext())
        viewBinding.imagesRecyclerView.adapter = imagesAdapter
    }

}