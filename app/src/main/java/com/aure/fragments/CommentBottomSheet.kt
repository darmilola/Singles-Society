package com.aure.fragments


import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.aure.R
import com.aure.UiAdapters.CommentsAdapter
import com.aure.UiModels.CommentModel
import com.aure.UiModels.CommentReplyModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
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
        val adapter = CommentsAdapter(requireContext(),commentList)
        commentRecyclerView.adapter = adapter

        adapter.setCommentProfileVisitListener {
            commentActionListener.onProfileVisit()
            dismiss()
        }


        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(view.parent as View)

        behavior.addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if(newState == BottomSheetBehavior.STATE_EXPANDED){
                          // makeBottomSheetShowFullScreen()
                        }
                else{
                           // dragHandler.visibility = View.VISIBLE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

    }

    private fun makeBottomSheetShowFullScreen(){
        dragHandler.visibility = View.INVISIBLE

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