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

import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;

import com.aure.Arvi.Config;
import com.aure.Arvi.model.PlaybackInfo;

/**
 * A base contract to be implemented by the item that support the playback management.
 */
public interface Playable extends PlayabilityStateChangeObserver {

    /**
     * Starts the playback.
     */
    void start();

    /**
     * Restarts the playback.
     */
    void restart();

    /**
     * Pauses the playback.
     */
    void pause();

    /**
     * Stops the playback.
     */
    void stop();


    void release();

    /**
     * Seeks to a specific playback position (in milliseconds).
     *
     * @param positionInMillis the exact position to seek to
     */
    void seekTo(long positionInMillis);

    /**
     * Retrieves the current playback position of the Player (in milliseconds).
     *
     * @return the current playback position
     */
    long getPlaybackPosition();

    /**
     * Retrieves the duration of the media track (in milliseconds).
     *
     * @return the duration of the media track
     */
    long getDuration();

    /**
     * Retrieves the player view associated with this item.
     *
     * @return the player view
     */
    View getPlayerView();

    /**
     * Retrieves the item's parent view.
     *
     * @return the parent view
     */
    ViewParent getParent();

    /**
     * Retrieves the corresponding {@link PlaybackInfo} (if there's any active playback).
     *
     * @return the corresponding playback info
     */
    PlaybackInfo getPlaybackInfo();

    /**
     * Retrieves the media Url associated with this item.
     *
     * @return the media url
     */
    @NonNull
    String getUrl();


    @NonNull
    String getTag();


    @NonNull
    String getKey();


    @NonNull
    Config getConfig();

    /**
     * Determines whether the current item is in active playback.
     *
     * @return whether the current item is in active playback
     */
    boolean isPlaying();


    boolean isTrulyPlayable();

    /**
     * Determines if this item's playback is a looping one.
     *
     * @return if the playback is a looping one
     */
    boolean isLooping();

    /**
     * Determines if the current item is visible enough to be able to start the playback.
     *
     * @return whether the item wants to start the playback
     */
    boolean wantsToPlay();

}
