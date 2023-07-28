package com.singlesSociety

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.singlesSociety.UiModels.CommentModel
import com.singlesSociety.UiModels.CommentReplyModel
import com.singlesSociety.databinding.ActivitySocietyPostDetailBinding
import com.singlesSociety.databinding.ActivitySocietySwipeBinding
import com.singlesSociety.uiAdapters.CommentsAdapter

class SocietyPostDetail : AppCompatActivity() {
    var commentReplyModelList: ArrayList<CommentReplyModel> = ArrayList()
    var commentList: ArrayList<CommentModel> = ArrayList()
    private lateinit var viewBinding: ActivitySocietyPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySocietyPostDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        supportPostponeEnterTransition()
        initView()
    }

    private fun initView(){
        for (i in 1..20){
            commentReplyModelList.add(CommentReplyModel())
        }

        for (i in 1..10){
            commentList.add(CommentModel(commentReplyModelList,1))
        }
        val adapter = CommentsAdapter(this,commentList, true)
        viewBinding.detailLayout.commentRecyclerView.adapter = adapter

        adapter.setCommentProfileVisitListener {

        }

        viewBinding.postDetailExitCta.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.statusBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }
}