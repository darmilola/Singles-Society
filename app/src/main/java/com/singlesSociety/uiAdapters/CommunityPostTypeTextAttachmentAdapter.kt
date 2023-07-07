package com.singlesSociety.uiAdapters

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.singlesSociety.Arvi.Config
import com.singlesSociety.Arvi.util.misc.ExoPlayerUtils
import com.singlesSociety.Arvi.widget.PlayableItemViewHolder
import com.singlesSociety.Arvi.widget.PlaybackState
import com.singlesSociety.OnSwipeTouchListener
import com.singlesSociety.R
import com.singlesSociety.UiModels.CommunityPostTypeTextAttachmentModel
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import com.google.android.exoplayer2.ui.PlayerView
import com.mackhartley.roundedprogressbar.RoundedProgressBar
import java.util.*
import java.util.concurrent.TimeUnit


private const val ATTACHMENT_TYPE_IMAGE: Int = 1
private const val ATTACHMENT_TYPE_VIDEO: Int = 2
private const val ATTACHMENT_TYPE_LINK: Int = 3

class CommunityPostTypeTextAttachmentAdapter(private val attachmentList: ArrayList<CommunityPostTypeTextAttachmentModel>, private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (attachmentList[position].attachmentType == ATTACHMENT_TYPE_VIDEO) {
            val vi = holder as AttachmentVideoItemViewHolder
            vi.setUrl("https://joy1.videvo.net/videvo_files/video/free/2016-12/large_watermarked/Code_flythough_loop_01_Videvo_preview.mp4")
            val request =
                ImageRequest.fromUri("https://images.pexels.com/photos/3825527/pexels-photo-3825527.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2")
            val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(vi.thumbnail.controller).build()
            vi.thumbnail.controller = controller
            vi.onStoppedState()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ATTACHMENT_TYPE_IMAGE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_text_image_attachment_v2, parent, false)
            return AttachmentPictureItemViewHolder(view)
        }
        if (viewType == ATTACHMENT_TYPE_LINK) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_text_type_link, parent, false)
            return AttachmentPictureItemViewHolder(view)
        }
        if (viewType == ATTACHMENT_TYPE_VIDEO) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_text_video_attachment, parent, false)
            return AttachmentVideoItemViewHolder(parent,view)
        }

       val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_text_image_attachment, parent, false)
            return AttachmentPictureItemViewHolder(view)

    }

    override fun getItemCount(): Int {
        return attachmentList.size
    }


    class AttachmentPictureItemViewHolder(ItemView: View) :
        RecyclerView.ViewHolder(ItemView) {

    }

    class AttachmentVideoItemViewHolder(
        parentViewGroup: ViewGroup,
        itemView: View
    ) :
        PlayableItemViewHolder(parentViewGroup, itemView) {
        var transparentOverlay: RelativeLayout
        var touchArea: FrameLayout
        var progressToolLayout: LinearLayout
        var videoProgress: RoundedProgressBar
        var duration: TextView
        var playPauseLayout: LinearLayout
        var loader: LottieAnimationView
        var thumbnail: SimpleDraweeView
        lateinit var postVideoConfig: Config
        var postVideoUrl = ""
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
        var isPostVideoLooping = false
        private val playerProgress = 0

      init {
            this.postVideoConfig = Config.Builder().cache(ExoPlayerUtils.getCache(itemView.context)).build()
            player = itemView.findViewById<PlayerView>(R.id.player_view)
            playPauseView =
                itemView.findViewById<LottieAnimationView>(R.id.attachment_video_play_pause_view)
            playPauseLayout = itemView.findViewById<LinearLayout>(R.id.attachment_play_pause_layout)
            loader = itemView.findViewById<LottieAnimationView>(R.id.attachment_video_loader_view)
            thumbnail = itemView.findViewById<SimpleDraweeView>(R.id.attachment_video_thumbnail)
            touchArea =
                itemView.findViewById<FrameLayout>(R.id.forum_post_item_attachments_video_frame)
            transparentOverlay =
                itemView.findViewById<RelativeLayout>(R.id.video_attachments_transparent_overlay)
            videoProgress =
                itemView.findViewById<RoundedProgressBar>(R.id.video_attachments_progressbar)
            progressToolLayout =
                itemView.findViewById<LinearLayout>(R.id.video_attachments_progress_layout)
            duration = itemView.findViewById<TextView>(R.id.video_attachments_playing_duration)
            controller = itemView.findViewById<LinearLayout>(R.id.video_attachments_controller)
            playPauseView.visibility = View.GONE
            playPauseView.setMinAndMaxFrame(0, 40)
            playPauseView.resumeAnimation()

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
            mPlayerView.setOnTouchListener(object : OnSwipeTouchListener(itemView.context) {
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
                        progressToolLayout.visibility = View.VISIBLE
                        playPauseView.visibility = View.VISIBLE
                        playPauseLayout.visibility = View.VISIBLE
                        scheduleVideoProgressToolDisappearance()
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
            this.postVideoUrl = url
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
            return postVideoUrl
        }

        override fun getConfig(): Config {
            return postVideoConfig
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
            isPostVideoLooping = true
            if (isPaused) {
                playPauseView.setMaxFrame(40)
                isPaused = false
            } else {
                progressToolLayout.visibility = View.GONE
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
            progressToolLayout.visibility = View.VISIBLE
        }

        private fun onReadyState() {
            if (isStarted && !isPaused) {
                isStarted = false
                scheduleVideoProgressToolDisappearance()
            }
            Log.e("ready", "onReadyState: ")
            isReady = true
            inReadyState = isReady
            loader.visibility = View.GONE
            thumbnail.visibility = View.GONE
            transparentOverlay.visibility = View.GONE
            progressToolLayout.visibility = View.GONE
            playPauseView.setMaxFrame(40)
            val mDuration = getDuration().toInt()

            //videoProgress.setMax((int) getDuration());
            (itemView.context as Activity).runOnUiThread(object : Runnable {
                override fun run() {
                    if (mPlayerView != null) {
                        val mCurrentPosition = TimeUnit.MILLISECONDS.toSeconds(
                            playbackPosition
                        ).toInt() % 60
                        val formattedSecPostion = TimeUnit.MILLISECONDS.toSeconds(
                            playbackPosition
                        ).toInt() % 60
                        val unformattedSecPostion = TimeUnit.MILLISECONDS.toSeconds(
                            playbackPosition
                        ).toInt()
                        val formattedMinutePostion = TimeUnit.MILLISECONDS.toMinutes(
                            playbackPosition
                        ).toInt() % 60
                        val unformattedMinutePostion = TimeUnit.MILLISECONDS.toMinutes(
                            playbackPosition
                        ).toInt()
                        var secs =
                            if (formattedSecPostion >= 0) formattedSecPostion else unformattedSecPostion
                        var minutes =
                            if (formattedMinutePostion >= 0) formattedMinutePostion else unformattedMinutePostion
                        val hours = TimeUnit.MILLISECONDS.toHours(
                            playbackPosition
                        ).toInt()
                        val totalSeconds = TimeUnit.MILLISECONDS.toSeconds(getDuration()).toInt()
                        val percentProgress =
                            java.lang.Double.valueOf((mCurrentPosition.toFloat() / totalSeconds * 100).toDouble())
                        videoProgress.setProgressPercentage(percentProgress, true)
                        if (hours <= 0) {
                            if (minutes < 10 && secs < 10) {
                                duration.text = "$minutes:0$secs"
                            } else if (minutes < 10 && secs >= 10) {
                                duration.text = "$minutes:$secs"
                            } else if (minutes >= 10 && secs < 10) {
                                duration.text = "$minutes:0$secs"
                            } else {
                                duration.text = "$minutes:$secs"
                            }
                        } else {
                            if (secs == 60) secs = 0
                            if (minutes == 60) minutes = 0
                            if (minutes < 10 && secs < 10) {
                                duration.text = "$hours:0$minutes:0$secs"
                            } else if (minutes < 10 && secs >= 10) {
                                duration.text = "$hours:0$minutes:$secs"
                            } else if (minutes >= 10 && secs < 10) {
                                duration.text = "$hours:$minutes:0$secs"
                            } else {
                                duration.text = "$hours:$minutes:$secs"
                            }
                        }
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
            progressToolLayout.visibility = View.VISIBLE
            playPauseView.visibility = View.VISIBLE
            playPauseLayout.visibility = View.VISIBLE
            loader.visibility = View.GONE
            thumbnail.visibility = View.GONE
            isPaused = true
            isPostVideoLooping = false
            playPauseView.setMinAndMaxFrame(40, 140)
            playPauseView.resumeAnimation()
        }

        fun onStoppedState() {
            isPausedByUser = false
            Log.e("stopped", "onStoppedState: ")
            isReady = false
            inReadyState = isReady
            isPostVideoLooping = false
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
            progressToolLayout.visibility = View.GONE
        }

        private fun onErrorState() {
            isPausedByUser = false
            Log.e("started", "onErrorState: ")
            isReady = false
            inReadyState = isReady
            isPostVideoLooping = false
            if (alphaAnim2 != null && !alphaAnim2!!.hasEnded()) {
                isCancelled = true
                alphaAnim2!!.cancel()
            }
            playPauseView.setMinAndMaxFrame(40, 140)
            playPauseView.resumeAnimation()
            loader.visibility = View.GONE
            playPauseView.visibility = View.VISIBLE
            transparentOverlay.visibility = View.VISIBLE
            progressToolLayout.visibility = View.GONE
            thumbnail.visibility = View.VISIBLE
        }

        fun scheduleVideoProgressToolDisappearance() {
            alphaAnim2 = AlphaAnimation(1.0f, 0.0f)
            alphaAnim2!!.startOffset = 4000
            alphaAnim2!!.duration = 400
            alphaAnim2!!.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    if (isCancelled) {
                        transparentOverlay.visibility = View.VISIBLE
                        playPauseView.visibility = View.VISIBLE
                        playPauseLayout.visibility = View.VISIBLE
                        isCancelled = false
                    } else {
                        playPauseView.visibility = View.GONE
                        playPauseLayout.visibility = View.GONE
                        transparentOverlay.visibility = View.GONE
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            transparentOverlay.animation = alphaAnim2
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (attachmentList[position].attachmentType == ATTACHMENT_TYPE_IMAGE) {
            ATTACHMENT_TYPE_IMAGE
        } else  if (attachmentList[position].attachmentType == ATTACHMENT_TYPE_VIDEO) {
            ATTACHMENT_TYPE_VIDEO
        } else if (attachmentList[position].attachmentType == ATTACHMENT_TYPE_LINK) {
            ATTACHMENT_TYPE_LINK
        } else{
            ATTACHMENT_TYPE_IMAGE
        }
    }



}