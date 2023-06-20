package com.swayy.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.swayy.core_database.converters.Converters
import com.swayy.core_database.dao.MatchesDao
import com.swayy.core_database.model.MatchesEntity

@Database(entities = [MatchesEntity::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class MatchesDatabase : RoomDatabase() {
    abstract val matchesDao: MatchesDao
}
