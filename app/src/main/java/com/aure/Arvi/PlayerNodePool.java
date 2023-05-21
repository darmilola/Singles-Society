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


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aure.Arvi.player.Player;


interface PlayerNodePool {


    void add(@NonNull PlayerNode player);

    @Nullable
    PlayerNode remove(@NonNull PlayerNode playerNode);


    @Nullable
   PlayerNode remove(@NonNull String key);


    void unregister(@NonNull String key);


    @Nullable
    PlayerNode acquire(@NonNull String key);


    @Nullable
    PlayerNode acquireFree(@NonNull String key);


    @Nullable
    PlayerNode acquireOldest(@NonNull String key);

    void release(@NonNull PlayerNode playerNode);


    void release(@NonNull String key);

    void release();


    @Nullable
    PlayerNode get(@NonNull String key);


    @Nullable
    PlayerNode getFree();


    @Nullable
    PlayerNode getOldest();


    int getPlayerCount();

    boolean isFull();


    boolean contains(@NonNull String key);
    void add(@NonNull String key, @NonNull Player player);

}
