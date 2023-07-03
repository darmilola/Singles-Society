package com.singlesSociety.UiModels

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.singlesSociety.R


@Suppress("unused")
class BottomNavIcon : RelativeLayout {

    companion object {
        const val EMPTY_VALUE = "empty"
    }

    var defaultIconColor = 0
        set(value) {
            field = value
            if (allowDraw)
                containerView.findViewById<CellImageView>(R.id.iv).color = if (!isEnabledCell) defaultIconColor else selectedIconColor
        }
    var selectedIconColor = Color.parseColor("#00C957")
        set(value) {
            field = value
            if (allowDraw)
                containerView.findViewById<CellImageView>(R.id.iv).color = if (isEnabledCell) selectedIconColor else defaultIconColor
        }



    var iconColor = Color.parseColor("#fa2d65")
        set(value) {
           field = value
            if (isCenterButton) {
                containerView.findViewById<CellImageView>(R.id.iv).color = iconColor
                progress = 1f
            }
        }

    var circleColor = 0
        set(value) {
            field = value
            if (allowDraw)
                isEnabledCell = isEnabledCell
        }

    var icon = 0
        set(value) {
            field = value
            if (allowDraw)
                containerView.findViewById<CellImageView>(R.id.iv).resource = value
        }

    var iconText = ""
        set(value) {
            field = value
          //  if (allowDraw)
                //tv.text = value
        }

    var iconTextColor = 0
        set(value) {
            field = value
          //  if (allowDraw) {
              //  if(!isEnabledCell) tv.setTextColor(iconTextColor) else tv.setTextColor(selectedIconTextColor)

       //     }
        }

    var selectedIconTextColor = 0
        set(value) {
            field = value
          //  if (allowDraw)
               // if(isEnabledCell) tv.setTextColor(selectedIconTextColor) else tv.setTextColor(iconTextColor)
        }

    var iconTextTypeface: Typeface? = null
        set(value) {
            field = value
          //  if (allowDraw && field != null)
              //  tv.typeface = field
        }

    var iconTextSize = 10f
        set(value) {
            field = value
            if (allowDraw) {
              //  tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, field)
            }
        }

    var count: String? = EMPTY_VALUE
        set(value) {
            field = value
            if (allowDraw) {
                if (count != null && count == EMPTY_VALUE) {
                    containerView.findViewById<TextView>(R.id.tv_count).text = ""
                    containerView.findViewById<TextView>(R.id.tv_count).visibility = View.INVISIBLE
                } else {
                    if (count != null && count?.length ?: 0 >= 3) {
                        field = count?.substring(0, 1) + ".."
                    }
                    containerView.findViewById<TextView>(R.id.tv_count).text = count
                    containerView.findViewById<TextView>(R.id.tv_count).visibility = View.VISIBLE
                    val scale = if (count?.isEmpty() == true) 0.5f else 1f
                    containerView.findViewById<TextView>(R.id.tv_count).scaleX = scale
                    containerView.findViewById<TextView>(R.id.tv_count).scaleY = scale
                }
            }
        }

    var iconSize = dip(context, 48)
        set(value) {
            field = value
            if (allowDraw) {
                containerView.findViewById<CellImageView>(R.id.iv).size = value
                containerView.findViewById<CellImageView>(R.id.iv).pivotX = iconSize / 2f
                containerView.findViewById<CellImageView>(R.id.iv).pivotY = iconSize / 2f
            }
        }

    var countTextColor = 0
        set(value) {
            field = value
            if (allowDraw)
                containerView.findViewById<TextView>(R.id.tv_count).setTextColor(field)
        }

    var countBackgroundColor = 0
        set(value) {
            field = value
            if (allowDraw) {
               // val d = GradientDrawable()
              //  d.setColor(field)
               // d.shape = GradientDrawable.OVAL
               // ViewCompat.setBackground(tv_count, d)
            }
        }

    var countTypeface: Typeface? = null
        set(value) {
            field = value
            if (allowDraw && field != null)
                containerView.findViewById<TextView>(R.id.tv_count).typeface = field
        }

    var rippleColor = 0
        set(value) {
            field = value
            if (allowDraw) {
                isEnabledCell = isEnabledCell
            }
        }

    var isFromLeft = false
    var duration = 0L
    private var progress = 0f
        set(value) {
            field = value
            Log.e("TAG","height is ${containerView.layoutParams.height} ${dip(context, 18)}")
            //fl.y = (1f - progress) * dip(context, 18) + dip(context, -2)

            containerView.findViewById<CellImageView>(R.id.iv).color = if (progress == 1f){
                selectedIconColor
            }
            else if(isCenterButton){
                selectedIconColor
            }else{
                defaultIconColor
            }
         //   tv.setTextColor(if (progress == 1f) selectedIconTextColor else defaultIconColor)
           // val scale = (1f - progress) * (-0.2f) + 1f
            //iv.scaleX = scale
            //iv.scaleY = scale

            /*val d = GradientDrawable()
            d.setColor(circleColor)
            d.shape = GradientDrawable.OVAL

            ViewCompat.setBackground(v_circle, d)

            ViewCompat.setElevation(v_circle, if (progress > 0.7f) dipf(context, progress * 4f) else 0f)

            val m = dip(context, 24)
            v_circle.x = (1f - progress) * (if (isFromLeft) -m else m) + ((measuredWidth - dip(context, 48)) / 2f)
            v_circle.y = (1f - progress) * measuredHeight + dip(context, 6)*/
        }

    var isEnabledCell = false
        set(value) {
            field = value
            val d = GradientDrawable()
            d.setColor(circleColor)
            d.shape = GradientDrawable.OVAL
            if (Build.VERSION.SDK_INT >= 21 && !isEnabledCell) {
                containerView.findViewById<LinearLayout>(R.id.fl).background = RippleDrawable(ColorStateList.valueOf(rippleColor), null, d)
            } else {
                containerView.findViewById<LinearLayout>(R.id.fl).runAfterDelay(200) {
                    containerView.findViewById<LinearLayout>(R.id.fl).setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }

    var isCenterButton = false

    var onClickListener: () -> Unit = {}
        set(value) {
            field = value
            containerView.findViewById<LinearLayout>(R.id.fl)?.setOnClickListener {
                onClickListener()
            }
        }

    private lateinit var containerView: View
    private var allowDraw = false

    constructor(context: Context) : super(context) {
        initializeView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setAttributeFromXml(context, attrs)
        initializeView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setAttributeFromXml(context, attrs)
        initializeView()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setAttributeFromXml(context: Context, attrs: AttributeSet) {
    }

    private fun initializeView() {
        allowDraw = true
        containerView = LayoutInflater.from(context).inflate(R.layout.bottom_nav_icon, this)
        draw()
    }

    private fun draw() {
        if (!allowDraw)
            return

        icon = icon
        count = count
        iconSize = iconSize
        iconTextTypeface = iconTextTypeface
        iconTextColor = iconTextColor
        selectedIconTextColor = selectedIconTextColor
        iconTextSize = iconTextSize
        countTextColor = countTextColor
        countBackgroundColor = countBackgroundColor
        countTypeface = countTypeface
        rippleColor = rippleColor
        onClickListener = onClickListener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        progress = progress
    }

    fun disableCell() {
        if (isEnabledCell)
            animateProgress(false)
        isEnabledCell = false
    }

    fun enableCell(isAnimate: Boolean = true) {
        if (!isEnabledCell)
            animateProgress(true, isAnimate)
        isEnabledCell = true
    }

    private fun animateProgress(enableCell: Boolean, isAnimate: Boolean = true) {
        val d = if (enableCell) duration else 250
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.apply {
            startDelay = if (enableCell) d / 4 else 0L
            duration = if (isAnimate) d else 1L
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener {
                val f = it.animatedFraction
                progress = if (enableCell)
                    f
                else
                    1f - f
            }
            start()
        }
    }
}