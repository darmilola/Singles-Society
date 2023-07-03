package com.singlesSociety.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.SinglesSociety.SocialText.RichTextController.MentionHashTagListener
import com.SinglesSociety.SocialText.RichTextController.RTManager
import com.SinglesSociety.SocialText.RichTextController.api.RTApi
import com.SinglesSociety.SocialText.RichTextController.api.RTProxyImpl
import com.singlesSociety.R
import com.singlesSociety.databinding.FragmentCreatePostTypeTextBinding


class CreatePostTypeText : Fragment() {

    private var createPostRTManager: RTManager? = null
    private var rtApi: RTApi? = null
    private lateinit var viewBinding: FragmentCreatePostTypeTextBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentCreatePostTypeTextBinding.inflate(layoutInflater)
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        rtApi = RTApi(context, RTProxyImpl(activity))
        createPostRTManager = RTManager(rtApi)
        createPostRTManager!!.registerToolbar(viewBinding.createPostToolbarLayout, viewBinding.createPostToolbar)
        createPostRTManager!!.registerEditor(viewBinding.editView, true)
        viewBinding.editView!!.setRichTextEditing(true, true)
        viewBinding.createPostToolbar.applyDefaultFont()
        viewBinding.exitButton.setOnClickListener {
            activity?.finish()
        }

        viewBinding.editView.setMentionHashTagListener(object : MentionHashTagListener {
            override fun onMentioning(sequence: CharSequence) {}
            override fun onHashTagging(sequence: CharSequence) {}
            override fun onStopMentioning() {}
            override fun onStopHashTags() {}
            override fun onMentionStarted() {}
            override fun onHashTagsStarted() {}
        })

    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.window?.decorView?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

}