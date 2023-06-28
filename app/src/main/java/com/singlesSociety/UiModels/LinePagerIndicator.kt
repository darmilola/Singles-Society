package com.singlesSociety.UiModels

import android.content.res.Resources
import android.graphics.Canvas
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.State

class LinePagerIndicator(val itemCount: Int = 0): RecyclerView.ItemDecoration() {

    private val dashIndicator = DashIndicator()

    private val mDp = Resources.getSystem().displayMetrics.density
    private val mIndicatorStrokeWidth = mDp * 2

    /**
     * Indicator width.
     */
    private val mIndicatorItemLength = mDp * 12

    /**
     * Padding between indicators.
     */
    private val mIndicatorItemPadding = mDp * 8

    private val interpolator = AccelerateDecelerateInterpolator()



    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: State) {
        super.onDrawOver(c, parent, state)

        val itemCount = this.itemCount

        dashIndicator.itemCount = itemCount
        val xPosition = dashIndicator.getX(parent)
       // val yPosition = dashIndicator.getY(parent)

        // center horizontally, calculate width and subtract half from center

        // center horizontally, calculate width and subtract half from center
        val totalLength: Float = mIndicatorItemLength * itemCount
        val paddingBetweenItemsI: Float = Math.max(0, itemCount - 1) * mIndicatorItemPadding
        val totalIndicatorHeight = totalLength + paddingBetweenItemsI
        val yPosition = (parent.height - totalIndicatorHeight) / 9f
       // val xPosition: Float = parent.width  / 0.6f

        drawInactiveIndicators(c, xPosition, yPosition, itemCount)
        drawActiveIndicator(c, parent, xPosition, yPosition, itemCount)
    }

    private fun drawInactiveIndicators(
            c: Canvas,
            indicatorStartX: Float,
            indicatorPosY: Float,
            itemCount: Int
    ) {
        for (position in 0 until itemCount) {
            val startX = dashIndicator.getInActiveStartX(indicatorStartX, position)
            val startY = dashIndicator.getStartY(indicatorPosY, position)
            val endX = startX + dashIndicator.getItemLength()
            val paint = dashIndicator.getInActivePaint()
            val endY = startY + dashIndicator.getItemLength()

            c.drawLine(
                    startX,
                    startY,
                    startX,
                    endY,
                    paint
            )
        }
    }


    private fun drawActiveIndicator(
            c: Canvas,
            parent: RecyclerView,
            indicatorStartX: Float,
            indicatorPosY: Float,
            itemCount: Int
    ) {
        val layoutManager = parent.layoutManager as LinearLayoutManager
        val itemPosition = layoutManager.findFirstVisibleItemPosition()

        if (itemPosition == NO_POSITION) return

        val itemView = layoutManager.findViewByPosition(itemPosition) ?: return
        val viewLeft = itemView.left
        val viewWidth = itemView.width

        val progress = interpolator.getInterpolation(viewLeft * -1 / viewWidth.toFloat())



        if (progress == 0f) {
            val startX = dashIndicator.getInActiveStartX(indicatorStartX, itemPosition)
            val startY = dashIndicator.getStartY(indicatorPosY, itemPosition)
            val endX = startX + dashIndicator.getItemLength()
            val paint = dashIndicator.getActivePaint()
            val endY = startY + dashIndicator.getItemLength()

            c.drawLine(
                    startX,
                    startY,
                    startX,
                    endY,
                    paint
            )
        } else {
            val startX = dashIndicator.getInActiveStartX(indicatorStartX, itemPosition)
            val startY = dashIndicator.getStartY(indicatorPosY, itemPosition)
            val endX = startX + dashIndicator.getItemLength()
            val paint = dashIndicator.getActivePaint()
            val endY = startY + dashIndicator.getItemLength()

            c.drawLine(
                    startX,
                    startY,
                    startX,
                    endY,
                    paint
            )
            // draw partial left

        }
    }


}