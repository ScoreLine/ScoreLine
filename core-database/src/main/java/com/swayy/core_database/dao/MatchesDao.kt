package com.swayy.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swayy.core_database.model.EventsEntity
import com.swayy.core_database.model.LineupEntity
import com.swayy.core_database.model.MatchesEntity
import com.swayy.core_database.model.NewsEntity

@Dao
interface MatchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matchesEntity: List<MatchesEntity>)

    @Query("SELECT * FROM matches_table")
    fun getMatches(): List<MatchesEntity>

    @Query("DELETE FROM matches_table")
    suspend fun deleteMatches()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLineup(lineupEntity: List<LineupEntity?>)

    @Query("SELECT * FROM lineup_table")
    fun getLineup(): List<LineupEntity>

    @Query("DELETE FROM lineup_table")
    suspend fun deleteLineup()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(eventsEntity: List<EventsEntity>)

    @Query("SELECT * FROM events_table")
    fun getEvents(): List<EventsEntity>

    @Query("DELETE FROM events_table")
    suspend fun deleteEvents()

}