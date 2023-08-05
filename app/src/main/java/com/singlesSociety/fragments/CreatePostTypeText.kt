package com.singlesSociety.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.SinglesSociety.SocialText.RichTextController.MentionHashTagListener
import com.facebook.common.util.UriUtil.getRealPathFromUri
import com.singlesSociety.UiModels.MediaUploadAttachmentModel
import com.singlesSociety.UiModels.Utils.LayoutUtils
import com.singlesSociety.databinding.FragmentCreatePostTypeTextBinding
import com.singlesSociety.uiAdapters.CreatePostAttachmentsAdapter
import com.ss.agora.EventsUI
import com.ss.cloudinary.SocietyMediaManager
import java.io.IOException


class CreatePostTypeText : Fragment() {
    private lateinit var viewBinding: FragmentCreatePostTypeTextBinding
    private lateinit var attachmentAdapter: CreatePostAttachmentsAdapter
    private lateinit var attachmentList: ArrayList<MediaUploadAttachmentModel>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentCreatePostTypeTextBinding.inflate(layoutInflater)
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        attachmentList = ArrayList()
        attachmentList.add(MediaUploadAttachmentModel(null, 0))
        attachmentAdapter = CreatePostAttachmentsAdapter(attachmentList,requireContext())
        viewBinding.createPostAttachmentRecyclerView.adapter = attachmentAdapter
        viewBinding.createPostEditView.setRichTextEditing(true, true)
        viewBinding.createPostEditView.setMentionHashTagListener(object : MentionHashTagListener {
            override fun onMentioning(sequence: CharSequence) {}
            override fun onHashTagging(sequence: CharSequence) {}
            override fun onStopMentioning() {}
            override fun onStopHashTags() {}
            override fun onMentionStarted() {}
            override fun onHashTagsStarted() {}
        })

        viewBinding.createPostBtn.setOnClickListener {
            LayoutUtils().requestPermission(requireActivity())
        }

    }

    override fun onResume() {
        super.onResume()
        activity?.window?.decorView?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == LayoutUtils().PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LayoutUtils().accessTheGallery(requireActivity())
            } else {
                Toast.makeText(context, "permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    @Deprecated("Deprecated in Java")
   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //get the image's file location
        val filePath = data?.data?.let { LayoutUtils().getRealPathFromUri(it, requireActivity()) }
        if (requestCode == LayoutUtils().PICK_IMAGE) {
            try {
                val imgMap: HashMap<String, Int>? = data?.data?.let { LayoutUtils().getImgSize(requireContext(),it) }
                attachmentAdapter.addToStart(MediaUploadAttachmentModel(uri = data?.data,  1, imgMap?.get("width"), imgMap?.get("height")))
                  //SocietyMediaManager().uploadToCloudinary(filePath,requireContext())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}