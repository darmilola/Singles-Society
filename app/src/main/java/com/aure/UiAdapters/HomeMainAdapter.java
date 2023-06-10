package com.aure.UiAdapters;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
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
import com.aure.Arvi.util.misc.ExoPlayerUtils;
import com.aure.Arvi.widget.PlayableItemViewHolder;
import com.aure.Arvi.widget.PlaybackState;
import com.aure.OnSwipeTouchListener;
import com.aure.R;
import com.aure.UiModels.LinePagerIndicator;
import com.aure.UiModels.ShowCaseMainModel;
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


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class HomeMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<ShowCaseMainModel> showCaseMainModelArrayList;
    private ShowCaseAdapter showCaseAdapter;

    private static int TYPE_MAIN = 0;
    private static int TYPE_POST = 1;

    private static int TYPE_IMAGE = 2;

    private Function0<Unit> visitProfileListener;


    public HomeMainAdapter(Context context, ArrayList<ShowCaseMainModel> showCaseMainModelArrayList){
        this.context = context;
        this.showCaseMainModelArrayList = showCaseMainModelArrayList;
    }

    public void setVisitProfileListener(@NotNull Function0<Unit> visitProfileListener) {
        this.visitProfileListener = visitProfileListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == TYPE_MAIN){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showcase_layout, parent, false);
            return new ShowcaseMainGeneralItemViewHolder(view);
        }

        else if(viewType == TYPE_POST){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_post, parent, false);
            return new CommunityPostItemViewHolder(parent, view);
        }
        else if(viewType == TYPE_IMAGE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_post_type_image, parent, false);
            return new ShowcaseImageItemViewHolder(view);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(showCaseMainModelArrayList.get(position).getItemViewType() == TYPE_MAIN){
            ShowcaseMainGeneralItemViewHolder holder1 = (ShowcaseMainGeneralItemViewHolder)holder;
            showCaseAdapter = new ShowCaseAdapter(context,showCaseMainModelArrayList.get(position).getShowCaseModelArrayList());
            LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            holder1.showcaseRecyclerview.setLayoutManager(layoutManager);
            holder1.showcaseRecyclerview.setAdapter(showCaseAdapter);
        }
       else if(showCaseMainModelArrayList.get(position).getItemViewType() == TYPE_POST){
               CommunityPostItemViewHolder communityPostItemViewHolder = (CommunityPostItemViewHolder) holder;

            communityPostItemViewHolder.setUrl("https://joy1.videvo.net/videvo_files/video/free/2016-12/large_watermarked/Code_flythough_loop_01_Videvo_preview.mp4");
            ImageRequest request = ImageRequest.fromUri("https://images.pexels.com/photos/3825527/pexels-photo-3825527.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2");
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController((communityPostItemViewHolder).thumbnail.getController()).build();
            (communityPostItemViewHolder).thumbnail.setController(controller);
            communityPostItemViewHolder.onStoppedState();
       }
       else if(showCaseMainModelArrayList.get(position).getItemViewType() == TYPE_IMAGE){
             ShowcaseImageItemViewHolder showcaseImageItemViewHolder = (ShowcaseImageItemViewHolder) holder;
            ShowcaseImageSliderAdapter showcaseImageSliderAdapter = new ShowcaseImageSliderAdapter(context);
            ArrayList<SliderItem> imageSlides = new ArrayList<>();
            for(int i = 0; i < 5; i++){
                showcaseImageSliderAdapter.addItem(new SliderItem("https://images.pexels.com/photos/3825527/pexels-photo-3825527.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"));
            }

            showcaseImageItemViewHolder.imageSlider.setSliderAdapter(showcaseImageSliderAdapter);
            showcaseImageItemViewHolder.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            showcaseImageItemViewHolder.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            showcaseImageItemViewHolder.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            showcaseImageItemViewHolder.imageSlider.setIndicatorSelectedColor(Color.parseColor("#fa2d65"));
            showcaseImageItemViewHolder.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
            showcaseImageItemViewHolder.imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
            showcaseImageItemViewHolder.imageSlider.startAutoCycle();


        }
    }



    @Override
    public int getItemCount() {
        return showCaseMainModelArrayList.size();
    }


    public class ShowcaseMainGeneralItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        RecyclerView showcaseRecyclerview;

        public ShowcaseMainGeneralItemViewHolder(View ItemView){
            super(ItemView);
            showcaseRecyclerview = ItemView.findViewById(R.id.showcase_recyclerview);
            showcaseRecyclerview.addItemDecoration(new LinePagerIndicator());
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

    public class ShowcaseImageItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        SliderView imageSlider;
        CircleImageView accountProfileImage;

        public ShowcaseImageItemViewHolder(View ItemView){
            super(ItemView);
            imageSlider = ItemView.findViewById(R.id.imageSlider);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageSlider.getPagerIndicator().getLayoutParams();
            imageSlider.getPagerIndicator().setElevation(50f);
            imageSlider.requestLayout();
            imageSlider.getPagerIndicator().requestLayout();
            accountProfileImage = ItemView.findViewById(R.id.accountProfilePicture);

            accountProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    visitProfileListener.invoke();
                }
            });



        }

        @Override
        public void onClick(View view) {

        }

    }

    @Override
    public int getItemViewType(int position) {
        if(showCaseMainModelArrayList.get(position).getItemViewType() == TYPE_MAIN){
            return TYPE_MAIN;
        }
        else if(showCaseMainModelArrayList.get(position).getItemViewType() == TYPE_POST){
            return TYPE_POST;
        }
        else if(showCaseMainModelArrayList.get(position).getItemViewType() == TYPE_IMAGE){
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
