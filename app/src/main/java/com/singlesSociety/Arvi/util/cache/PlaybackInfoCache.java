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

package com.singlesSociety.Arvi.util.cache;


import android.util.Log;

import androidx.annotation.RestrictTo;

import com.singlesSociety.Arvi.model.PlaybackInfo;

import java.util.HashMap;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class PlaybackInfoCache implements Cache<String, PlaybackInfo> {


    private static volatile PlaybackInfoCache sInstance;

    private final Cache<String, PlaybackInfo> mCache;
    private static HashMap<String,PlaybackInfoCache> playbackInfoCacheMap = new HashMap<>();



    /**
     * Lazily creates an instance of the {@link PlaybackInfoCache} (if necessary).
     *
     * @return the instance of the {@link PlaybackInfoCache}
     */
    public static PlaybackInfoCache getInstance(boolean isNewPlayer,String id) {
        Log.e(String.valueOf(isNewPlayer), " getInstance: ");
     if(isNewPlayer){
         synchronized (PlaybackInfoCache.class) {
             sInstance = new PlaybackInfoCache();
             playbackInfoCacheMap.put(id, sInstance);
             return sInstance;
         }
     }
     else {
         if (sInstance == null) {
             synchronized (PlaybackInfoCache.class) {
                 if (sInstance == null) {
                     sInstance = new PlaybackInfoCache();
                     playbackInfoCacheMap.put(id,sInstance);
                     return sInstance;
                 }
             }
         }
         return playbackInfoCacheMap.get(id);
     }

    }






    private PlaybackInfoCache() {
        mCache = CacheType.IN_MEMORY.create(true);
    }




    @Override
    public final PlaybackInfo put(String key, PlaybackInfo value) {
        return mCache.put(key, value);
    }




    @Override
    public final PlaybackInfo get(String key) {
        return mCache.get(key);
    }




    @Override
    public final PlaybackInfo get(String key, PlaybackInfo defaultValue) {
        return mCache.get(key, defaultValue);
    }




    @Override
    public final <RV> RV getAs(String key) {
        return mCache.getAs(key);
    }




    @Override
    public final <RV> RV getAs(String key, RV defaultValue) {
        return mCache.getAs(key, defaultValue);
    }




    @Override
    public final PlaybackInfo remove(String key) {
        return mCache.remove(key);
    }




    @Override
    public final PlaybackInfo remove(String key, PlaybackInfo defaultValue) {
        return mCache.remove(key, defaultValue);
    }




    @Override
    public final <RV> RV removeAs(String key) {
        return mCache.removeAs(key);
    }




    @Override
    public final <RV> RV removeAs(String key, RV defaultValue) {
        return mCache.removeAs(key, defaultValue);
    }




    @Override
    public final boolean contains(String key) {
        return mCache.contains(key);
    }




    @Override
    public final boolean clear() {
        return mCache.clear();
    }




}
