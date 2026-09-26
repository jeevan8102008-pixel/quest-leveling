package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val email: String,
  val passwordHash: String,
  val name: String = "",
  val gender: String = "Men",
  val bodyType: String = "Medium",
  val age: Int = 20,
  val heightCm: Float = 175f,
  val weightKg: Float = 70f,
  val role: String = "Hunter",
  val workoutTimePref: String = "Morning",
  val preferredStartTime: String = "07:00",
  val sessionDurationMinutes: Int = 45,
  val trainingLocation: String = "Home",
  val fitnessGoal: String = "Become stronger",
  val currentRank: String = "E Rank",
  val xp: Int = 0,
  val waterTargetMl: Int = 4000,
  val hasCompletedOnboarding: Boolean = false,
  val isActiveSession: Boolean = true,
  val createdAt: Long = System.currentTimeMillis()
)
