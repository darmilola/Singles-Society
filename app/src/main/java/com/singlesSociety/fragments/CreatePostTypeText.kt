package com.singlesSociety.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.SinglesSociety.SocialText.RichTextController.MentionHashTagListener
import com.singlesSociety.UiModels.MediaUploadAttachmentModel
import com.singlesSociety.UiModels.Utils.LayoutUtils
import com.singlesSociety.databinding.FragmentCreatePostTypeTextBinding
import com.singlesSociety.uiAdapters.CreatePostAttachmentsAdapter
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
        if (requestCode == LayoutUtils().SELECT_ATTACHMENTS) {
            try {

                Log.e("MimeType ", data?.data?.let { getMimeType(uri = it) }!!)
                if(data.data?.let { getMimeType(it).equals("png",true) || getMimeType(it).equals("jpg",true) } == true){
                    val attachmentSizeMap: HashMap<String, Int>? = data.data?.let {
                        LayoutUtils().getAttachmentSize(requireContext(),it)
                    }
                    attachmentAdapter.addToStart(MediaUploadAttachmentModel(uri = data.data,  1, attachmentSizeMap?.get("width"), attachmentSizeMap?.get("height")))
                }
                else{
                    val attachmentSizeMap: HashMap<String, Int?>? = data.data?.let {
                        LayoutUtils().getVideoAttachmentSize(requireContext(),it)
                    }
                    attachmentAdapter.addToStart(MediaUploadAttachmentModel(uri = data.data,  2, attachmentSizeMap?.get("width"), attachmentSizeMap?.get("height")))
                }

                  //SocietyMediaManager().uploadToCloudinary(filePath,requireContext())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getMimeType(uri: Uri): String? {
        val cR = requireContext().contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

}