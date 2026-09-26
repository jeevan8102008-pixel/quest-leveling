package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_quests")
data class DailyQuestEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val userId: Long,
  val dayOfWeek: Int, // 1 to 7 (Day 1 through Day 7)
  val title: String,
  val targetValue: Int,
  val currentProgress: Int = 0,
  val unit: String = "reps",
  val isCompleted: Boolean = false,
  val category: String = "MAIN", // "WARMUP", "DAILY_CORE", "MAIN", "WATER", "COOLDOWN"
  val exerciseKey: String = "",
  val estimatedMinutes: Int = 10,
  val targetMuscles: String = "Full Body"
)
