/*
 * Copyright 2017 Arthur Ivanets, arthur.ivanets.l@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aure.Arvi.widget;

import static com.aure.Arvi.util.misc.CollectionUtils.hashSetOf;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.aure.Arvi.PlayerProviderImpl;
import com.aure.Arvi.util.misc.Preconditions;

import java.util.HashSet;
import java.util.Set;


public final class PlayableItemsRecyclerView extends RecyclerView implements PlayableItemsContainer {


    private static final Set<PlaybackTriggeringState> DEFAULT_PLAYBACK_TRIGGERING_STATES = hashSetOf(
        PlaybackTriggeringState.DRAGGING,
        PlaybackTriggeringState.IDLING
    );

    private final Set<PlaybackTriggeringState> mPlaybackTriggeringStates = new HashSet<>();

    private int mPreviousScrollDeltaX;
    private int mPreviousScrollDeltaY;

    private AutoplayMode mAutoplayMode;

    private boolean mIsAutoplayEnabled;
    private boolean mIsScrolling;
    private boolean isPlayBackPlaying = false;
    private boolean isOnReadyState = false;
    private boolean isFromActivityStateChange = false;




    public PlayableItemsRecyclerView(Context context) {
        super(context);
        init();
    }




    public PlayableItemsRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }




    public PlayableItemsRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setOnReadyState(boolean onReadyState) {
        isOnReadyState = onReadyState;
    }

    public boolean isOnReadyState() {
        return isOnReadyState;
    }

    private void init() {
        mPreviousScrollDeltaX = 0;
        mPreviousScrollDeltaY = 0;
        mAutoplayMode = AutoplayMode.ONE_AT_A_TIME;
        mIsAutoplayEnabled = true;
        mPlaybackTriggeringStates.addAll(DEFAULT_PLAYBACK_TRIGGERING_STATES);
        setRecyclerListener(this::onRecyclerViewViewRecycled);
    }

    @Override
    public final void startPlayback() {
        handleItemPlayback();
    }

    public  boolean setPlayBackPlaying(boolean isPlayBackPlaying){
        this.isPlayBackPlaying = isPlayBackPlaying;
        return  this.isPlayBackPlaying;
    }

    public boolean isPlayBackPlaying() {
        return isPlayBackPlaying;
    }

    @Override
    public final void stopPlayback() {
        stopItemPlayback();
        //releaseAllItems();
    }




    @Override
    public final void pausePlayback() {
        pauseItemPlayback();
    }




    @Override
    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        //startPlayback();
    }




    @Override
    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopItemPlayback();
    }




    @Override
    public final void onChildAttachedToWindow(View child) {
        super.onChildAttachedToWindow(child);

        final ViewHolder viewHolder = getChildViewHolder(child);
        if(viewHolder instanceof PlayableItemViewHolder) {

            ((PlayableItemViewHolder) viewHolder).setPlayBackStartedPlaying(new PlayableItemViewHolder.PlayBackStarted() {
                @Override
                public void onPlayBackPlaying() {
                    setPlayBackPlaying(true);
                }
            });
            ((PlayableItemViewHolder) viewHolder).setOnReadyState(new PlayableItemViewHolder.OnReadyState() {
                @Override
                public void isOnReadyState() {

                }
            });
            ((PlayableItemViewHolder) viewHolder).setOnPlayBackPause(new PlayableItemViewHolder.onPlayBackPause() {
                @Override
                public void isOnPaused() {

                    if (!isFromActivityStateChange) setPlayBackPlaying(false);

                }
            });
            ((PlayableItemViewHolder) viewHolder).setOnPlayBackStopped(new PlayableItemViewHolder.onPlayBackStopped() {
                @Override
                public void isOnStopped() {
                    setPlayBackPlaying(false);
                }
            });

        }
    }

    @Override
    public final void onChildDetachedFromWindow(View child) {
        super.onChildDetachedFromWindow(child);
    }




    @Override
    public final void onResume() {
        startPlayback();
    }




    @Override
    public final void onPause(boolean fromActivityStateChange) {
        isFromActivityStateChange = fromActivityStateChange;
        if(!isFromActivityStateChange)setOnReadyState(false);
          pausePlayback();
    }




    @Override
    public final void onDestroy(boolean isFromActivityStateChange) {
        if(isFromActivityStateChange){
            setOnReadyState(false);
            setPlayBackPlaying(false);
            releaseAllItems();
            PlayerProviderImpl.getInstance(getContext()).release();
        }
        else{
            setOnReadyState(false);
            setPlayBackPlaying(false);
            releaseAllItems();
        }
    }




    private void onRecyclerViewViewRecycled(ViewHolder holder) { }




   private void handleItemPlayback() {

        final int childCount = getChildCount();
        ViewHolder viewHolder;
        // extracting all the playable visible items
        for(int i = 0; i < childCount; i++) {
            viewHolder = findContainingViewHolder(getChildAt(i));

            if(viewHolder instanceof Playable && ((PlayableItemViewHolder)viewHolder).isMuted()){
                ((PlayableItemViewHolder)viewHolder).setMuted(false);
            }
            else {

                if ((viewHolder instanceof Playable)
                        && ((Playable) viewHolder).isTrulyPlayable()) {
                    Playable playable = ((Playable) viewHolder);
                    if ((!((PlayableItemViewHolder) viewHolder).isPausedByUser()) && !((PlayableItemViewHolder) viewHolder).getPlayBackCacheID().equalsIgnoreCase("") && !playable.isPlaying()) {
                        playable.start();
                    }
                }
            }
        }
    }
    private void stopItemPlayback() {
        final int childCount = getChildCount();
        ViewHolder viewHolder;

        for(int i = 0; i < childCount; i++) {
            viewHolder = findContainingViewHolder(getChildAt(i));

            if((viewHolder instanceof Playable)
                    && ((Playable) viewHolder).isTrulyPlayable() && !((PlayableItemViewHolder)viewHolder).getPlayBackCacheID().equalsIgnoreCase("")) {
               ((Playable) viewHolder).stop();
                setPlayBackPlaying(false);
                ((PlayableItemViewHolder)viewHolder).setPausedByUser(true);
                 setOnReadyState(false);
            }
        }
    }




    private void pauseItemPlayback() {
        final int childCount = getChildCount();
        ViewHolder viewHolder;
        Log.e(String.valueOf(childCount),"  pauseItemPlayback: ");

        for(int i = 0; i < childCount; i++) {
            viewHolder = findContainingViewHolder(getChildAt(i));

            if((viewHolder instanceof Playable) && !((PlayableItemViewHolder)viewHolder).getPlayBackCacheID().equalsIgnoreCase("") && ((Playable) viewHolder).isTrulyPlayable() && ((Playable) viewHolder).isPlaying() && ((PlayableItemViewHolder) viewHolder).getInReadyState()) {
                   ((Playable) viewHolder).pause();

            }
            else if((viewHolder instanceof Playable) && !((PlayableItemViewHolder)viewHolder).getPlayBackCacheID().equalsIgnoreCase("") && ((Playable) viewHolder).isTrulyPlayable() && ((Playable) viewHolder).isPlaying() && !((PlayableItemViewHolder) viewHolder).getInReadyState()) {
                ((Playable) viewHolder).stop();
            }
        }

        }


        private void releaseAllItems() {
        final int childCount = getChildCount();
        ViewHolder viewHolder;
        for(int i = 0; i < childCount; i++) {
            viewHolder = findContainingViewHolder(getChildAt(i));
            if((viewHolder instanceof Playable)
                    && ((Playable) viewHolder).isTrulyPlayable() &&  !((PlayableItemViewHolder)viewHolder).getPlayBackCacheID().equalsIgnoreCase("")) {
                ((Playable) viewHolder).release();
            }
        }
    }

    @Override
    public final void setAutoplayMode(@NonNull AutoplayMode autoplayMode) {
        mAutoplayMode = Preconditions.checkNonNull(autoplayMode);

        if(isAutoplayEnabled()) {
            //startPlayback();
        }
    }




    @NonNull
    @Override
    public final AutoplayMode getAutoplayMode() {
        return mAutoplayMode;
    }




    @Override
    public final void setPlaybackTriggeringStates(@NonNull PlaybackTriggeringState... states) {
        Preconditions.nonNull(states);

        mPlaybackTriggeringStates.clear();
        mPlaybackTriggeringStates.addAll((states.length == 0) ? DEFAULT_PLAYBACK_TRIGGERING_STATES : hashSetOf(states));
    }




    @NonNull
    @Override
    public final Set<PlaybackTriggeringState> getPlaybackTriggeringStates() {
        return mPlaybackTriggeringStates;
    }




    private PlaybackTriggeringState getPlaybackStateForScrollState(int scrollState) {
        switch(scrollState) {

            case SCROLL_STATE_SETTLING:
                return PlaybackTriggeringState.SETTLING;

            case SCROLL_STATE_DRAGGING:
                return PlaybackTriggeringState.DRAGGING;

            default:
                return PlaybackTriggeringState.IDLING;

        }
    }




    @Override
    public final void setAutoplayEnabled(boolean isAutoplayEnabled) {
        mIsAutoplayEnabled = isAutoplayEnabled;

        if(isAutoplayEnabled) {
            //startPlayback();
        } else {
            //stopPlayback();
        }
    }




    @Override
    public final boolean isAutoplayEnabled() {
        return mIsAutoplayEnabled;
    }




    @Override
    public final void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        //handleItemPlayback();
    }




    @Override
    public final void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        mIsScrolling = ((Math.abs(mPreviousScrollDeltaX - dx) > 0) || (Math.abs(mPreviousScrollDeltaY - dy) > 0));
        //handleItemPlayback();
        mPreviousScrollDeltaX = dx;
        mPreviousScrollDeltaY = dy;
    }

    private boolean canPlay() {
        final PlaybackTriggeringState state = getPlaybackStateForScrollState(getScrollState());
        final boolean containsState = mPlaybackTriggeringStates.contains(state);
        final boolean isDragging = (PlaybackTriggeringState.DRAGGING.equals(state) && !mIsScrolling);
        final boolean isSettling = PlaybackTriggeringState.SETTLING.equals(state);
        final boolean isIdling = PlaybackTriggeringState.IDLING.equals(state);
        return (containsState && (isDragging || isSettling || isIdling));
    }

}
