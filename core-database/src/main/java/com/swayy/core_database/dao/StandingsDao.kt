package com.swayy.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swayy.core_database.model.StandingsEntity

@Dao
interface StandingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStandings(standingsEntity: List<StandingsEntity>)

    @Query("SELECT * FROM standings_table")
    fun getStandings(): List<StandingsEntity>

    @Query("DELETE FROM standings_table")
    suspend fun deleteStandings()
}