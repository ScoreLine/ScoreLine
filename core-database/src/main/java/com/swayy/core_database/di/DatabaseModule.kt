package com.swayy.core_database.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.swayy.core_database.MatchesDatabase
import com.swayy.core_database.converters.Converters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideTypeConverters(gson: Gson) =
        Converters(gson)

    @Provides
    @Singleton
    fun provideMatchesDatabase(
        @ApplicationContext context: Context,
        converters: Converters
    ): MatchesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MatchesDatabase::class.java,
            "MatchesDatabase"
        )
            .fallbackToDestructiveMigration()
            .addTypeConverter(converters)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideMatchesDao(database: MatchesDatabase) = database.matchesDao

    @Provides
    @Singleton
    fun provideStandingsDao(database: MatchesDatabase) = database.standingsDao

    @Provides
    @Singleton
    fun provideNewsDao(database: MatchesDatabase) = database.newsDao

    @Provides
    @Singleton
    fun provideClubsDao(database: MatchesDatabase) = database.clubsDao

    @Provides
    @Singleton
    fun provideFavoriteDao(database: MatchesDatabase) = database.favoritesDao

    @Provides
    @Singleton
    fun provideSoccerDao(database: MatchesDatabase) = database.soccerDao

}