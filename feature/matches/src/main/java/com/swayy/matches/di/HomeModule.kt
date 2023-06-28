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
package com.swayy.matches.di


import com.swayy.core_database.dao.MatchesDao
import com.swayy.core_network.LiveScoreApi
import com.swayy.matches.data.repository.MatchRepositoryImpl
import com.swayy.matches.domain.repository.MatchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideMatchesRepository(
        liveScoreApi: LiveScoreApi,
        matchesDao: MatchesDao

    ): MatchRepository {
        return MatchRepositoryImpl(
            liveScoreApi = liveScoreApi,
            matchesDao = matchesDao
        )
    }

}

