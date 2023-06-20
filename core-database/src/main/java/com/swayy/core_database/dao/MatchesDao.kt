package com.swayy.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swayy.core_database.model.MatchesEntity

@Dao
interface MatchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matchesEntity: List<MatchesEntity>)

    @Query("SELECT * FROM matches_table")
    fun getMatches(): List<MatchesEntity>

    @Query("DELETE FROM matches_table")
    suspend fun deleteMatches()

}