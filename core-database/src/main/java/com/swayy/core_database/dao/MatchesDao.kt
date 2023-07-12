package com.swayy.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swayy.core_database.model.EventsEntity
import com.swayy.core_database.model.HeadToHeadEntity
import com.swayy.core_database.model.LineupEntity
import com.swayy.core_database.model.LiveMatchesEntity
import com.swayy.core_database.model.MatchesEntity
import com.swayy.core_database.model.NewsEntity
import com.swayy.core_database.model.StatsEntity

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(statsEntity: List<StatsEntity>)

    @Query("SELECT * FROM stats_table")
    fun getStats(): List<StatsEntity>

    @Query("DELETE FROM stats_table")
    suspend fun deleteStats()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertH2H(h2hEntity: List<HeadToHeadEntity>)

    @Query("SELECT * FROM h2h_table")
    fun getH2H(): List<HeadToHeadEntity>

    @Query("DELETE FROM h2h_table")
    suspend fun deleteH2H()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLiveMatches(liveEntity: List<LiveMatchesEntity>)

    @Query("SELECT * FROM livematches_table")
    fun getLiveMatches(): List<LiveMatchesEntity>

    @Query("DELETE FROM livematches_table")
    suspend fun deleteLiveMatches()

}