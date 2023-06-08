package com.aure.UiAdapters;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.aure.Arvi.Config;
import com.aure.Arvi.util.misc.ExoPlayerUtils;
import com.aure.Arvi.widget.PlayableItemViewHolder;
import com.aure.Arvi.widget.PlaybackState;
import com.aure.OnSwipeTouchListener;
import com.aure.R;
import com.aure.UiModels.ShowCaseModel;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.chip.Chip;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ShowCaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int SHOWCASE_TYPE_MAIN = 1;
    private static int SHOWCASE_TYPE_QUOTE = 2;
    private static int SHOWCASE_TYPE_ABOUT_ME = 3;
    private static int SHOWCASE_TYPE_CAREER = 4;
    private static int SHOWCASE_TYPE_PICTURE = 6;
    private static int SHOWCASE_TYPE_TAKE_ACTION = 8;

    private static int SHOWCASE_TYPE_VIDEO = 9;
    Context context;

    private Boolean uiNeedsAdjustment = false;
    ArrayList<ShowCaseModel> showcaseList = new ArrayList<>();

    public ShowCaseAdapter(Context context, ArrayList<ShowCaseModel> showCaseList){
        this.context = context;
        this.showcaseList = showCaseList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SHOWCASE_TYPE_MAIN) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_main, parent, false);
            return new ShowcaseMainItemViewHolder(view);
        }
        if (viewType == SHOWCASE_TYPE_QUOTE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_quote, parent, false);
            return new ShowcaseQuoteItemViewHolder(view);
        }
        if (viewType == SHOWCASE_TYPE_CAREER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_career, parent, false);
            return new ShowcaseCareerItemViewHolder(view);
        }
        if (viewType == SHOWCASE_TYPE_PICTURE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_picture, parent, false);
            return new ShowcasePictureItemViewHolder(view);
        }
        if (viewType == SHOWCASE_TYPE_ABOUT_ME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_about_me, parent, false);
            return new ShowcaseAboutMeItemViewHolder(view);
        }


        if (viewType == SHOWCASE_TYPE_TAKE_ACTION) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_fav_report_block, parent, false);
            return new ShowcaseTakeActionItemViewHolder(view);
        }


        if (viewType == SHOWCASE_TYPE_VIDEO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_showcase_type_video, parent, false);
            return new ShowcaseVideoItemViewHolder(parent, view);
        }

        return null;
    }

    public void setUiNeedsAdjustment(Boolean uiNeedsAdjustment) {
        this.uiNeedsAdjustment = uiNeedsAdjustment;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ShowCaseModel showCaseModel = showcaseList.get(position);
        if(showCaseModel.getShowcaseType() == 1){
            ShowcaseMainItemViewHolder showcaseMainItemViewHolder = (ShowcaseMainItemViewHolder) holder;
            //showcaseMainItemViewHolder.nameAge.setText(showCaseModel.getModelInfoList().get(0)+", "+showCaseModel.getModelInfoList().get(1));
            showcaseMainItemViewHolder.city.setText(showCaseModel.getModelInfoList().get(2));
            showcaseMainItemViewHolder.occupation.setText(showCaseModel.getModelInfoList().get(3));
            for(String id: showCaseModel.getLikeList()){
                if(id.equalsIgnoreCase(showCaseModel.getModelInfoList().get(5))){
                    showcaseMainItemViewHolder.potentialMatch.setVisibility(View.VISIBLE);
                }
            }
            Glide.with(context)
                    .load("https://images.pexels.com/photos/3825527/pexels-photo-3825527.jpeg?auto=compress&cs=tinysrgb&w=260&h=650&dpr=2")
                    .placeholder(R.drawable.profileplaceholder)
                    .error(R.drawable.profileplaceholder)
                    .into(showcaseMainItemViewHolder.imageView);

        }

        if(showCaseModel.getShowcaseType() == 3){
            ShowcaseAboutMeItemViewHolder showcaseAboutMeItemViewHolder = (ShowcaseAboutMeItemViewHolder) holder;
            showcaseAboutMeItemViewHolder.status.setText(showCaseModel.getModelInfoList().get(0));
            showcaseAboutMeItemViewHolder.smoking.setText(showCaseModel.getModelInfoList().get(1));
            showcaseAboutMeItemViewHolder.drinking.setText(showCaseModel.getModelInfoList().get(2));
            showcaseAboutMeItemViewHolder.language.setText(showCaseModel.getModelInfoList().get(3));
            showcaseAboutMeItemViewHolder.religion.setText(showCaseModel.getModelInfoList().get(4));
            showcaseAboutMeItemViewHolder.marriageGoals.setText(showCaseModel.getModelInfoList().get(5));

        }

        if(showCaseModel.getShowcaseType() == 4){
            ShowcaseCareerItemViewHolder showcaseCareerItemViewHolder  = (ShowcaseCareerItemViewHolder) holder;
            showcaseCareerItemViewHolder.degree.setText(showCaseModel.getModelInfoList().get(0));
            showcaseCareerItemViewHolder.occupation.setText(showCaseModel.getModelInfoList().get(1));
            showcaseCareerItemViewHolder.workplace.setText(showCaseModel.getModelInfoList().get(2));
            Glide.with(context)
                    .load(showCaseModel.getModelInfoList().get(3))
                    .placeholder(R.drawable.profileplaceholder)
                    .error(R.drawable.profileplaceholder)
                    .into(showcaseCareerItemViewHolder.imageView);
        }

        if(showCaseModel.getShowcaseType() == 6){
            ShowcasePictureItemViewHolder showcasePictureItemViewHolder = (ShowcasePictureItemViewHolder) holder;
            Glide.with(context)
                    .load(showCaseModel.getModelInfoList().get(0))
                    .placeholder(R.drawable.profileplaceholder)
                    .error(R.drawable.profileplaceholder)
                    .into(showcasePictureItemViewHolder.imageView);
        }



        if(showCaseModel.getShowcaseType() == 9){
            ShowcaseVideoItemViewHolder vi = (ShowcaseVideoItemViewHolder) holder;

            vi.setUrl("https://joy1.videvo.net/videvo_files/video/free/2016-12/large_watermarked/Code_flythough_loop_01_Videvo_preview.mp4");
            ImageRequest request = ImageRequest.fromUri("https://images.pexels.com/photos/3825527/pexels-photo-3825527.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2");
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController((vi).thumbnail.getController()).build();
            (vi).thumbnail.setController(controller);
            vi.onStoppedState();

        }
    }

    @Override
    public int getItemCount() {
        return showcaseList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_MAIN){
            return SHOWCASE_TYPE_MAIN;
        }
       else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_CAREER){
            return SHOWCASE_TYPE_CAREER;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_QUOTE){
            return SHOWCASE_TYPE_QUOTE;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_PICTURE){
            return SHOWCASE_TYPE_PICTURE;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_ABOUT_ME){
            return SHOWCASE_TYPE_ABOUT_ME;
        }
        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_TAKE_ACTION){
            return SHOWCASE_TYPE_TAKE_ACTION;
        }

        else if(showcaseList.get(position).getShowcaseType() == SHOWCASE_TYPE_VIDEO){
            return SHOWCASE_TYPE_VIDEO;
        }
        else{
            return SHOWCASE_TYPE_MAIN;
        }
    }


    public class ShowcaseMainItemViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView city,nameAge;
        Chip occupation;
        ConstraintLayout potentialMatch;
        LinearLayout viewRoot;
        public ShowcaseMainItemViewHolder(View ItemView){
            super(ItemView);
            imageView = ItemView.findViewById(R.id.type_main_image);
            city = ItemView.findViewById(R.id.type_main_city);
            nameAge = ItemView.findViewById(R.id.type_main_name_age);
            occupation = ItemView.findViewById(R.id.type_main_occupation);
            potentialMatch = ItemView.findViewById(R.id.potential_match);
            viewRoot = ItemView.findViewById(R.id.mainViewRoot);
            if(uiNeedsAdjustment) {
                final int heightDp = context.getResources().getSystem().getDisplayMetrics().heightPixels - convertDpToPixel(70, context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightDp);
                viewRoot.setLayoutParams(layoutParams);
            }

        }
    }

    public static int convertPixelsToDp(float px, Context context){
        return (int) (px / ((int) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int convertDpToPixel(float dp, Context context){
        return (int) (dp * ((int) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }






    public class ShowcaseTakeActionItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        public ShowcaseTakeActionItemViewHolder(View ItemView){
            super(ItemView);
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

    public class ShowcaseMarriageGoalsItemViewHolder extends RecyclerView.ViewHolder{


        Chip goal;
        public ShowcaseMarriageGoalsItemViewHolder(View ItemView){
            super(ItemView);
            goal = ItemView.findViewById(R.id.type_goals_year);
        }


    }



    public class ShowcaseAboutMeItemViewHolder extends RecyclerView.ViewHolder{

        Chip status,smoking,drinking,language,religion,marriageGoals;

        public ShowcaseAboutMeItemViewHolder(View ItemView){
            super(ItemView);
            status = ItemView.findViewById(R.id.type_about_status);
            smoking = ItemView.findViewById(R.id.type_about_smoking);
            drinking = ItemView.findViewById(R.id.type_about_drinking);
            language = ItemView.findViewById(R.id.type_about_language);
            religion = ItemView.findViewById(R.id.type_about_religion);
            marriageGoals = ItemView.findViewById(R.id.marriage_goals);
        }

    }

    public class ShowcasePictureItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        LinearLayout viewRoot;

        public ShowcasePictureItemViewHolder(View ItemView) {
            super(ItemView);
            imageView = ItemView.findViewById(R.id.type_picture_image);
            viewRoot = ItemView.findViewById(R.id.mainViewRoot);
            if(uiNeedsAdjustment) {
                final int heightDp = context.getResources().getSystem().getDisplayMetrics().heightPixels - convertDpToPixel(70, context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightDp);
                viewRoot.setLayoutParams(layoutParams);
            }

        }

    }



    public class ShowcaseQuoteItemViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public ShowcaseQuoteItemViewHolder(View ItemView){
            super(ItemView);
            textView = ItemView.findViewById(R.id.type_quote_text);

        }



    }

    public class ShowcaseCareerItemViewHolder extends RecyclerView.ViewHolder {

        Chip degree, occupation, workplace;
        ImageView imageView;
        LinearLayout viewRoot;

        public ShowcaseCareerItemViewHolder(View ItemView) {
            super(ItemView);
            degree = ItemView.findViewById(R.id.type_career_education);
            occupation = ItemView.findViewById(R.id.type_career_occupation);
            workplace = ItemView.findViewById(R.id.type_career_workplace);
            imageView = ItemView.findViewById(R.id.type_career_image);
            viewRoot = ItemView.findViewById(R.id.mainViewRoot);
            if(uiNeedsAdjustment) {
                final int heightDp = context.getResources().getSystem().getDisplayMetrics().heightPixels - convertDpToPixel(70, context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightDp);
                viewRoot.setLayoutParams(layoutParams);
            }
        }

    }


        public class ShowcaseVideoItemViewHolder extends PlayableItemViewHolder {

            RelativeLayout transparentOverlay;
            FrameLayout touchArea;
            LinearLayout progressToolLayout;
            RoundedProgressBar videoProgress;
            TextView duration;
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
            LinearLayout viewRoot;
            private Handler mHandler = new Handler();
            boolean isCancelled = false;
            boolean isPaused = false;
            boolean isReady = false;
            boolean isStarted = false;
            boolean isLooping = false;
            private int playerProgress = 0;

            private ShowcaseVideoItemViewHolder(ViewGroup parentViewGroup, View itemView) {

                super(parentViewGroup, itemView);
                this.config = new Config.Builder().cache(ExoPlayerUtils.getCache(context)).build();
                player = itemView.findViewById(R.id.player_view);
                playPauseView = itemView.findViewById(R.id.attachment_video_play_pause_view);
                playPauseLayout = itemView.findViewById(R.id.attachment_play_pause_layout);
                loader = itemView.findViewById(R.id.attachment_video_loader_view);
                thumbnail = itemView.findViewById(R.id.attachment_video_thumbnail);
                touchArea = itemView.findViewById(R.id.forum_post_item_attachments_video_frame);
                transparentOverlay = itemView.findViewById(R.id.video_attachments_transparent_overlay);
                videoProgress = itemView.findViewById(R.id.video_attachments_progressbar);
                progressToolLayout = itemView.findViewById(R.id.video_attachments_progress_layout);
                duration = itemView.findViewById(R.id.video_attachments_playing_duration);
                controller = itemView.findViewById(R.id.video_attachments_controller);
                playPauseView.setVisibility(View.GONE);
                playPauseView.setMinAndMaxFrame(0,40);
                playPauseView.resumeAnimation();
                viewRoot = itemView.findViewById(R.id.mainViewRoot);
                if(uiNeedsAdjustment) {
                    final int heightDp = context.getResources().getSystem().getDisplayMetrics().heightPixels - convertDpToPixel(70, context);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightDp);
                    viewRoot.setLayoutParams(layoutParams);
                }


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
                            progressToolLayout.setVisibility(View.VISIBLE);
                            playPauseView.setVisibility(View.VISIBLE);
                            playPauseLayout.setVisibility(View.VISIBLE);
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


                    progressToolLayout.setVisibility(View.GONE);
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
                progressToolLayout.setVisibility(View.VISIBLE);

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
                progressToolLayout.setVisibility(View.GONE);
                playPauseView.setMaxFrame(40);

                int mDuration = (int) getDuration();

                //videoProgress.setMax((int) getDuration());
                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        if (mPlayerView != null) {
                            int mCurrentPosition = (int) TimeUnit.MILLISECONDS.toSeconds(getPlaybackPosition()) % 60;
                            int formattedSecPostion = (int) TimeUnit.MILLISECONDS.toSeconds(getPlaybackPosition()) % 60;
                            int unformattedSecPostion = (int) TimeUnit.MILLISECONDS.toSeconds(getPlaybackPosition());
                            int formattedMinutePostion = (int) TimeUnit.MILLISECONDS.toMinutes(getPlaybackPosition()) % 60;
                            int unformattedMinutePostion = (int) TimeUnit.MILLISECONDS.toMinutes(getPlaybackPosition());
                            int secs = formattedSecPostion >= 0 ? formattedSecPostion : unformattedSecPostion;
                            int minutes = formattedMinutePostion >= 0 ? formattedMinutePostion : unformattedMinutePostion;
                            int hours = (int) TimeUnit.MILLISECONDS.toHours(getPlaybackPosition());


                            int totalSeconds = (int) TimeUnit.MILLISECONDS.toSeconds(getDuration());

                            Double percentProgress = Double.valueOf((float)mCurrentPosition/totalSeconds * 100);

                            videoProgress.setProgressPercentage(percentProgress,true);


                            if (hours <= 0) {
                                if (minutes < 10 && secs < 10) {
                                    duration.setText(minutes + ":0" + secs);
                                } else if (minutes < 10 && secs >= 10) {
                                    duration.setText(minutes + ":" + secs);
                                } else if (minutes >= 10 && secs < 10) {
                                    duration.setText(minutes + ":0" + secs);
                                } else {
                                    duration.setText(minutes + ":" + secs);
                                }
                            } else {

                                if (secs == 60) secs = 0;
                                if (minutes == 60) minutes = 0;

                                if (minutes < 10 && secs < 10) {
                                    duration.setText(hours + ":0" + minutes + ":0" + secs);
                                } else if (minutes < 10 && secs >= 10) {
                                    duration.setText(hours + ":0" + minutes + ":" + secs);
                                } else if (minutes >= 10 && secs < 10) {
                                    duration.setText(hours + ":" + minutes + ":0" + secs);
                                } else {
                                    duration.setText(hours + ":" + minutes + ":" + secs);
                                }
                            }

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
                progressToolLayout.setVisibility(View.VISIBLE);
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
                progressToolLayout.setVisibility(View.GONE);

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
                progressToolLayout.setVisibility(View.GONE);
                thumbnail.setVisibility(View.VISIBLE);

            }

            public void scheduleVideoProgressToolDisappearance() {

                alphaAnim2 = new AlphaAnimation(1.0f, 0.0f);
                alphaAnim2.setStartOffset(4000);
                alphaAnim2.setDuration(400);
                alphaAnim2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    public void onAnimationEnd(Animation animation) {


                        if (isCancelled) {
                            transparentOverlay.setVisibility(View.VISIBLE);
                            playPauseView.setVisibility(View.VISIBLE);
                            playPauseLayout.setVisibility(View.VISIBLE);
                            isCancelled = false;
                        } else {
                            playPauseView.setVisibility(View.GONE);
                            playPauseLayout.setVisibility(View.GONE);
                            transparentOverlay.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                transparentOverlay.setAnimation(alphaAnim2);

            }

        }

    }

