package com.singlesSociety.UiModels.Utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import java.io.File


class LayoutUtils() {

     val PERMISSION_CODE = 1
     val PICK_IMAGE = 1

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Int, context: Context): Float {
        return dp * (context.getResources()
            .getDisplayMetrics().densityDpi  / DisplayMetrics.DENSITY_DEFAULT).toFloat()
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.getResources()
            .getDisplayMetrics().densityDpi as Float / DisplayMetrics.DENSITY_DEFAULT)
    }


    class FadePageTransformer : ViewPager.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            view.setTranslationX(view.getWidth() * -position);

            if(position <= -1.0F || position >= 1.0F) {
                view.setAlpha(0.0F);
            } else if( position == 0.0F ) {
                view.setAlpha(1.0F);
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.setAlpha(1.0F - Math.abs(position));
            }
        }
    }


    @SuppressLint("Recycle")
   fun getRealPathFromUri(imageUri: Uri, activity: Activity): String? {
        val cursor = activity.contentResolver.query(imageUri, null, null, null, null)
        return if (cursor == null) {
            imageUri.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }


     fun getImgSize(context: Context,uri: Uri): HashMap<String,Int> {
         val imgMap = HashMap<String, Int>()
         val options = BitmapFactory.Options()
         options.inJustDecodeBounds = true // w  w  w. j av  a2 s.  c  o  m
         val imagePath = getMediaAbsolutePath(context, uri)
         val bitmap = BitmapFactory.decodeFile(imagePath, options)
         val height = options.outHeight
         val width = options.outWidth
         imgMap["height"] = height
         imgMap["width"] = width
         return imgMap
    }

    private fun getMediaAbsolutePath(ctx: Context, uri: Uri?): String? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = ctx.contentResolver.query(uri!!, filePathColumn, null, null, null)
        if (cursor == null || cursor.count == 0) return null
        cursor.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val picturePath = cursor.getString(columnIndex)
        cursor.close()
        return picturePath
    }


    fun requestPermission(activity: Activity) {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            accessTheGallery(activity)
        } else {
            ActivityCompat.requestPermissions(
                activity, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_CODE
            )
        }
    }


    fun accessTheGallery(activity: Activity) {
        val i: Intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        i.type = "image/*"
       activity.startActivityForResult(i, PICK_IMAGE)
    }



}