package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.R
import com.singlesSociety.uiAdapters.BookmarksAdapter
import com.singlesSociety.UiModels.BookmarksModel
import com.singlesSociety.databinding.FragmentBookMarksBinding


class BookMarksFragment : Fragment() {

    var bookmarkList = ArrayList<BookmarksModel>()
    var bookmarksAdapter: BookmarksAdapter? = null
    private lateinit var viewBinding: FragmentBookMarksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBookMarksBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bookmarksModel = BookmarksModel(1)
        var bookmarksModel2 = BookmarksModel(2)
        var bookmarksModel3 = BookmarksModel(3)

        for (i in 0..50) {
            bookmarkList.add(bookmarksModel)
            bookmarkList.add(bookmarksModel2)
            bookmarkList.add(bookmarksModel3)

        }
        bookmarksAdapter = BookmarksAdapter(bookmarkList,requireContext())
        viewBinding.bookmarksRecyclerView.adapter = bookmarksAdapter
    }

}