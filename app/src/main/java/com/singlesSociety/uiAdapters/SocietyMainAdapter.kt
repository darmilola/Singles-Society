package com.singlesSociety.uiAdapters

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnDragListener
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.exoplayer2.ui.PlayerView
import com.mackhartley.roundedprogressbar.RoundedProgressBar
import com.singlesSociety.Arvi.Config
import com.singlesSociety.Arvi.Direction
import com.singlesSociety.Arvi.util.misc.ExoPlayerUtils
import com.singlesSociety.Arvi.widget.CardStackLayoutManager
import com.singlesSociety.Arvi.widget.CardStackListener
import com.singlesSociety.Arvi.widget.PlayableCardStackView
import com.singlesSociety.Arvi.widget.PlayableItemViewHolder
import com.singlesSociety.Arvi.widget.PlayableItemsRecyclerView
import com.singlesSociety.Arvi.widget.PlaybackState
import com.singlesSociety.Arvi.widget.SwipeableMethod
import com.singlesSociety.OnSwipeTouchListener
import com.singlesSociety.R
import com.singlesSociety.SocietyPostDetail
import com.singlesSociety.SocietySwipeActivity
import com.singlesSociety.UiModels.CommunityPostTypeTextAttachmentModel
import com.singlesSociety.UiModels.MainActivityModel
import com.singlesSociety.UiModels.SocietyModel
import com.singlesSociety.fragments.EventBottomsheet
import com.singlesSociety.uiAdapters.CardStackAdapter.ScrollStateListener
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.smarteist.autoimageslider.SliderViewAdapter
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import de.hdodenhof.circleimageview.CircleImageView
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage
import java.util.Random
import java.util.concurrent.TimeUnit

class SocietyMainAdapter(
    private val context: Context,
    private val societyModelArrayList: ArrayList<SocietyModel>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var visitProfileListener: Function0<Unit>? = null
    private var profileMatchedListener: Function0<Unit>? = null
    private var profileEmptyListener: Function0<Unit>? = null
    private var exitSpaceListener: Function0<Unit>? = null
    private var datingProfileListener: Function0<Unit>? = null
    private var addACommentClickListener: Function0<Unit>? = null
    private var postListener: Function0<Unit>? = null
    private var cardStackAdapter: CardStackAdapter? = null
    fun setExitSpaceListener(exitSpaceListener: Function0<Unit>?) {
        this.exitSpaceListener = exitSpaceListener
    }

    fun setVisitProfileListener(visitProfileListener: Function0<Unit>) {
        this.visitProfileListener = visitProfileListener
    }

    fun setDatingProfileListener(datingProfileListener: Function0<Unit>?) {
        this.datingProfileListener = datingProfileListener
    }

    fun setAddACommentClickListener(addACommentClickListener: Function0<Unit>?) {
        this.addACommentClickListener = addACommentClickListener
    }

    fun setProfileEmptyListener(profileEmptyListener: Function0<Unit>?) {
        this.profileEmptyListener = profileEmptyListener
    }

    fun setPostListener(postListener: Function0<Unit>?) {
        this.postListener = postListener
    }

    fun setProfileMatchedListener(profileMatchedListener: Function0<Unit>?) {
        this.profileMatchedListener = profileMatchedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_MAIN) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.showcase_stack_layout, parent, false)
            ShowcaseItemViewHolder(view)
        } else (when (viewType) {
            TYPE_EVENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.community_post_type_live, parent, false)
                EventItemViewholder(view)
            }
            TYPE_TEXT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.community_post_type_text_v2, parent, false)
                TextPostItemViewHolder(view)
            }
            TYPE_TEXT_SHARED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.community_post_type_text_shared, parent, false)
                TextSharedPostItemViewHolder(view)
            }
            TYPE_EVENT_SHARED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.community_post_type_event_shared, parent, false)
                SharedEventItemViewholder(view)
            }
            TYPE_IMAGE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.community_post_type_image, parent, false)
                ShowcaseImageItemViewHolder(view)
            }
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.spaces_header_layout, parent, false)
                SpacesHeaderItemViewholder(view)
            }
            TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.post_loading_item, parent, false)
                SpacesLoadingItemViewholder(view)
            }
            else -> {
                null
            }
        })!!
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is ShowcaseItemViewHolder) {
            datingProfileListener!!.invoke()
        } else {
            postListener!!.invoke()
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val userProfileInPreview = arrayOf("")
        val cardPosition = intArrayOf(0)
        if (societyModelArrayList[position].itemViewType == TYPE_MAIN) {
            val userProfileToPreview = societyModelArrayList[position].userProfileToPreview
            val userLikedList = societyModelArrayList[position].usersLikedList
            cardStackAdapter = CardStackAdapter(userProfileToPreview, userLikedList, context)
            cardStackAdapter!!.setVisibleUserListener { userId -> userProfileInPreview[0] = userId }
            cardStackAdapter!!.setScrollStateListener(object : ScrollStateListener {
                override fun onReadyToMoveUp() {
                    //   onReadyToMoveUpListener.invoke();
                    Toast.makeText(context, "Top Reached", Toast.LENGTH_SHORT).show()
                }

                override fun onReadyToGoDown() {
                    //  onReadyToGoDownListener.invoke();
                    Toast.makeText(context, "End Reached", Toast.LENGTH_SHORT).show()
                }
            })
            (holder as ShowcaseItemViewHolder).swipeRight.setOnClickListener {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(com.yuyakaido.android.cardstackview.Direction.Right)
                    .setDuration(Duration.Slow.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                holder.manager.setSwipeAnimationSetting(setting)
                holder.showcaseCardStackRecyclerview.swipe()
            }
            holder.goFullView.setOnClickListener {
                val intent = Intent(context, SocietySwipeActivity::class.java)
                intent.putParcelableArrayListExtra(
                    "userProfileToPreview",
                    userProfileToPreview as ArrayList<out Parcelable?>
                )
                intent.putStringArrayListExtra("userLikedList", userLikedList)
                intent.putExtra(
                    "transitionName",
                    ViewCompat.getTransitionName(holder.itemView)
                )
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (context as Activity),
                    holder.itemView,
                    ViewCompat.getTransitionName(holder.itemView)!!
                )
                context.startActivity(intent, options.toBundle())
            }
            holder.swipeLeft.setOnClickListener {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(com.yuyakaido.android.cardstackview.Direction.Left)
                    .setDuration(Duration.Slow.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                holder.manager.setSwipeAnimationSetting(setting)
                holder.showcaseCardStackRecyclerview.swipe()
            }
            holder.showcaseCardStackRecyclerview.adapter =
                cardStackAdapter
            holder.manager.setListener(object : CardStackListener {
                override fun onCardDragging(direction: Direction, ratio: Float) {
                    // ((ShowcaseItemViewHolder) holder).swipeLayout.setVisibility(View.GONE);
                }

                override fun onCardSwiped(direction: Direction) {
                    if (direction == Direction.Right) {
                        val mainActivityModel = MainActivityModel(
                            userProfileInPreview[0],
                            context
                        )
                        mainActivityModel.setLiked()
                        profileMatchedListener!!.invoke()
                    }
                    if (cardPosition[0] == societyModelArrayList[position].userProfileToPreview.size - 1) {
                        profileEmptyListener!!.invoke()
                    }
                }

                override fun onCardRewound() {}
                override fun onCardCanceled() {}
                override fun onCardAppeared(view: View, position: Int) {}
                override fun onCardDisappeared(view: View, position: Int) {
                    cardPosition[0] = position
                }
            })
            holder.showcaseCardStackRecyclerview.requestLayout()
        } else if (societyModelArrayList[position].itemViewType == TYPE_IMAGE) {
            postListener!!.invoke()
            val showcaseImageItemViewHolder = holder as ShowcaseImageItemViewHolder
            val showcaseImageSliderAdapter: ShowcaseImageSliderAdapter = ShowcaseImageSliderAdapter(
                context
            )
            for (i in 0..4) {
                showcaseImageSliderAdapter.addItem(SliderItem("https://images.pexels.com/photos/3825527/pexels-photo-3825527.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"))
            }
            showcaseImageItemViewHolder.imageSlider.setSliderAdapter(showcaseImageSliderAdapter)
            showcaseImageItemViewHolder.scheduleEngagementViewDisappearance()
            showcaseImageItemViewHolder.imageSlider.startAutoCycle()
        }
    }

    override fun getItemCount(): Int {
        return societyModelArrayList.size
    }

    inner class SpacesHeaderItemViewholder(ItemView: View) :
        RecyclerView.ViewHolder(ItemView) {
        var exitCta: LinearLayout

        init {
            exitCta = ItemView.findViewById(R.id.spaceExitCta)
            exitCta.setOnClickListener { exitSpaceListener!!.invoke() }
        }
    }

    inner class SpacesLoadingItemViewholder(ItemView: View?) :
        RecyclerView.ViewHolder(ItemView!!)
    private lateinit var accountPicture: AvatarView

    inner class EventItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            accountPicture = ItemView.findViewById(R.id.userProfileImageViewWithIndicator)
            accountPicture.loadImage(context.resources.getDrawable(R.drawable.woman_official))
            ItemView.setOnClickListener {
                val manager =
                    (context as AppCompatActivity).supportFragmentManager
                EventBottomsheet().show(manager, "event")
            }

        }
    }


    inner class SharedEventItemViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            accountPicture = ItemView.findViewById(R.id.userProfileImageViewWithIndicator)
            accountPicture.loadImage(context.resources.getDrawable(R.drawable.woman_official))
            ItemView.setOnClickListener {
                val manager =
                    (context as AppCompatActivity).supportFragmentManager
                EventBottomsheet().show(manager, "event")
            }

        }
    }

    inner class TextPostItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        private var attachmentView: PlayableItemsRecyclerView
        private var commentLayout: LinearLayout
        private var accountPicture: AvatarView

        init {
            attachmentView = ItemView.findViewById(R.id.postAttachmentRecyclerView)
            val attachmentModel = CommunityPostTypeTextAttachmentModel(1)
            val attachmentModel2 = CommunityPostTypeTextAttachmentModel(2)
            val attachmentModel3 = CommunityPostTypeTextAttachmentModel(3)
            val attachmentModels = ArrayList<CommunityPostTypeTextAttachmentModel>()
            attachmentModels.add(attachmentModel)
            attachmentModels.add(attachmentModel)
            attachmentModels.add(attachmentModel)
            attachmentModels.add(attachmentModel2)
            attachmentModels.add(attachmentModel3)
            commentLayout = ItemView.findViewById(R.id.commentLayout)
            accountPicture = ItemView.findViewById(R.id.userProfileImageViewWithIndicator)
            commentLayout.setOnClickListener { addACommentClickListener!!.invoke() }
            accountPicture.setOnClickListener { visitProfileListener!!.invoke() }
            accountPicture.loadImage(context.resources.getDrawable(R.drawable.woman_official))
            itemView.setOnClickListener {
                context.startActivity(
                    Intent(
                        context,
                        SocietyPostDetail::class.java
                    )
                )
            }
            attachmentView.setHasFixedSize(true)
            attachmentView.adapter = CommunityPostTypeTextAttachmentAdapter(
                attachmentModels,
                context
            )
        }
    }

    inner class TextSharedPostItemViewHolder(ItemView: View) :
        RecyclerView.ViewHolder(ItemView) {
        var attachmentView: PlayableItemsRecyclerView
        var commentLayout: LinearLayout
        private var accountPicture: AvatarView

        init {
            attachmentView = ItemView.findViewById(R.id.postAttachmentRecyclerView)
            val attachmentModel = CommunityPostTypeTextAttachmentModel(1)
            val attachmentModel2 = CommunityPostTypeTextAttachmentModel(2)
            val attachmentModel3 = CommunityPostTypeTextAttachmentModel(3)
            val attachmentModels = ArrayList<CommunityPostTypeTextAttachmentModel>()
            attachmentModels.add(attachmentModel)
            attachmentModels.add(attachmentModel)
            attachmentModels.add(attachmentModel)
            attachmentModels.add(attachmentModel2)
            attachmentModels.add(attachmentModel3)
            commentLayout = ItemView.findViewById(R.id.commentLayout)
            commentLayout.setOnClickListener { addACommentClickListener!!.invoke() }
            accountPicture = ItemView.findViewById(R.id.userProfileImageViewWithIndicator)
            accountPicture.setOnClickListener { visitProfileListener!!.invoke() }
            accountPicture.loadImage(context.resources.getDrawable(R.drawable.woman_official))

            itemView.setOnClickListener {
                context.startActivity(
                    Intent(
                        context,
                        SocietyPostDetail::class.java
                    )
                )
            }
            attachmentView.setHasFixedSize(true)
            attachmentView.adapter = CommunityPostTypeTextAttachmentAdapter(
                attachmentModels,
                context
            )
        }
    }

    inner class ShowcaseItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var showcaseCardStackRecyclerview: PlayableCardStackView
        var manager: CardStackLayoutManager
        var swipeLayout: LinearLayout
        var swipeLeft: LinearLayout
        var rewound: LinearLayout
        var swipeRight: LinearLayout
        var goFullView: LinearLayout

        init {
            showcaseCardStackRecyclerview = ItemView.findViewById(R.id.showcaseCardStackView)
            swipeLayout = ItemView.findViewById(R.id.showcase_swipe_layout)
            swipeLeft = ItemView.findViewById(R.id.user_swipe_left)
            rewound = ItemView.findViewById(R.id.user_rewind)
            swipeRight = ItemView.findViewById(R.id.user_swipe_right)
            goFullView = ItemView.findViewById(R.id.cardStackGoFullView)
            manager = CardStackLayoutManager(context)
            manager.setVisibleCount(2)
            manager.setScaleInterval(1.0f)
            manager.setStackFrom(StackFrom.Top)
            manager.setSwipeThreshold(0.2f)
            manager.setMaxDegree(80.0f)
            manager.setDirections(Direction.HORIZONTAL)
            manager.setCanScrollHorizontal(true)
            manager.setCanScrollVertical(false)
            manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            showcaseCardStackRecyclerview.layoutManager = manager
        }
    }

    inner class ShowcaseImageItemViewHolder(ItemView: View) :
        RecyclerView.ViewHolder(ItemView), View.OnClickListener,
        OnDragListener {
        var imageSlider: SliderView
        var accountProfileImage: CircleImageView
        var alphaAnim2: AlphaAnimation? = null
        var engagementOverlay: FrameLayout
        var isCancelled = false
        var imagePostCard: CardView
        var addAComment: TextView

        init {
            imageSlider = ItemView.findViewById(R.id.imageSlider)
            val params = imageSlider.pagerIndicator.layoutParams as FrameLayout.LayoutParams
            imageSlider.pagerIndicator.elevation = 50f
            imageSlider.requestLayout()
            imageSlider.pagerIndicator.requestLayout()
            imageSlider.isAutoCycle = true
            imageSlider.setIndicatorAnimation(IndicatorAnimationType.FILL)
            imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            imageSlider.indicatorSelectedColor = Color.parseColor("#fa2d65")
            imageSlider.indicatorUnselectedColor = Color.GRAY
            imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
            accountProfileImage = ItemView.findViewById(R.id.accountProfilePicture)
            engagementOverlay = ItemView.findViewById(R.id.postEngagementOverlay)
            imagePostCard = ItemView.findViewById(R.id.imagePostCard)
            addAComment = itemView.findViewById(R.id.societyPostAddAComment)
            imagePostCard.requestFocusFromTouch()
            imagePostCard.requestFocus()
            imageSlider.scrollTimeInSec = 7
            itemView.setOnClickListener {
                engagementOverlay.visibility = View.VISIBLE
                // scheduleEngagementViewDisappearance();
            }
            addAComment.setOnClickListener { addACommentClickListener!!.invoke() }
            accountProfileImage.setOnClickListener { visitProfileListener!!.invoke() }
        }

        override fun onClick(view: View) {
            engagementOverlay.visibility = View.VISIBLE
            //  scheduleEngagementViewDisappearance();
        }

        fun scheduleEngagementViewDisappearance() {
            alphaAnim2 = AlphaAnimation(1.0f, 0.0f)
            alphaAnim2!!.startOffset = 3000
            alphaAnim2!!.duration = 2000
            alphaAnim2!!.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    if (isCancelled) {
                        engagementOverlay.visibility = View.VISIBLE
                        isCancelled = false
                    } else {
                        engagementOverlay.visibility = View.GONE
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            engagementOverlay.animation = alphaAnim2
        }

        override fun onDrag(view: View, dragEvent: DragEvent): Boolean {
            return false
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (societyModelArrayList[position].itemViewType) {
            TYPE_MAIN -> {
                TYPE_MAIN
            }
            TYPE_IMAGE -> {
                TYPE_IMAGE
            }
            TYPE_HEADER -> {
                TYPE_HEADER
            }
            TYPE_TEXT -> {
                TYPE_TEXT
            }
            TYPE_TEXT_SHARED -> {
                TYPE_TEXT_SHARED
            }
            TYPE_EVENT_SHARED -> {
                TYPE_EVENT_SHARED
            }
            TYPE_EVENT -> {
                TYPE_EVENT
            }
            TYPE_LOADING -> {
                TYPE_LOADING
            }
            else -> {
                TYPE_TEXT
            }
        }
    }

    inner class SliderItem internal constructor(var imageUrl: String) {
        var description: String? = null

    }

    inner class ShowcaseImageSliderAdapter(private val context: Context) :
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
            Glide.with(viewHolder.itemView)
                .load(sliderItem.imageUrl)
                .fitCenter()
                .into(viewHolder.imageViewBackground)
            viewHolder.itemView.setOnClickListener { }
        }

        override fun getCount(): Int {
            //slider view count could be dynamic size
            return mSliderItems.size
        }

        inner class SliderAdapterVH(itemView: View) :
            ViewHolder(itemView) {
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

    inner class CommunityPostItemTypeVideoViewHolder(parentViewGroup: ViewGroup?, itemView: View) :
        PlayableItemViewHolder(parentViewGroup, itemView), View.OnClickListener {
        var transparentOverlay: RelativeLayout
        var touchArea: FrameLayout
        var progressToolLayout: LinearLayout
        var videoProgress: RoundedProgressBar
        var playPauseLayout: LinearLayout
        var loader: LottieAnimationView
        var thumbnail: SimpleDraweeView
        var player: PlayerView
        var controller: LinearLayout
        var playbackState: PlaybackState? = null
        var alphaAnim2: AlphaAnimation? = null
        var playPauseView: LottieAnimationView
        var playbackCacheID = ""
        private val mHandler = Handler()
        var isCancelled = false
        var isPaused = false
        var isReady = false
        var isStarted = false
        private val playerProgress = 0
        var addAComment: TextView
        private val accountProfileImage: CircleImageView

        init {
            this.config = Config.Builder().cache(
                ExoPlayerUtils.getCache(
                    context
                )
            ).build()
            player = itemView.findViewById(R.id.player_view)
            playPauseView = itemView.findViewById(R.id.video_play_pause_view)
            playPauseLayout = itemView.findViewById(R.id.video_play_pause_layout)
            loader = itemView.findViewById(R.id.video_loader_view)
            thumbnail = itemView.findViewById(R.id.video_thumbnail)
            touchArea = itemView.findViewById(R.id.video_frame)
            transparentOverlay = itemView.findViewById(R.id.video_transparent_overlay)
            videoProgress = itemView.findViewById(R.id.video_progressbar)
            progressToolLayout = itemView.findViewById(R.id.video_progress_layout)
            controller = itemView.findViewById(R.id.video_controller)
            addAComment = itemView.findViewById(R.id.societyPostAddAComment)
            playPauseView.visibility = View.GONE
            playPauseView.setMinAndMaxFrame(0, 40)
            playPauseView.resumeAnimation()
            accountProfileImage = itemView.findViewById(R.id.accountProfilePicture)
            addAComment.setOnClickListener { addACommentClickListener!!.invoke() }
            accountProfileImage.setOnClickListener { visitProfileListener!!.invoke() }
            setOnGestureListeners()
            playPauseView.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    playPauseView.pauseAnimation()
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
            playPauseView.setOnClickListener {
                if (isNewPlayer) {
                    playbackCacheID = generatePlaybackCacheID()
                }
                setPlayBackCacheID(playbackCacheID)
                if (isReady && isPlaying && isTrulyPlayable) {
                    if (alphaAnim2 != null && !alphaAnim2!!.hasEnded()) {
                        isCancelled = true
                        alphaAnim2!!.cancel()
                    }
                    isPaused = true
                    isPausedByUser = true
                    pause()
                } else {
                    if (alphaAnim2 != null && !alphaAnim2!!.hasEnded()) {
                        isCancelled = true
                        alphaAnim2!!.cancel()
                    }
                    start()
                }
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        private fun setOnGestureListeners() {
            mPlayerView.setOnTouchListener(object : OnSwipeTouchListener(context) {
                override fun onSwipeRight() {
                    super.onSwipeRight()
                    Log.e("right", "onSwipeRight: ")
                }

                override fun onSwipeLeft() {
                    super.onSwipeLeft()
                    Log.e("left", "onSwipeLeft: ")
                }

                override fun onClick() {
                    super.onClick()
                    if (isReady && isPlaying && isTrulyPlayable) {
                        if (alphaAnim2 != null && !alphaAnim2!!.hasEnded()) {
                            isCancelled = true
                            alphaAnim2!!.cancel()
                        }
                        transparentOverlay.visibility = View.VISIBLE
                        // progressToolLayout.setVisibility(View.VISIBLE);
                        playPauseView.visibility = View.VISIBLE
                        //  playPauseLayout.setVisibility(View.VISIBLE);
                        //    scheduleVideoProgressToolDisappearance();
                    }
                }

                override fun onDoubleClick() {
                    super.onDoubleClick()
                    if (isReady && isPlaying && isTrulyPlayable) {
                        if (alphaAnim2 != null && !alphaAnim2!!.hasEnded()) {
                            isCancelled = true
                            alphaAnim2!!.cancel()
                        }
                        isPaused = true
                        isPausedByUser = true
                        pause()
                    }
                }
            })
        }

        override fun getTriggerOffset(): Float {
            return 0.999f
        }

        fun setUrl(url: String) {
            this.url = url
        }

        override fun isLooping(): Boolean {
            return true
        }

        /**
         * Retrieves the media Url associated with this item.
         *
         * @return the media url
         */
        override fun getUrl(): String {
            return url
        }

        override fun getConfig(): Config {
            return config
        }

        override fun getDuration(): Long {
            return super.getDuration()
        }

        fun generatePlaybackCacheID(): String {
            val SALTCHARS = "ABCDEFGHIJLMNOPQRSTUVWXYZ123456890"
            val salt = StringBuilder()
            val random = Random()
            while (salt.length < 18) {
                val index = (random.nextFloat() * SALTCHARS.length).toInt()
                salt.append(SALTCHARS[index])
            }
            return salt.toString()
        }

        public override fun onStateChanged(playbackState: PlaybackState) {
            super.onStateChanged(playbackState)
            this.playbackState = playbackState
            if (playbackState == PlaybackState.BUFFERING) onBufferingState()
            if (playbackState == PlaybackState.ERROR) onErrorState()
            if (playbackState == PlaybackState.PAUSED) onPausedState()
            if (playbackState == PlaybackState.READY) onReadyState()
            if (playbackState == PlaybackState.STOPPED) onStoppedState()
            if (playbackState == PlaybackState.STARTED) onStartedState()
        }

        private fun onStartedState() {
            Log.e("started", "onStartedState: ")
            loader.visibility = View.VISIBLE
            transparentOverlay.visibility = View.VISIBLE
            isStarted = true
            isReady = false
            inReadyState = false
            if (isPaused) {
                playPauseView.setMaxFrame(40)
                isPaused = false
            } else {


                //    progressToolLayout.setVisibility(View.VISIBLE);
                thumbnail.visibility = View.VISIBLE
                loader.visibility = View.VISIBLE
                playPauseView.visibility = View.GONE
                playPauseLayout.visibility = View.GONE
            }
        }

        private fun onBufferingState() {
            Log.e("buffering", "onBufferingState: ")
            isReady = true
            loader.visibility = View.VISIBLE
            thumbnail.visibility = View.GONE
            transparentOverlay.visibility = View.VISIBLE
            //   progressToolLayout.setVisibility(View.VISIBLE);
        }

        private fun onReadyState() {
            if (isStarted && !isPaused) {
                isStarted = false
                //  scheduleVideoProgressToolDisappearance();
            }
            Log.e("ready", "onReadyState: ")
            isReady = true
            inReadyState = isReady
            loader.visibility = View.GONE
            thumbnail.visibility = View.GONE
            transparentOverlay.visibility = View.GONE
            // progressToolLayout.setVisibility(View.VISIBLE);
            playPauseView.setMaxFrame(40)
            val mDuration = duration.toInt()

            //videoProgress.setMax((int) getDuration());
            (context as Activity).runOnUiThread(object : Runnable {
                override fun run() {
                    if (mPlayerView != null) {
                        val mCurrentPosition = TimeUnit.MILLISECONDS.toSeconds(
                            playbackPosition
                        ).toInt() % 60
                        val totalSeconds = TimeUnit.MILLISECONDS.toSeconds(
                            duration
                        ).toInt()
                        val percentProgress =
                            java.lang.Double.valueOf((mCurrentPosition.toFloat() / totalSeconds * 100).toDouble())
                        videoProgress.setProgressPercentage(percentProgress, true)
                    }
                    mHandler.postDelayed(this, 1000)
                }
            })

            /*   videoProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        playerProgress = progress;
                        if (mPlayerView != null && fromUser) {
                            if (alphaAnim2 != null && !alphaAnim2.hasEnded()) {
                                isCancelled = true;
                                alphaAnim2.cancel();

                            }
                            seekTo(progress);
                            videoProgress.setProgress(progress);
                            isStarted = false;
                            scheduleVideoProgressToolDisappearance();
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });*/
        }

        private fun onPausedState() {
            Log.e("pause", "onPausedState: ")
            if (alphaAnim2 != null && !alphaAnim2!!.hasEnded()) {
                isCancelled = true
                alphaAnim2!!.cancel()
            }
            transparentOverlay.visibility = View.VISIBLE
            // progressToolLayout.setVisibility(View.VISIBLE);
            playPauseView.visibility = View.VISIBLE
            playPauseLayout.visibility = View.VISIBLE
            loader.visibility = View.GONE
            thumbnail.visibility = View.GONE
            isPaused = true
            playPauseView.setMinAndMaxFrame(40, 140)
            playPauseView.resumeAnimation()
        }

        private fun onStoppedState() {
            isPausedByUser = false
            Log.e("stopped", "onStoppedState: ")
            isReady = false
            inReadyState = isReady
            if (alphaAnim2 != null && !alphaAnim2!!.hasEnded()) {
                isCancelled = true
                alphaAnim2!!.cancel()
            }
            playPauseView.setMinAndMaxFrame(40, 140)
            playPauseView.resumeAnimation()
            playPauseView.visibility = View.VISIBLE
            playPauseLayout.visibility = View.VISIBLE
            thumbnail.visibility = View.VISIBLE
            loader.visibility = View.GONE
            transparentOverlay.visibility = View.VISIBLE
            // progressToolLayout.setVisibility(View.GONE);
        }

        private fun onErrorState() {
            isPausedByUser = false
            Log.e("started", "onErrorState: ")
            isReady = false
            inReadyState = isReady
            if (alphaAnim2 != null && !alphaAnim2!!.hasEnded()) {
                isCancelled = true
                alphaAnim2!!.cancel()
            }
            playPauseView.setMinAndMaxFrame(40, 140)
            playPauseView.resumeAnimation()
            loader.visibility = View.GONE
            playPauseView.visibility = View.VISIBLE
            transparentOverlay.visibility = View.VISIBLE
            //  progressToolLayout.setVisibility(View.GONE);
            thumbnail.visibility = View.VISIBLE
        }

        fun scheduleVideoProgressToolDisappearance() {
            alphaAnim2 = AlphaAnimation(1.0f, 0.0f)
            alphaAnim2!!.startOffset = 5000
            alphaAnim2!!.duration = 3000
            alphaAnim2!!.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    if (isCancelled) {
                        transparentOverlay.visibility = View.VISIBLE
                        playPauseView.visibility = View.VISIBLE
                        playPauseLayout.visibility = View.VISIBLE
                        //  progressToolLayout.setVisibility(View.VISIBLE);
                        isCancelled = false
                    } else {
                        playPauseView.visibility = View.GONE
                        playPauseLayout.visibility = View.GONE
                        transparentOverlay.visibility = View.GONE
                        //   progressToolLayout.setVisibility(View.VISIBLE);
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            transparentOverlay.animation = alphaAnim2
        }

        override fun onClick(view: View) {}
    }

    companion object {
        private const val TYPE_MAIN = 0
        private const val TYPE_IMAGE = 2
        private const val TYPE_HEADER = 3
        private const val TYPE_TEXT = 4
        private const val TYPE_EVENT = 5
        private const val TYPE_TEXT_SHARED = 7
        private const val TYPE_EVENT_SHARED = 8
        private const val TYPE_LOADING = 6
    }
}