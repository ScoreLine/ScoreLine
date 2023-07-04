package com.swayy.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swayy.core_database.model.LeaguesEntity
import com.swayy.core_database.model.StandingsEntity
import com.swayy.core_database.model.TopAssistsEntity
import com.swayy.core_database.model.TopScorersEntity

@Dao
interface StandingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStandings(standingsEntity: List<StandingsEntity>)

    @Query("SELECT * FROM standings_table")
    fun getStandings(): List<StandingsEntity>

    @Query("DELETE FROM standings_table")
    suspend fun deleteStandings()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeagues(leaguesEntity: List<LeaguesEntity>)

    @Query("SELECT * FROM leagues_table")
    fun getLeagues(): List<LeaguesEntity>

    @Query("DELETE FROM leagues_table")
    suspend fun deleteLeagues()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopScorers(topScorersEntity: List<TopScorersEntity>)

    @Query("SELECT * FROM topscorers_table")
    fun getTopScorers(): List<TopScorersEntity>

    @Query("DELETE FROM topscorers_table")
    suspend fun deleteTopScorers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopAssists(topAssistsEntity: List<TopAssistsEntity>)

    @Query("SELECT * FROM topassists_table")
    fun getTopAssists(): List<TopAssistsEntity>

    @Query("DELETE FROM topassists_table")
    suspend fun deleteTopAssists()
}