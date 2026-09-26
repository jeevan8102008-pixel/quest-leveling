package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.WaterLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {
  @Query("SELECT * FROM water_logs WHERE userId = :userId AND dateString = :dateString LIMIT 1")
  fun getWaterLog(userId: Long, dateString: String): Flow<WaterLogEntity?>

  @Query("SELECT * FROM water_logs WHERE userId = :userId AND dateString = :dateString LIMIT 1")
  suspend fun getWaterLogSync(userId: Long, dateString: String): WaterLogEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertWaterLog(log: WaterLogEntity)

  @Update
  suspend fun updateWaterLog(log: WaterLogEntity)
}
