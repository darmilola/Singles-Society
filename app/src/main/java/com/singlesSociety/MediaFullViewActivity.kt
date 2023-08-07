package com.singlesSociety

import android.app.Activity
import android.content.Intent
import android.graphics.Matrix
import android.graphics.Point
import android.graphics.RectF
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.singlesSociety.UiModels.MediaUploadAttachmentModel
import com.singlesSociety.UiModels.dip
import com.singlesSociety.databinding.ActivityMainV2Binding
import com.singlesSociety.databinding.ActivityMediaFullViewBinding
import com.singlesSociety.uiAdapters.CreatePostAttachmentsAdapter

class MediaFullViewActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMediaFullViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMediaFullViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val attachmentObj: MediaUploadAttachmentModel? = intent.getParcelableExtra("info")
        val viewType: Int = intent.getIntExtra("type", TYPE_IMAGE)
        computeDimensions(1,attachmentObj!!)
        if(viewType == TYPE_IMAGE){
            viewBinding.attachmentImageView.isVisible = true
            viewBinding.attachmentImageView.setImageURI(attachmentObj.uri)
        }
        else if(viewType == TYPE_VIDEO){
            viewBinding.attachmentVideoView.isVisible = true
            //viewBinding.attachmentImageView.setImageURI(attachmentObj.uri)
        }

    }

    private fun computeDimensions(viewType: Int = TYPE_IMAGE, attachmentObject: MediaUploadAttachmentModel){

        val height: Int? = attachmentObject.height
        val width: Int? = attachmentObject.width

        val display: Display = windowManager.defaultDisplay   //context as ac .getWindowManager().getDefaultDisplay()
        val size = Point()
        display.getSize(size)
        val  screenWidthInPixel = size.x
        val screenHeightInPixel = size.y

        val factorX = screenWidthInPixel/width!!
        val factorY = screenHeightInPixel/height!!

        val factor: Float = if(factorX < factorY){
            factorX.toFloat()
        }
        else{
            factorY.toFloat()
        }

        val matrix = Matrix()
        matrix.postScale(factor, factor)

        val attachmentView =  if(viewType == TYPE_IMAGE){
             viewBinding.attachmentImageView
        }
        else{
            viewBinding.attachmentVideoView
        }

        val drawableRect = RectF(0f, 0f,width.toFloat(), height.toFloat())
        val viewRect = RectF(0f, 0f, attachmentView.width.toFloat(), attachmentView.height.toFloat())
        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER)

        /*if (height != null && width != null){
            if(height > width){
                attachmentView.layoutParams.height = dip(this,250)
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
        }*/

    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
           // window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    companion object {
        private const val TYPE_IMAGE = 1
        private const val TYPE_VIDEO = 2
    }
}