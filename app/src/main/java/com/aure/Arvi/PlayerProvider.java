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

package com.aure.Arvi;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aure.Arvi.player.Player;
import com.google.android.exoplayer2.source.MediaSource;

/**
 * Defines a base contract for the concrete {@link PlayerProvider} implementations.
 */
public interface PlayerProvider {


    @NonNull
    MediaSource createMediaSource(@NonNull Uri uri);


    @NonNull
    MediaSource createMediaSource(@NonNull Uri uri, boolean isLooping);


    @NonNull
    MediaSource createMediaSource(@NonNull Config config, @NonNull Uri uri);


    @NonNull
    MediaSource createMediaSource(@NonNull Config config, @NonNull Uri uri, boolean isLooping);

    /**
     * Retrieves the library name.
     *
     * @return the library name
     */
    @NonNull
    String getLibraryName();

    /**
     * Retrieves the application {@link Context}.
     *
     * @return the application context
     */
    @NonNull
    Context getContext();


    @Nullable
    Player getPlayer(@NonNull String key);


    @Nullable
    Player getPlayer(@NonNull Config config, @NonNull String key);

    @NonNull
    Player getOrInitPlayer(@NonNull String key);


    @NonNull
    Player getOrInitPlayer(@NonNull Config config, @NonNull String key);


    boolean hasPlayer(@NonNull String key);


    boolean hasPlayer(@NonNull Config config, @NonNull String key);


    void unregister(@NonNull String key);


    void unregister(@NonNull Config config, @NonNull String key);


    void release(@NonNull String key);

    /**
     * Releases all the {@link Player}s that match the specified {@link Config}.
     *
     * @param config the player configuration
     */
    void release(@NonNull Config config);

    /**
     * Releases the {@link Player} for the specified key and {@link Config}.
     *
     * @param config the player configuration
     * @param key the key to release the Player for
     */
    void release(@NonNull Config config, @NonNull String key);

    /**
     * Releases all the currently available (initialized) {@link Player}s.
     */
    void release();

}
