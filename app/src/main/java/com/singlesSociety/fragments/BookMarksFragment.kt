package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.R
import com.singlesSociety.uiAdapters.BookmarksAdapter
import com.singlesSociety.UiModels.BookmarksModel
import kotlinx.android.synthetic.main.fragment_book_marks.*


class BookMarksFragment : Fragment() {

    var bookmarkList = ArrayList<BookmarksModel>()
    var bookmarksAdapter: BookmarksAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_marks, container, false)
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
        bookmarksRecyclerView.adapter = bookmarksAdapter
    }

}