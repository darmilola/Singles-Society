package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.R
import com.singlesSociety.UiModels.NonMediaPostModel
import com.singlesSociety.UiModels.VideoModel
import com.singlesSociety.databinding.FragmentTextLibraryBinding
import com.singlesSociety.databinding.FragmentVideoLibraryBinding
import com.singlesSociety.uiAdapters.TextLibraryAdapter
import com.singlesSociety.uiAdapters.VideoLibraryAdapter


class TextLibraryFragment : Fragment(), CommentBottomSheet.CommentActionListener {

    private var postList = ArrayList<NonMediaPostModel>()
    private var postAdapter: TextLibraryAdapter? = null
    private var nonMediaPostModel: NonMediaPostModel = NonMediaPostModel()
    private lateinit var viewBinding: FragmentTextLibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTextLibraryBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..50) {
            postList.add(nonMediaPostModel)

        }
        postAdapter = TextLibraryAdapter(postList,requireContext(), addACommentClickListener = {
            val commentingSection = CommentBottomSheet(this@TextLibraryFragment)
            commentingSection.show(parentFragmentManager, "commentingSection")
        })
        viewBinding.textRecyclerView.adapter = postAdapter
    }

    override fun onProfileVisit() {

    }
}