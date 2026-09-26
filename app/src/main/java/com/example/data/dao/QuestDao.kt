package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.DailyQuestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {
  @Query("SELECT * FROM daily_quests WHERE userId = :userId AND dayOfWeek = :dayOfWeek ORDER BY id ASC")
  fun getQuestsForUserAndDay(userId: Long, dayOfWeek: Int): Flow<List<DailyQuestEntity>>

  @Query("SELECT * FROM daily_quests WHERE userId = :userId ORDER BY dayOfWeek ASC, id ASC")
  fun getAllQuestsForUser(userId: Long): Flow<List<DailyQuestEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertQuests(quests: List<DailyQuestEntity>)

  @Update
  suspend fun updateQuest(quest: DailyQuestEntity)

  @Query("UPDATE daily_quests SET isCompleted = :isCompleted, currentProgress = :progress WHERE id = :id")
  suspend fun updateQuestProgress(id: Long, progress: Int, isCompleted: Boolean)

  @Query("DELETE FROM daily_quests WHERE userId = :userId")
  suspend fun deleteQuestsForUser(userId: Long)
}
