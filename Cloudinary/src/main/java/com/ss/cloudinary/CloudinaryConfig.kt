package com.ss.cloudinary

import android.content.Context
import com.cloudinary.android.MediaManager




class CloudinaryConfig constructor(private val context: Context) {

     fun initManager() {
        MediaManager.init(context)
    }
}