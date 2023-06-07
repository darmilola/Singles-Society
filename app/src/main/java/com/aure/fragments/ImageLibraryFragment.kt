package com.aure.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aure.R
import com.aure.UiAdapters.BookmarksAdapter
import com.aure.UiAdapters.ImageLibraryAdapter
import com.aure.UiModels.BookmarksModel
import com.aure.UiModels.ImageModel
import kotlinx.android.synthetic.main.fragment_book_marks.*
import kotlinx.android.synthetic.main.fragment_image_library.*


class ImageLibraryFragment : Fragment() {

    var imageList = ArrayList<ImageModel>()
    var imagesAdapter: ImageLibraryAdapter? = null
    var imageModel: ImageModel = ImageModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..50) {
            imageList.add(imageModel)

        }
        imagesAdapter = ImageLibraryAdapter(imageList,requireContext())
        imagesRecyclerView.adapter = imagesAdapter
    }

}