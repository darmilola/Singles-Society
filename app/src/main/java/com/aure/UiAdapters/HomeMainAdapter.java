package com.aure.UiAdapters;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.aure.Arvi.Config;
import com.aure.Arvi.Direction;
import com.aure.Arvi.util.misc.ExoPlayerUtils;
import com.aure.Arvi.widget.CardStackLayoutManager;
import com.aure.Arvi.widget.CardStackListener;
import com.aure.Arvi.widget.PlayableCardStackView;
import com.aure.Arvi.widget.PlayableItemViewHolder;
import com.aure.Arvi.widget.PlaybackState;
import com.aure.Arvi.widget.SwipeableMethod;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.aure.OnSwipeTouchListener;
import com.aure.R;
import com.aure.UiModels.MainActivityModel;
import com.aure.UiModels.SocietyModel;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.google.android.exoplayer2.ui.PlayerView;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class HomeMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<SocietyModel> societyModelArrayList;
    private static int TYPE_MAIN = 0;
    private static int TYPE_POST = 1;

    private static int TYPE_IMAGE = 2;

    private Function0<Unit> visitProfileListener;
    private Function0<Unit> profileMatchedListener;

    private Function0<Unit> profileEmptyListener;
    private Function0<Unit> datingProfileListener;
    private Function0<Unit> postListener;
    private CardStackAdapter cardStackAdapter;


    public HomeMainAdapter(Context context, ArrayList<SocietyModel> societyModelArrayList){
        this.context = context;
        this.societyModelArrayList = societyModelArrayList;
    }

    public void setVisitProfileListener(@NotNull Function0<Unit> visitProfileListener) {
        this.visitProfileListener = visitProfileListener;
    }

    public void setDatingProfileListener(Function0<Unit> datingProfileListener) {
        this.datingProfileListener = datingProfileListener;
    }

    public void setProfileEmptyListener(Function0<Unit> profileEmptyListener) {
        this.profileEmptyListener = profileEmptyListener;
    }

    public void setPostListener(Function0<Unit> postListener) {
        this.postListener = postListener;
    }

    public void setProfileMatchedListener(Function0<Unit> profileMatchedListener) {
        this.profileMatchedListener = profileMatchedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == TYPE_MAIN){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showcase_stack_layout, parent, false);
            return new ShowcaseItemViewHolder(view);
        }

        else if(viewType == TYPE_POST){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_post, parent, false);
            return new CommunityPostItemViewHolder(parent, view);
        }
        else if(viewType == TYPE_IMAGE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_post_type_image, parent, false);
            return new ShowcaseImageItemViewHolder(view);
        }
        else {

            return null;
        }

    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if(holder instanceof ShowcaseItemViewHolder){
            datingProfileListener.invoke();
        }
        else{
            postListener.invoke();
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final String[] userProfileInPreview = {""};
        final int[] cardPosition = {0};
        if(societyModelArrayList.get(position).getItemViewType() == TYPE_MAIN){
            cardStackAdapter = new CardStackAdapter(societyModelArrayList.get(position).getUserProfileToPreview(),societyModelArrayList.get(position).getUsersLikedList(),context);
            cardStackAdapter.setVisibleUserListener(new CardStackAdapter.VisibleUserListener() {
                @Override
                public void onUserVisible(String userId) {
                    userProfileInPreview[0] = userId;
                }
            });

            ((ShowcaseItemViewHolder) holder).swipeRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                            .setDirection(com.yuyakaido.android.cardstackview.Direction.Right)
                            .setDuration(Duration.Slow.duration)
                            .setInterpolator(new AccelerateInterpolator())
                            .build();
                    ((ShowcaseItemViewHolder)holder).manager.setSwipeAnimationSetting(setting);
                    ((ShowcaseItemViewHolder)holder).showcaseCardStackRecyclerview.swipe();
                }
            });


            ((ShowcaseItemViewHolder) holder).swipeLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                            .setDirection(com.yuyakaido.android.cardstackview.Direction.Left)
                            .setDuration(Duration.Slow.duration)
                            .setInterpolator(new AccelerateInterpolator())
                            .build();
                    ((ShowcaseItemViewHolder)holder).manager.setSwipeAnimationSetting(setting);
                    ((ShowcaseItemViewHolder)holder).showcaseCardStackRecyclerview.swipe();
                }
            });

            ((ShowcaseItemViewHolder) holder).showcaseCardStackRecyclerview.setAdapter(cardStackAdapter);
            ((ShowcaseItemViewHolder) holder).manager.setListener(new CardStackListener() {
                @Override
                public void onCardDragging(Direction direction, float ratio) {
                    ((ShowcaseItemViewHolder) holder).swipeLayout.setVisibility(View.GONE);
                }
                @Override
                public void onCardSwiped(Direction direction) {
                       if(direction == Direction.Right){
                           MainActivityModel mainActivityModel = new MainActivityModel(userProfileInPreview[0],context);
                           mainActivityModel.setLiked();
                           profileMatchedListener.invoke();
                       }

                    if(cardPosition[0] == societyModelArrayList.get(position).getUserProfileToPreview().size()-1){
                        profileEmptyListener.invoke();
                    }

                }

                @Override
                public void onCardRewound() {

                }

                @Override
                public void onCardCanceled() {

                }

                @Override
                public void onCardAppeared(View view, int position) {

                }

                @Override
                public void onCardDisappeared(View view, int position) {
                    cardPosition[0] = position;

                }
            });
            ((ShowcaseItemViewHolder) holder).showcaseCardStackRecyclerview.requestLayout();

        }
       else if(societyModelArrayList.get(position).getItemViewType() == TYPE_POST){
               CommunityPostItemViewHolder communityPostItemViewHolder = (CommunityPostItemViewHolder) holder;
            postListener.invoke();
            communityPostItemViewHolder.setUrl("https://joy1.videvo.net/videvo_files/video/free/2016-12/large_watermarked/Code_flythough_loop_01_Videvo_preview.mp4");
            ImageRequest request = ImageRequest.fromUri("https://images.pexels.com/photos/3825527/pexels-photo-3825527.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2");
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController((communityPostItemViewHolder).thumbnail.getController()).build();
            (communityPostItemViewHolder).thumbnail.setController(controller);
            communityPostItemViewHolder.onStoppedState();
       }
       else if(societyModelArrayList.get(position).getItemViewType() == TYPE_IMAGE){
            postListener.invoke();
             ShowcaseImageItemViewHolder showcaseImageItemViewHolder = (ShowcaseImageItemViewHolder) holder;
            ShowcaseImageSliderAdapter showcaseImageSliderAdapter = new ShowcaseImageSliderAdapter(context);
            for(int i = 0; i < 5; i++){
                showcaseImageSliderAdapter.addItem(new SliderItem("https://images.pexels.com/photos/3825527/pexels-photo-3825527.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"));
            }
            showcaseImageItemViewHolder.imageSlider.setSliderAdapter(showcaseImageSliderAdapter);
            showcaseImageItemViewHolder.scheduleEngagementViewDisappearance();
            showcaseImageItemViewHolder.imageSlider.startAutoCycle();


        }
    }



    @Override
    public int getItemCount() {
        return societyModelArrayList.size();
    }


    public class ShowcaseItemViewHolder extends RecyclerView.ViewHolder{


        PlayableCardStackView showcaseCardStackRecyclerview;
        CardStackLayoutManager manager;

        LinearLayout swipeLayout, swipeLeft, rewound, swipeRight;



        public ShowcaseItemViewHolder(View ItemView){
            super(ItemView);
            showcaseCardStackRecyclerview = ItemView.findViewById(R.id.showcaseCardStackView);
            swipeLayout = ItemView.findViewById(R.id.showcase_swipe_layout);
            swipeLeft = ItemView.findViewById(R.id.user_swipe_left);
            rewound = ItemView.findViewById(R.id.user_rewind);
            swipeRight = ItemView.findViewById(R.id.user_swipe_right);
            manager = new CardStackLayoutManager(context);
            manager.setVisibleCount(2);
            manager.setScaleInterval(1.0f);
            manager.setStackFrom(StackFrom.Top);
            manager.setSwipeThreshold(0.2f);
            manager.setMaxDegree(80.0f);
            manager.setDirections(Direction.HORIZONTAL);
            manager.setCanScrollHorizontal(true);
            manager.setCanScrollVertical(false);
            manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
            showcaseCardStackRecyclerview.setLayoutManager(manager);
        }


    }

    public class ShowcaseImageItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnDragListener {

        SliderView imageSlider;
        CircleImageView accountProfileImage;
        AlphaAnimation alphaAnim2;
        FrameLayout engagementOverlay;
        boolean isCancelled = false;
        CardView imagePostCard;
        ConstraintLayout navigationRoot;
        ImageView arrowRight, arrowLeft;

        public ShowcaseImageItemViewHolder(View ItemView){
            super(ItemView);
            imageSlider = ItemView.findViewById(R.id.imageSlider);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageSlider.getPagerIndicator().getLayoutParams();
            imageSlider.getPagerIndicator().setElevation(50f);
            imageSlider.requestLayout();
            imageSlider.getPagerIndicator().requestLayout();
            imageSlider.setAutoCycle(true);
            imageSlider.setIndicatorAnimation(IndicatorAnimationType.FILL);
            imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            imageSlider.setIndicatorSelectedColor(Color.parseColor("#fa2d65"));
            imageSlider.setIndicatorUnselectedColor(Color.GRAY);
            imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            accountProfileImage = ItemView.findViewById(R.id.accountProfilePicture);
            engagementOverlay = ItemView.findViewById(R.id.postEngagementOverlay);
            imagePostCard = ItemView.findViewById(R.id.imagePostCard);
            navigationRoot = ItemView.findViewById(R.id.navigationArrowRoot);
            arrowRight = ItemView.findViewById(R.id.navigationArrowRight);
            arrowLeft = ItemView.findViewById(R.id.navigationArrowLeft);
            imagePostCard.requestFocusFromTouch();
            imagePostCard.requestFocus();

            imageSlider.setScrollTimeInSec(7);
            navigationRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    engagementOverlay.setVisibility(View.VISIBLE);
                    scheduleEngagementViewDisappearance();
                }
            });

            arrowLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageSlider.stopAutoCycle();
                    imageSlider.slideToNextPosition();
                }
            });

            arrowRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageSlider.stopAutoCycle();
                    imageSlider.slideToPreviousPosition();
                }
            });

            accountProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    visitProfileListener.invoke();
                }
            });






        }

        @Override
        public void onClick(View view) {
           engagementOverlay.setVisibility(View.VISIBLE);
           scheduleEngagementViewDisappearance();
        }

        public void scheduleEngagementViewDisappearance() {

            alphaAnim2 = new AlphaAnimation(1.0f, 0.0f);
            alphaAnim2.setStartOffset(3000);
            alphaAnim2.setDuration(2000);
            alphaAnim2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                public void onAnimationEnd(Animation animation) {


                    if (isCancelled) {
                        engagementOverlay.setVisibility(View.VISIBLE);
                        isCancelled = false;
                    } else {
                        engagementOverlay.setVisibility(View.GONE);
                        navigationRoot.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            engagementOverlay.setAnimation(alphaAnim2);

        }


        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            return false;
        }
    }



    @Override
    public int getItemViewType(int position) {
        if(societyModelArrayList.get(position).getItemViewType() == TYPE_MAIN){
            return TYPE_MAIN;
        }
        else if(societyModelArrayList.get(position).getItemViewType() == TYPE_POST){
            return TYPE_POST;
        }
        else if(societyModelArrayList.get(position).getItemViewType() == TYPE_IMAGE){
            return TYPE_IMAGE;
        }
        else{
            return TYPE_POST;
        }


    }


    public class SliderItem {

        private String description;
        private String imageUrl;

        public String getDescription() {
            return description;
        }

        SliderItem(String imageUrl){
            this.imageUrl = imageUrl;
        }
        public void setDescription(String description) {
            this.description = description;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }



    public class ShowcaseImageSliderAdapter extends
            SliderViewAdapter<ShowcaseImageSliderAdapter.SliderAdapterVH> {

        private Context context;
        private List<SliderItem> mSliderItems = new ArrayList<>();

        public ShowcaseImageSliderAdapter(Context context) {
            this.context = context;
        }

        public void renewItems(List<SliderItem> sliderItems) {
            this.mSliderItems = sliderItems;
            notifyDataSetChanged();
        }

        public void deleteItem(int position) {
            this.mSliderItems.remove(position);
            notifyDataSetChanged();
        }

        public void addItem(SliderItem sliderItem) {
            this.mSliderItems.add(sliderItem);
            notifyDataSetChanged();
        }

        @Override
        public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
            return new SliderAdapterVH(inflate);
        }

        @Override
        public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

            SliderItem sliderItem = mSliderItems.get(position);

            viewHolder.textViewDescription.setText(sliderItem.getDescription());
            viewHolder.textViewDescription.setTextSize(16);
            viewHolder.textViewDescription.setTextColor(Color.WHITE);
            Glide.with(viewHolder.itemView)
                    .load(sliderItem.getImageUrl())
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        @Override
        public int getCount() {
            //slider view count could be dynamic size
            return mSliderItems.size();
        }


        class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

            View itemView;
            ImageView imageViewBackground;
            ImageView imageGifContainer;
            TextView textViewDescription;

            public SliderAdapterVH(View itemView) {
                super(itemView);
                imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
                imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
                textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
                this.itemView = itemView;
            }
        }

    }


        public class CommunityPostItemViewHolder extends PlayableItemViewHolder implements View.OnClickListener {

            RelativeLayout transparentOverlay;
            FrameLayout touchArea;
            LinearLayout progressToolLayout;
            RoundedProgressBar videoProgress;
            LinearLayout playPauseLayout;
            LottieAnimationView loader;
            SimpleDraweeView thumbnail;
            Config config;
            String url = "";
            PlayerView player;
            LinearLayout controller;
            PlaybackState playbackState;
            AlphaAnimation alphaAnim2;
            LottieAnimationView playPauseView;
            String playbackCacheID = "";
            private Handler mHandler = new Handler();
            boolean isCancelled = false;
            boolean isPaused = false;
            boolean isReady = false;
            boolean isStarted = false;
            boolean isLooping = false;
            private int playerProgress = 0;
            private CircleImageView accountProfileImage;

            public CommunityPostItemViewHolder(ViewGroup parentViewGroup, View itemView) {
                super(parentViewGroup, itemView);
                this.config = new Config.Builder().cache(ExoPlayerUtils.getCache(context)).build();
                player = itemView.findViewById(R.id.player_view);
                playPauseView = itemView.findViewById(R.id.video_play_pause_view);
                playPauseLayout = itemView.findViewById(R.id.video_play_pause_layout);
                loader = itemView.findViewById(R.id.video_loader_view);
                thumbnail = itemView.findViewById(R.id.video_thumbnail);
                touchArea = itemView.findViewById(R.id.video_frame);
                transparentOverlay = itemView.findViewById(R.id.video_transparent_overlay);
                videoProgress = itemView.findViewById(R.id.video_progressbar);
                progressToolLayout = itemView.findViewById(R.id.video_progress_layout);
                controller = itemView.findViewById(R.id.video_controller);
                playPauseView.setVisibility(View.GONE);
                playPauseView.setMinAndMaxFrame(0, 40);
                playPauseView.resumeAnimation();
                accountProfileImage = itemView.findViewById(R.id.accountProfilePicture);



                accountProfileImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        visitProfileListener.invoke();
                    }
                });

                setOnGestureListeners();
                playPauseView.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        playPauseView.pauseAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });


                playPauseView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNewPlayer) {
                            playbackCacheID = generatePlaybackCacheID();
                        }
                        setPlayBackCacheID(playbackCacheID);

                        if (isReady && isPlaying() && isTrulyPlayable()) {
                            if (alphaAnim2 != null && !alphaAnim2.hasEnded()) {
                                isCancelled = true;
                                alphaAnim2.cancel();
                            }
                            isPaused = true;
                            setPausedByUser(true);
                            pause();
                        } else {
                            if (alphaAnim2 != null && !alphaAnim2.hasEnded()) {
                                isCancelled = true;
                                alphaAnim2.cancel();
                            }
                            start();
                        }
                    }
                });
            }


            @SuppressLint("ClickableViewAccessibility")
            private void setOnGestureListeners() {
                mPlayerView.setOnTouchListener(new OnSwipeTouchListener(context) {
                    @Override
                    public void onSwipeRight() {
                        super.onSwipeRight();
                        Log.e("right", "onSwipeRight: ");

                    }

                    @Override
                    public void onSwipeLeft() {
                        super.onSwipeLeft();
                        Log.e("left", "onSwipeLeft: ");

                    }

                    @Override
                    public void onClick() {
                        super.onClick();
                        if (isReady && isPlaying() && isTrulyPlayable()) {

                            if (alphaAnim2 != null && !alphaAnim2.hasEnded()) {
                                isCancelled = true;
                                alphaAnim2.cancel();
                            }
                            transparentOverlay.setVisibility(View.VISIBLE);
                            // progressToolLayout.setVisibility(View.VISIBLE);
                            playPauseView.setVisibility(View.VISIBLE);
                            //  playPauseLayout.setVisibility(View.VISIBLE);
                            scheduleVideoProgressToolDisappearance();

                        }


                    }

                    @Override
                    public void onDoubleClick() {
                        super.onDoubleClick();
                        if (isReady && isPlaying() && isTrulyPlayable()) {
                            if (alphaAnim2 != null && !alphaAnim2.hasEnded()) {
                                isCancelled = true;
                                alphaAnim2.cancel();
                            }
                            isPaused = true;
                            setPausedByUser(true);
                            pause();
                        }

                    }
                });
            }

            @Override
            protected float getTriggerOffset() {
                return 0.999f;
            }


            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public boolean isLooping() {
                return true;
            }


            /**
             * Retrieves the media Url associated with this item.
             *
             * @return the media url
             */
            @NonNull
            @Override
            public String getUrl() {
                return url;
            }

            @NotNull
            @Override
            public Config getConfig() {
                return config;
            }

            @Override
            public long getDuration() {
                return super.getDuration();
            }

            String generatePlaybackCacheID() {
                String SALTCHARS = "ABCDEFGHIJLMNOPQRSTUVWXYZ123456890";
                StringBuilder salt = new StringBuilder();
                Random random = new Random();
                while (salt.length() < 18) {
                    int index = (int) (random.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }
                String saltr = salt.toString();
                return saltr;
            }

            @Override
            public void onStateChanged(@NonNull PlaybackState playbackState) {
                super.onStateChanged(playbackState);
                this.playbackState = playbackState;
                if (playbackState == PlaybackState.BUFFERING) onBufferingState();
                if (playbackState == PlaybackState.ERROR) onErrorState();
                if (playbackState == PlaybackState.PAUSED) onPausedState();
                if (playbackState == PlaybackState.READY) onReadyState();
                if (playbackState == PlaybackState.STOPPED) onStoppedState();
                if (playbackState == PlaybackState.STARTED) onStartedState();


            }

            private void onStartedState() {
                Log.e("started", "onStartedState: ");
                loader.setVisibility(View.VISIBLE);
                transparentOverlay.setVisibility(View.VISIBLE);
                isStarted = true;
                isReady = false;
                setInReadyState(false);
                isLooping = true;
                if (isPaused) {

                    playPauseView.setMaxFrame(40);
                    isPaused = false;


                } else {


                    //    progressToolLayout.setVisibility(View.VISIBLE);
                    thumbnail.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.VISIBLE);
                    playPauseView.setVisibility(View.GONE);
                    playPauseLayout.setVisibility(View.GONE);

                }

            }

            private void onBufferingState() {
                Log.e("buffering", "onBufferingState: ");
                isReady = true;
                loader.setVisibility(View.VISIBLE);
                thumbnail.setVisibility(View.GONE);
                transparentOverlay.setVisibility(View.VISIBLE);
                //   progressToolLayout.setVisibility(View.VISIBLE);

            }


            private void onReadyState() {
                if (isStarted && !isPaused) {
                    isStarted = false;
                    scheduleVideoProgressToolDisappearance();
                }
                Log.e("ready", "onReadyState: ");
                isReady = true;
                setInReadyState(isReady);
                loader.setVisibility(View.GONE);
                thumbnail.setVisibility(View.GONE);
                transparentOverlay.setVisibility(View.GONE);
                // progressToolLayout.setVisibility(View.VISIBLE);
                playPauseView.setMaxFrame(40);

                int mDuration = (int) getDuration();

                //videoProgress.setMax((int) getDuration());
                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        if (mPlayerView != null) {
                            int mCurrentPosition = (int) TimeUnit.MILLISECONDS.toSeconds(getPlaybackPosition()) % 60;

                            int totalSeconds = (int) TimeUnit.MILLISECONDS.toSeconds(getDuration());

                            Double percentProgress = Double.valueOf((float) mCurrentPosition / totalSeconds * 100);

                            videoProgress.setProgressPercentage(percentProgress, true);

                        }
                        mHandler.postDelayed(this, 1000);
                    }
                });

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


            private void onPausedState() {

                Log.e("pause", "onPausedState: ");
                if (alphaAnim2 != null && !alphaAnim2.hasEnded()) {
                    isCancelled = true;
                    alphaAnim2.cancel();
                }
                transparentOverlay.setVisibility(View.VISIBLE);
                // progressToolLayout.setVisibility(View.VISIBLE);
                playPauseView.setVisibility(View.VISIBLE);
                playPauseLayout.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
                thumbnail.setVisibility(View.GONE);
                isPaused = true;
                isLooping = false;
                playPauseView.setMinAndMaxFrame(40, 140);
                playPauseView.resumeAnimation();


            }


            private void onStoppedState() {

                setPausedByUser(false);
                Log.e("stopped", "onStoppedState: ");
                isReady = false;
                setInReadyState(isReady);
                isLooping = false;
                if (alphaAnim2 != null && !alphaAnim2.hasEnded()) {
                    isCancelled = true;
                    alphaAnim2.cancel();
                }
                playPauseView.setMinAndMaxFrame(40, 140);
                playPauseView.resumeAnimation();
                playPauseView.setVisibility(View.VISIBLE);
                playPauseLayout.setVisibility(View.VISIBLE);
                thumbnail.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
                transparentOverlay.setVisibility(View.VISIBLE);
                // progressToolLayout.setVisibility(View.GONE);

            }


            private void onErrorState() {

                setPausedByUser(false);
                Log.e("started", "onErrorState: ");
                isReady = false;
                setInReadyState(isReady);
                isLooping = false;
                if (alphaAnim2 != null && !alphaAnim2.hasEnded()) {
                    isCancelled = true;
                    alphaAnim2.cancel();
                }
                playPauseView.setMinAndMaxFrame(40, 140);
                playPauseView.resumeAnimation();
                loader.setVisibility(View.GONE);
                playPauseView.setVisibility(View.VISIBLE);
                transparentOverlay.setVisibility(View.VISIBLE);
                //  progressToolLayout.setVisibility(View.GONE);
                thumbnail.setVisibility(View.VISIBLE);

            }

            public void scheduleVideoProgressToolDisappearance() {

                alphaAnim2 = new AlphaAnimation(1.0f, 0.0f);
                alphaAnim2.setStartOffset(5000);
                alphaAnim2.setDuration(3000);
                alphaAnim2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    public void onAnimationEnd(Animation animation) {


                        if (isCancelled) {
                            transparentOverlay.setVisibility(View.VISIBLE);
                            playPauseView.setVisibility(View.VISIBLE);
                            playPauseLayout.setVisibility(View.VISIBLE);
                            //  progressToolLayout.setVisibility(View.VISIBLE);
                            isCancelled = false;
                        } else {
                            playPauseView.setVisibility(View.GONE);
                            playPauseLayout.setVisibility(View.GONE);
                            transparentOverlay.setVisibility(View.GONE);
                            //   progressToolLayout.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                transparentOverlay.setAnimation(alphaAnim2);

            }

            @Override
            public void onClick(View view) {

            }
        }

}
