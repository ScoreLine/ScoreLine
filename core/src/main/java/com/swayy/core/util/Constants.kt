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

    // Endpoints
    const val GET_FIXTURES = "fixtures"
    const val GET_LINEUP = "fixtures/lineups"
    const val GET_EVENTS = "fixtures/events"
    const val GET_STANDINGS = "standings"
    const val GET_LEAGUES = "leagues"
    const val GET_TOPSCORERS = "players/topscorers"
    const val GET_TOPASSISTS = "leagues"
    const val GET_STATS = "fixtures/statistics"
    const val GET_H2H = "fixtures/headtohead"
    const val LIVE_MATCH = "fixtures"
}
