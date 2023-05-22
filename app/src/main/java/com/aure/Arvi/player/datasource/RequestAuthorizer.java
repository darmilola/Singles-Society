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

package com.aure.Arvi.player.datasource;

import androidx.annotation.NonNull;

/**
 * A base contract to be implemented by the concrete HTTP Video Data Request Authorizers.
 * (Should be able to provide the appropriate authorization token)
 */
public interface RequestAuthorizer {

    /**
     * Used to obtain the appropriate Authorization Token for the Video Data Request.
     *
     * @return the authorization token to be used for the request
     */
    @NonNull
    String getAuthorization();

}