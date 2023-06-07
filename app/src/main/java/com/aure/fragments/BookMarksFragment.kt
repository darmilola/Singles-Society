package com.aure.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aure.R
import com.aure.UiAdapters.BookmarksAdapter
import com.aure.UiAdapters.MatchesAdapter
import com.aure.UiAdapters.MessagesAdapter
import com.aure.UiModels.BookmarksModel
import com.aure.UiModels.ExploreItem
import com.aure.UiModels.MatchesModel
import com.aure.UiModels.MessageConnectionModel
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