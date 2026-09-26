package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_logs")
data class WorkoutLogEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val userId: Long,
  val dayOfWeek: Int,
  val dateCompleted: String,
  val durationMinutes: Int,
  val caloriesBurned: Int,
  val xpEarned: Int = 50
)
