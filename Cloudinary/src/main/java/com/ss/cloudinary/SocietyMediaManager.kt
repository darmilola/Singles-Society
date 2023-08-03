package com.ss.cloudinary

import android.content.Context
import android.util.Log
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.MediaManager


class SocietyMediaManager {

    fun uploadToCloudinary(filePath: String?, context: Context) {
        Log.d("A", "sign up uploadToCloudinary- ")
        MediaManager.get().upload(filePath).callback(object : UploadCallback {
            override fun onStart(requestId: String) {
                Log.e("starting ","onStart: ")
            }
            override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                Log.e("progress ",bytes.toString())
            }

            override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                Log.e("success", resultData.toString())
            }

            override fun onError(requestId: String, error: ErrorInfo) {
                Log.e("error",requestId)
            }
            override fun onReschedule(requestId: String, error: ErrorInfo) {
                Log.e("error",requestId)
            }
        }).dispatch()
    }

}