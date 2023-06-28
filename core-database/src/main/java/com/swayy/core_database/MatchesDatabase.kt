package com.swayy.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.swayy.core_database.converters.Converters
import com.swayy.core_database.dao.MatchesDao
import com.swayy.core_database.dao.StandingsDao
import com.swayy.core_database.model.LineupEntity
import com.swayy.core_database.model.MatchesEntity
import com.swayy.core_database.model.StandingsEntity

@Database(entities = [MatchesEntity::class, LineupEntity::class, StandingsEntity::class], version = 6, exportSchema = true)
@TypeConverters(Converters::class)
abstract class MatchesDatabase : RoomDatabase() {
    abstract val matchesDao: MatchesDao
    abstract val standingsDao: StandingsDao
}
