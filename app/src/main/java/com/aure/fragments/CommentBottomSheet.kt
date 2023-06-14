package com.aure.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aure.R
import com.aure.UiAdapters.CommentsAdapter
import com.aure.UiModels.CommentModel
import com.aure.UiModels.CommentReplyModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_comment_bottom_sheet.*


class CommentBottomSheet(var commentActionListener: CommentActionListener) : BottomSheetDialogFragment() {

    var commentReplyModelList: ArrayList<CommentReplyModel> = ArrayList()
    var commentList: ArrayList<CommentModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 1..20){
            commentReplyModelList.add(CommentReplyModel())
        }

        for (i in 1..10){
            commentList.add(CommentModel(commentReplyModelList,1))
        }
        val adapter = CommentsAdapter(requireContext(),commentList)
        commentRecyclerView.adapter = adapter

        adapter.setCommentProfileVisitListener {
            commentActionListener.onProfileVisit()
            dismiss()
        }

    }


    interface CommentActionListener{
         fun onProfileVisit()
    }

}