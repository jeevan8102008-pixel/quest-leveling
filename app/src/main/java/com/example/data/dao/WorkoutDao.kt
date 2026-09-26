package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entity.WorkoutLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
  @Query("SELECT * FROM workout_logs WHERE userId = :userId ORDER BY dateCompleted DESC")
  fun getWorkoutLogs(userId: Long): Flow<List<WorkoutLogEntity>>

  @Query("SELECT COUNT(*) FROM workout_logs WHERE userId = :userId")
  fun getCompletedWorkoutCount(userId: Long): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertWorkoutLog(log: WorkoutLogEntity)
}
