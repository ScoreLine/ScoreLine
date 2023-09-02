package com.swayy.core.di

import android.app.Application
import android.content.Context
import com.swayy.core.data.datastore.AppSettingsManager
import com.swayy.core.data.datastore.ThemeSettingsManager
import com.swayy.core.data.repository.LeagueStandingRepositoryImpl
import com.swayy.core.data.repository.WebMatchRepositoryImpl
import com.swayy.core.domain.repository.LeagueStandingRepository
import com.swayy.core.domain.repository.WebMatchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val ACRA_SHARED_PREFS_NAME = "acra_shared_pref"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWebMatchRepository(

    ): WebMatchRepository {
        return WebMatchRepositoryImpl(

        )
    }

    @Provides
    @Singleton
    fun provideLeagueStandingRepository(

    ): LeagueStandingRepository {
        return LeagueStandingRepositoryImpl(

        )
    }

    // appTheme datastore
    @Provides
    @Singleton
    fun provideThemeSettingsManager(@ApplicationContext context: Context) =
        ThemeSettingsManager(context)

    // settings datastore
    @Provides
    @Singleton
    fun provideAppSettingsManager(@ApplicationContext context: Context) =
        AppSettingsManager(context)

}