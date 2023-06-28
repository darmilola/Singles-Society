package com.singlesSociety

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.community_post_engagement_view.*
import kotlinx.android.synthetic.main.fragment_image_post_full_view.*

class ImagePostFullView(private var imagePostProfileVisitListener: Function0<Unit>? = null) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_post_full_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var showcaseImageSliderAdapter: ShowcaseImageSliderAdapter = ShowcaseImageSliderAdapter(requireContext())

        for (i in 0..4) {
            showcaseImageSliderAdapter.addItem(SliderItem("https://images.pexels.com/photos/3825527/pexels-photo-3825527.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"))
        }

        imageSlider.setSliderAdapter(showcaseImageSliderAdapter)
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!

        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH)
        imageSlider.setIndicatorSelectedColor(Color.parseColor("#fa2d65"))
        imageSlider.setIndicatorUnselectedColor(Color.GRAY)
        imageSlider.setScrollTimeInSec(4) //set scroll delay in seconds :
        imageSlider.startAutoCycle()

        accountProfilePicture.setOnClickListener {
              imagePostProfileVisitListener?.invoke()
        }


    }


    class SliderItem internal constructor(var imageUrl: String) {
        var description: String? = null

    }


    private class ShowcaseImageSliderAdapter(private val context: Context) :
        SliderViewAdapter<ShowcaseImageSliderAdapter.SliderAdapterVH>() {
        private var mSliderItems: MutableList<SliderItem> = ArrayList()
        fun renewItems(sliderItems: MutableList<SliderItem>) {
            mSliderItems = sliderItems
            notifyDataSetChanged()
        }

        fun deleteItem(position: Int) {
            mSliderItems.removeAt(position)
            notifyDataSetChanged()
        }

        fun addItem(sliderItem: SliderItem) {
            mSliderItems.add(sliderItem)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
            val inflate =
                LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
            return SliderAdapterVH(inflate)
        }

        override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
            val sliderItem = mSliderItems[position]
            viewHolder.textViewDescription.text = sliderItem.description
            viewHolder.textViewDescription.textSize = 16f
            viewHolder.textViewDescription.setTextColor(Color.WHITE)
            Glide.with(context)
                .load(sliderItem.imageUrl)
                .fitCenter()
                .into(viewHolder.imageViewBackground)
        }

        override fun getCount(): Int {
            //slider view count could be dynamic size
            return mSliderItems.size
        }

        internal inner class SliderAdapterVH(itemView: View) :
            ViewHolder(itemView) {
            lateinit var itemView: View
            var imageViewBackground: ImageView
            var imageGifContainer: ImageView
            var textViewDescription: TextView

            init {
                imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider)
                imageGifContainer = itemView.findViewById(R.id.iv_gif_container)
                textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider)
            }
        }
    }
}