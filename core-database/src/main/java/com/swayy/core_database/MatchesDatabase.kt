package com.swayy.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.swayy.core_database.converters.Converters
import com.swayy.core_database.dao.ClubsDao
import com.swayy.core_database.dao.FavoritesDao
import com.swayy.core_database.dao.MatchesDao
import com.swayy.core_database.dao.NewsDao
import com.swayy.core_database.dao.SoccerDao
import com.swayy.core_database.dao.StandingsDao
import com.swayy.core_database.model.ClubsEntity
import com.swayy.core_database.model.EventsEntity
import com.swayy.core_database.model.FavoriteEntity
import com.swayy.core_database.model.HeadToHeadEntity
import com.swayy.core_database.model.LeaguesEntity
import com.swayy.core_database.model.LineupEntity
import com.swayy.core_database.model.LiveMatchesEntity
import com.swayy.core_database.model.MatchesEntity
import com.swayy.core_database.model.NewsEntity
import com.swayy.core_database.model.SoccerEntity
import com.swayy.core_database.model.StandingsEntity
import com.swayy.core_database.model.StatsEntity
import com.swayy.core_database.model.TopAssistsEntity
import com.swayy.core_database.model.TopScorersEntity

@Database(
    entities = [
        MatchesEntity::class, LineupEntity::class,
        EventsEntity::class, NewsEntity::class,
        StandingsEntity::class, LeaguesEntity::class,
        TopScorersEntity::class, TopAssistsEntity::class,
        ClubsEntity::class, FavoriteEntity::class,
        StatsEntity::class, HeadToHeadEntity::class,
        LiveMatchesEntity::class, SoccerEntity::class], version = 22, exportSchema = true
)
@TypeConverters(Converters::class)
abstract class MatchesDatabase : RoomDatabase() {
    abstract val matchesDao: MatchesDao
    abstract val newsDao: NewsDao
    abstract val standingsDao: StandingsDao
    abstract val clubsDao: ClubsDao
    abstract val favoritesDao: FavoritesDao
    abstract val soccerDao: SoccerDao
}