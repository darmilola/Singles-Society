package com.singlesSociety.UiModels.Utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager


class LayoutUtils {

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


}