package com.singlesSociety.UiModels

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MediaUploadAttachmentModel constructor(val uri: Uri? = null, val viewType: Int? = 0,val width: Int? = null, val height: Int?  = null) : Parcelable