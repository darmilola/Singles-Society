package com.aure.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.SinglesSociety.SocialText.RichTextController.RTManager
import com.SinglesSociety.SocialText.RichTextController.api.RTApi
import com.SinglesSociety.SocialText.RichTextController.api.RTProxyImpl
import com.aure.R
import kotlinx.android.synthetic.main.fragment_create_post_type_text.*


class CreatePostTypeText : Fragment() {

    private var createPostRTManager: RTManager? = null
    private var rtApi: RTApi? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_post_type_text, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        rtApi = RTApi(context, RTProxyImpl(activity))
        createPostRTManager = RTManager(rtApi)
        createPostRTManager!!.registerToolbar(createPostToolbarLayout, createPostToolbar)
        createPostRTManager!!.registerEditor(editView, true)
        editView!!.setRichTextEditing(true, true)
        createPostToolbar.applyDefaultFont()
        exitButton.setOnClickListener {
            activity?.finish()
        }

    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity?.window?.decorView?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

}