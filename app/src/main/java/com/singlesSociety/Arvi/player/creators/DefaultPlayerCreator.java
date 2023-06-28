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

package com.singlesSociety.Arvi.player.creators;

import static com.singlesSociety.Arvi.util.misc.Preconditions.checkNonNull;


import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.singlesSociety.Arvi.Config;
import com.singlesSociety.Arvi.PlayerProvider;
import com.singlesSociety.Arvi.player.DefaultPlayer;
import com.singlesSociety.Arvi.player.Player;
import com.singlesSociety.Arvi.player.util.MediaSourceBuilder;
import com.singlesSociety.Arvi.player.util.MultiDrmRendererFactory;
import com.singlesSociety.Arvi.util.misc.CollectionUtils;
import com.singlesSociety.Arvi.util.misc.Preconditions;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;

import java.util.Arrays;


public final class DefaultPlayerCreator implements PlayerCreator {


    public final PlayerProvider playerProvider;

    private final TrackSelector trackSelector;
    private final LoadControl loadControl;
    private final BandwidthMeter bandwidthMeter;
    private final MediaSourceBuilder mediaSourceBuilder;
    private final RenderersFactory renderersFactory;
    private final DataSource.Factory mediaDataSourceFactory;
    private final DataSource.Factory manifestDataSourceFactory;
    private final DrmSessionManager[] drmSessionManagers;




    public DefaultPlayerCreator(@NonNull PlayerProvider playerProvider, @NonNull Config config) {
        Preconditions.nonNull(playerProvider);
        Preconditions.nonNull(config);

        this.playerProvider = checkNonNull(playerProvider);
        this.trackSelector = new DefaultTrackSelector();
        this.loadControl = config.loadControl;
        this.bandwidthMeter = config.meter;
        this.mediaSourceBuilder = config.mediaSourceBuilder;
        this.renderersFactory = new MultiDrmRendererFactory(
            playerProvider.getContext(),
            config.drmSessionManagers,
            config.extensionMode
        );
        this.mediaDataSourceFactory = createDataSourceFactory(playerProvider, config);
        this.manifestDataSourceFactory = new DefaultDataSourceFactory(playerProvider.getContext(), playerProvider.getLibraryName());
        this.drmSessionManagers = config.drmSessionManagers;
    }




    private DataSource.Factory createDataSourceFactory(PlayerProvider playerProvider, Config config) {
        DataSource.Factory baseFactory = config.dataSourceFactory;

        if(baseFactory == null) {
            baseFactory = new DefaultHttpDataSourceFactory(playerProvider.getLibraryName(), config.meter);
        }

        DataSource.Factory factory = new DefaultDataSourceFactory(
            playerProvider.getContext(),
            config.meter,
            baseFactory
        );

        if(config.cache != null) {
            factory = new CacheDataSourceFactory(config.cache, factory);
        }

        return factory;
    }




    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public final Player createPlayer() {
        return new DefaultPlayer(
            this.playerProvider.getContext(),
            this.renderersFactory,
            this.trackSelector,
            this.loadControl,
            this.bandwidthMeter,
            CollectionUtils.takeFirstOrNull(this.drmSessionManagers)
        );
    }




    @NonNull
    @Override
    public final MediaSource createMediaSource(@NonNull Uri uri) {
        return createMediaSource(uri, "");
    }




    @NonNull
    @Override
    public final MediaSource createMediaSource(@NonNull Uri uri, @NonNull String extension) {
        Preconditions.nonNull(uri);
        Preconditions.nonNull(extension);

        return this.mediaSourceBuilder.buildMediaSource(
            this.playerProvider.getContext(),
            uri,
            extension,
            new Handler(),
            this.manifestDataSourceFactory,
            this.mediaDataSourceFactory,
            null
        );
    }




    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 17;
        result = ((prime * result) + this.playerProvider.hashCode());
        result = ((prime * result) + this.trackSelector.hashCode());
        result = ((prime * result) + this.loadControl.hashCode());
        result = ((prime * result) + this.mediaSourceBuilder.hashCode());
        result = ((prime * result) + this.renderersFactory.hashCode());
        result = ((prime * result) + this.mediaDataSourceFactory.hashCode());
        result = ((prime * result) + this.manifestDataSourceFactory.hashCode());
        result = ((prime * result) + Arrays.hashCode(this.drmSessionManagers));

        return result;
    }




    @Override
    public final boolean equals(Object obj) {
        return ((obj instanceof DefaultPlayerCreator) && (obj.hashCode() == hashCode()));
    }




}
