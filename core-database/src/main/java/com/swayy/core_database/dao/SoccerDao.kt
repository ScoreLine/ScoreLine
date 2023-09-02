package com.swayy.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swayy.core_database.model.ClubsEntity
import com.swayy.core_database.model.SoccerEntity
@Dao
interface SoccerDao {
    @Query("SELECT * FROM soccer_table")
    fun getAllSoccer(): List<SoccerEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSoccer(soccerEntity: List<SoccerEntity>)

    @Query("DELETE FROM soccer_table")
    suspend fun deleteSoccer()
}