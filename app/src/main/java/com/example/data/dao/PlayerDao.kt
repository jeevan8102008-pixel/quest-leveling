package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
  @Query("SELECT * FROM players WHERE isActiveSession = 1 LIMIT 1")
  fun getActivePlayer(): Flow<PlayerEntity?>

  @Query("SELECT * FROM players WHERE email = :email LIMIT 1")
  suspend fun getPlayerByEmail(email: String): PlayerEntity?

  @Query("SELECT * FROM players WHERE id = :id LIMIT 1")
  fun getPlayerById(id: Long): Flow<PlayerEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPlayer(player: PlayerEntity): Long

  @Update
  suspend fun updatePlayer(player: PlayerEntity)

  @Query("UPDATE players SET isActiveSession = 0")
  suspend fun clearActiveSessions()

  @Query("UPDATE players SET isActiveSession = 1 WHERE id = :id")
  suspend fun setActiveSession(id: Long)
}
