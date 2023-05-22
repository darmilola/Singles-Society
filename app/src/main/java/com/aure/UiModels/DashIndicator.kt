package com.aure.UiModels

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class DashIndicator: Indicator() {

    // Length of each indicator
    private val length = 12.toPx()
    private val margin = 8.toPx()
    private val paddingBottom = 16.toPx()
    private val lineStrokeWidth = (1.7f).toPx()
    private val paint = Paint()
    private val colorActive = 0xFFFFFFFF
    private val colorInactive = 0x80FFFFFF
    private var myStaticStartX: Float? = null

    var itemCount = 0

    init {
        paint.apply {
            strokeCap = Paint.Cap.ROUND
            strokeWidth = lineStrokeWidth.toFloat()
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
    }

    fun getX(parent: RecyclerView): Float {
        val totalLength = getTotalLength()
        return parent.width - 70f
    }

    fun getY(parent: RecyclerView): Float {
        return (parent.height - paddingBottom).toFloat()
    }

    private fun getTotalLength(): Int {
        val indicatorTotalLength = getIndicatorsLength()
        val indicatorSpacingLength = getSpacingLength()
        return indicatorTotalLength + indicatorSpacingLength
    }

    private fun getIndicatorsLength(): Int {
        return itemCount * length
    }

    private fun getSpacingLength(): Int {
        val totalSpace = 0.coerceAtLeast(itemCount - 1)
        return totalSpace * margin
    }

    private fun setActive() {
        paint.color = Color.parseColor("#fa2d65")
    }

    private fun setInActive() {
        paint.color = colorInactive.toInt()
    }

    fun getInActiveStartX(indicatorStartX: Float, position: Int): Float {
        val previousIndicatorLength = position * length
        val previousSpacingLength = position * margin
        val previousLength = previousIndicatorLength + previousSpacingLength
        if(myStaticStartX == null) myStaticStartX = indicatorStartX + previousLength
        return myStaticStartX as Float
    }

    fun getStartX(indicatorStartX: Float, position: Int): Float {
        val previousIndicatorLength = position * length
        val previousSpacingLength = position * margin
        val previousLength = previousIndicatorLength + previousSpacingLength

        return indicatorStartX + previousLength
    }

    fun getStartY(indicatorStartY: Float, position: Int): Float {
        val previousIndicatorLength = position * length
        val previousSpacingLength = position * margin
        val previousLength = previousIndicatorLength + previousSpacingLength
        return indicatorStartY + previousLength
    }

    fun getItemLength(): Float {
        return length.toFloat()
    }

    fun getInActivePaint(): Paint {
        setInActive()
        return paint
    }

    fun getActivePaint(): Paint {
        setActive()
        return paint
    }

    fun getItemLengthWithMargin(): Int {
        return length + margin
    }
}