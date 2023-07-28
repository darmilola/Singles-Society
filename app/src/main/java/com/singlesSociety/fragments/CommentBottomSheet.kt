package com.singlesSociety.fragments


import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.singlesSociety.R
import com.singlesSociety.uiAdapters.CommentsAdapter
import com.singlesSociety.UiModels.CommentModel
import com.singlesSociety.UiModels.CommentReplyModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.singlesSociety.databinding.FragmentCommentBottomSheetBinding


class CommentBottomSheet(var commentActionListener: CommentActionListener) : BottomSheetDialogFragment() {

    var commentReplyModelList: ArrayList<CommentReplyModel> = ArrayList()
    var commentList: ArrayList<CommentModel> = ArrayList()
    private lateinit var viewBinding: FragmentCommentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentCommentBottomSheetBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 1..20){
            commentReplyModelList.add(CommentReplyModel())
        }

        for (i in 1..10){
            commentList.add(CommentModel(commentReplyModelList,1))
        }
        val adapter = CommentsAdapter(requireContext(),commentList, true)
        viewBinding.commentRecyclerView.adapter = adapter

        adapter.setCommentProfileVisitListener {
            commentActionListener.onProfileVisit()
            dismiss()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val containerLayout = dialog?.findViewById(
                com.google.android.material.R.id.container
            ) as? FrameLayout

            val bottomSheetActionLayout = LayoutInflater.from(requireContext())
                .inflate(
                    R.layout.add_comment_input,
                    containerLayout,
                    false
                )

            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )

            containerLayout?.addView(
                bottomSheetActionLayout,
                layoutParams
            )


        }

        return bottomSheetDialog

    }


       interface CommentActionListener{
         fun onProfileVisit()
    }



    override fun onResume() {
        super.onResume()
            activity?.window?.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.white)
            activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}