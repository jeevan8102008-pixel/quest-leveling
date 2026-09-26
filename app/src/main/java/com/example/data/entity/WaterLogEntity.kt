package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_logs")
data class WaterLogEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val userId: Long,
  val dateString: String,
  val amountMl: Int = 0,
  val targetMl: Int = 4000
)
