package com.singlesSociety.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.singlesSociety.UiModels.*
import com.singlesSociety.uiAdapters.CommentsAdapter
import com.singlesSociety.uiAdapters.EventSpeakerAdapter
import kotlinx.android.synthetic.main.fragment_comment_bottom_sheet.*
import kotlinx.android.synthetic.main.live_event_bottomsheet.*
import kotlinx.android.synthetic.main.live_event_bottomsheet.commentRecyclerView


class LiveEventBottomsheet() : BottomSheetDialogFragment() {

    private var speakersList = ArrayList<EventSpeakerModel>()
    private var commentList: ArrayList<CommentModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.singlesSociety.R.layout.live_event_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), com.singlesSociety.R.style.MyTransparentBottomSheetDialogTheme)
    }

    private fun initView(){
        for(i in 1.. 6){
            speakersList.add(EventSpeakerModel())
        }
        eventSpeakerRecyclerview.adapter = EventSpeakerAdapter(speakersList)


        for (i in 1..10){
            commentList.add(CommentModel(ArrayList<CommentReplyModel>(),1))
        }

        val adapter = CommentsAdapter(requireContext(),commentList, false)
        commentRecyclerView.adapter = adapter
        val mLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        commentRecyclerView.layoutManager = mLayoutManager



        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(view?.parent as View)
        behavior.peekHeight = dip(requireContext(),190)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED

        commentBottomsheetRootView.setOnTouchListener(OnTouchListener { v, event ->
            isCancelable = false
            behavior.peekHeight = dip(requireContext(),190)
            true
        })

        commentRecyclerView.setOnTouchListener(OnTouchListener { v, event ->
            isCancelable = false
            behavior.peekHeight = -1
            false
        })


        commentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy != 0) {
                  isCancelable = false
                  behavior.peekHeight = -1
               }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isCancelable = false
                    behavior.peekHeight = dip(requireContext(),190)
                }
            }
        })

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


    override fun onResume() {
        super.onResume()
        activity?.window?.navigationBarColor = ContextCompat.getColor(requireContext(), com.singlesSociety.R.color.white)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), com.singlesSociety.R.color.white)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}