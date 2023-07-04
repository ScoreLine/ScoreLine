package com.swayy.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swayy.core_database.model.ClubsEntity
import com.swayy.core_database.model.NewsEntity

@Dao
interface ClubsDao {
    @Query("SELECT * FROM clubs_table")
    fun getAllClubs(): List<ClubsEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClubs(clubsEntity: List<ClubsEntity>)

    @Query("DELETE FROM clubs_table")
    suspend fun deleteClubs()
}