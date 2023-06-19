/*
 * Copyright 2023 Gideon Rotich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.swayy.core.util

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val BASE_URL = "https://v3.football.api-sports.io/"

    // Headers
    const val API_KEY = "ac94f94bb2msh209b8322dafe16ep19e183jsnabc6710bd5a9"
    const val X_RAPID_KEY = "X-RapidAPI-Key"
    const val X_RAPID_HOST = "X-RapidAPI-Host"
    const val HOST_VALUE = "api-football-v1.p.rapidapi.com"

    // Endpoints
    const val GET_FIXTURES = "fixtures"
    const val GET_STANDINGS = "v3/standings"
    const val GET_LIVE_FIXTURES = "v3/fixtures"
    const val GET_TOP_SCORERS = "v3/players/{stat_type}"
}
