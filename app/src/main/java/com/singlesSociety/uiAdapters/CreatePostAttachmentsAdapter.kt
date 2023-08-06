package com.singlesSociety.uiAdapters

import android.app.Activity
import android.content.Context
import android.media.tv.TvTrackInfo.TYPE_VIDEO
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ui.PlayerView
import com.singlesSociety.R
import com.singlesSociety.UiModels.MediaUploadAttachmentModel
import com.singlesSociety.UiModels.Utils.LayoutUtils
import com.singlesSociety.UiModels.dip

class CreatePostAttachmentsAdapter(private val itemList: ArrayList<MediaUploadAttachmentModel>, val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return when (viewType) {
           TYPE_VIDEO -> {
               val view = LayoutInflater.from(parent.context)
                   .inflate(R.layout.post_upload_video_attachment_item, parent, false)
               VideoItemViewHolder(view)
           }
           TYPE_IMAGE -> {
               val view = LayoutInflater.from(parent.context)
                   .inflate(R.layout.post_upload_attachment_image_item, parent, false)
               ImageItemViewHolder(view)
           }
           TYPE_UPLOAD -> {
               val view = LayoutInflater.from(parent.context)
                   .inflate(R.layout.create_post_upload_attachment_cta, parent, false)
               UploadItemViewHolder(view)
           }
           else -> {
               val view = LayoutInflater.from(parent.context)
                   .inflate(R.layout.create_post_upload_attachment_cta, parent, false)
               return  UploadItemViewHolder(view)
           }
       }
    }

    fun addToStart(item: MediaUploadAttachmentModel){
        itemList.add(itemList.size-1,item)
        notifyItemInserted(itemList.size-1)
    }


    override fun getItemViewType(position: Int): Int {
        return when (itemList[position].viewType) {
            TYPE_IMAGE -> {
                TYPE_IMAGE
            }
            TYPE_VIDEO -> {
                TYPE_VIDEO
            }
            TYPE_UPLOAD -> {
                TYPE_UPLOAD
            }
            else -> {
                TYPE_VIDEO
            }
        }

    }



   override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       val attachmentObject: MediaUploadAttachmentModel = itemList[position]
       when (itemList[position].viewType) {
           TYPE_IMAGE -> {
               itemList[position].viewType?.let { computeDimensions(it,holder,attachmentObject) }
               val mHolder =  holder as ImageItemViewHolder
               mHolder.imgView.setImageURI(attachmentObject.uri)
               mHolder.removeAction.setOnClickListener {
                   if(position == itemList.size-1){
                       itemList.removeAt(position-1)
                       notifyItemRemoved(position-1)
                   }
                   else if(position == 0){
                       itemList.removeAt(0)
                       notifyItemRemoved(0)
                   }
                   else{
                       itemList.removeAt(position)
                       notifyItemRemoved(position)
                   }

               }

           }
           TYPE_VIDEO -> {
               itemList[position].viewType?.let { computeDimensions(it,holder,attachmentObject) }
               val mHolder =  holder as VideoItemViewHolder
               mHolder.removeAction.setOnClickListener {
                   itemList.removeAt(position)
                   notifyItemRemoved(position)
               }
           }
           TYPE_UPLOAD -> {
               val mHolder =  holder as UploadItemViewHolder
               mHolder.itemView.setOnClickListener {
                   LayoutUtils().requestPermission(context as Activity)
               }
           }
       }
    }

    private fun computeDimensions(viewType: Int, holder: RecyclerView.ViewHolder, attachmentObject: MediaUploadAttachmentModel){
        var mHolder: RecyclerView.ViewHolder? = null
        mHolder = when (viewType) {
            TYPE_IMAGE -> {
                holder as ImageItemViewHolder
            }
            TYPE_VIDEO -> {
                holder as VideoItemViewHolder
            }
            TYPE_UPLOAD -> {
                holder as UploadItemViewHolder
            }
            else -> {
                holder as VideoItemViewHolder
            }
        }
        val height: Int? = attachmentObject.height
        val width: Int? = attachmentObject.width

        if (height != null && width != null){
            val attachmentView = mHolder.itemView.findViewById<View>(R.id.attachmentView)
            if(height > width){
                attachmentView.layoutParams.height = dip(context,250)
                attachmentView.layoutParams.width = dip(context,180)
            }
            else if(height < width){
                attachmentView.layoutParams.height = dip(context,250)
                attachmentView.layoutParams.width = dip(context,300)
            }
            else if(height == width){
                attachmentView.layoutParams.height = dip(context,250)
                attachmentView.layoutParams.width = dip(context,250)
            }
            attachmentView.requestLayout()
        }

    }


   inner class ImageItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
       val imgView: ImageView = ItemView.findViewById(R.id.attachmentView)
       val removeAction: ImageView = ItemView.findViewById(R.id.removeAttachmentAction)
   }

   inner class VideoItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
       val videoView: PlayerView = ItemView.findViewById(R.id.attachmentView)
       val removeAction: ImageView = ItemView.findViewById(R.id.removeAttachmentAction)
   }

    inner class UploadItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    }

    companion object {
            private const val TYPE_IMAGE = 1
            private const val TYPE_VIDEO = 2
            private const val TYPE_UPLOAD = 0
      }
}