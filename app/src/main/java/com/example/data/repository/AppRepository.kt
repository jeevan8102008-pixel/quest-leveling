package com.example.data.repository

import com.example.data.dao.PlayerDao
import com.example.data.dao.QuestDao
import com.example.data.dao.WaterDao
import com.example.data.dao.WorkoutDao
import com.example.data.entity.DailyQuestEntity
import com.example.data.entity.PlayerEntity
import com.example.data.entity.WaterLogEntity
import com.example.data.entity.WorkoutLogEntity
import com.example.data.generator.QuestPlanGenerator
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppRepository(
  private val playerDao: PlayerDao,
  private val questDao: QuestDao,
  private val waterDao: WaterDao,
  private val workoutDao: WorkoutDao
) {

  val activePlayer: Flow<PlayerEntity?> = playerDao.getActivePlayer()

  suspend fun registerPlayer(email: String, password: String, name: String = ""): PlayerEntity {
    playerDao.clearActiveSessions()
    val newPlayer = PlayerEntity(
      email = email.trim(),
      passwordHash = password,
      name = name.ifBlank { "Hunter" },
      currentRank = "E Rank",
      hasCompletedOnboarding = false,
      isActiveSession = true
    )
    val id = playerDao.insertPlayer(newPlayer)
    val created = newPlayer.copy(id = id)

    // Generate initial 7-day quests
    val initialQuests = QuestPlanGenerator.generateWeeklyQuests(created)
    questDao.insertQuests(initialQuests)

    return created
  }

  suspend fun signInPlayer(email: String, password: String): Result<PlayerEntity> {
    val existing = playerDao.getPlayerByEmail(email.trim())
    return if (existing != null && existing.passwordHash == password) {
      playerDao.clearActiveSessions()
      playerDao.setActiveSession(existing.id)
      Result.success(existing.copy(isActiveSession = true))
    } else {
      Result.failure(Exception("Invalid hunter credentials"))
    }
  }

  suspend fun signOut() {
    playerDao.clearActiveSessions()
  }

  suspend fun updatePlayerProfile(player: PlayerEntity) {
    playerDao.updatePlayer(player)
  }

  suspend fun finishOnboarding(updatedPlayer: PlayerEntity) {
    val playerWithOnboarding = updatedPlayer.copy(
      hasCompletedOnboarding = true,
      currentRank = "E Rank"
    )
    playerDao.updatePlayer(playerWithOnboarding)

    // Regenerate quests based on user's selected preferences (Location, Goal, Duration)
    questDao.deleteQuestsForUser(playerWithOnboarding.id)
    val customizedQuests = QuestPlanGenerator.generateWeeklyQuests(playerWithOnboarding)
    questDao.insertQuests(customizedQuests)
  }

  fun getQuestsForDay(userId: Long, dayOfWeek: Int): Flow<List<DailyQuestEntity>> {
    return questDao.getQuestsForUserAndDay(userId, dayOfWeek)
  }

  fun getAllQuestsForUser(userId: Long): Flow<List<DailyQuestEntity>> {
    return questDao.getAllQuestsForUser(userId)
  }

  suspend fun updateQuestProgress(id: Long, progress: Int, isCompleted: Boolean) {
    questDao.updateQuestProgress(id, progress, isCompleted)
  }

  suspend fun updateQuestTarget(quest: DailyQuestEntity, newTarget: Int) {
    questDao.updateQuest(quest.copy(targetValue = newTarget))
  }

  fun getWaterLog(userId: Long, dateString: String): Flow<WaterLogEntity?> {
    return waterDao.getWaterLog(userId, dateString)
  }

  suspend fun addWater(userId: Long, dateString: String, deltaMl: Int, targetMl: Int) {
    val current = waterDao.getWaterLogSync(userId, dateString)
    if (current == null) {
      waterDao.insertWaterLog(
        WaterLogEntity(
          userId = userId,
          dateString = dateString,
          amountMl = deltaMl.coerceAtLeast(0),
          targetMl = targetMl
        )
      )
    } else {
      val newAmount = (current.amountMl + deltaMl).coerceAtLeast(0)
      waterDao.updateWaterLog(current.copy(amountMl = newAmount))
    }
  }

  suspend fun setWaterTarget(userId: Long, dateString: String, newTargetMl: Int) {
    val current = waterDao.getWaterLogSync(userId, dateString)
    if (current != null) {
      waterDao.updateWaterLog(current.copy(targetMl = newTargetMl))
    } else {
      waterDao.insertWaterLog(
        WaterLogEntity(
          userId = userId,
          dateString = dateString,
          amountMl = 0,
          targetMl = newTargetMl
        )
      )
    }
  }

  suspend fun resetWater(userId: Long, dateString: String) {
    val current = waterDao.getWaterLogSync(userId, dateString)
    if (current != null) {
      waterDao.updateWaterLog(current.copy(amountMl = 0))
    }
  }

  fun getWorkoutLogs(userId: Long): Flow<List<WorkoutLogEntity>> {
    return workoutDao.getWorkoutLogs(userId)
  }

  suspend fun logWorkoutCompleted(
    player: PlayerEntity,
    dayOfWeek: Int,
    durationMinutes: Int,
    caloriesBurned: Int
  ) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val today = dateFormat.format(Date())
    workoutDao.insertWorkoutLog(
      WorkoutLogEntity(
        userId = player.id,
        dayOfWeek = dayOfWeek,
        dateCompleted = today,
        durationMinutes = durationMinutes,
        caloriesBurned = caloriesBurned,
        xpEarned = 100
      )
    )

    // Update Player XP and rank progress
    val newXp = player.xp + 100
    val updatedRank = when {
      newXp >= 1000 -> "S Rank"
      newXp >= 600 -> "A Rank"
      newXp >= 400 -> "B Rank"
      newXp >= 200 -> "C Rank"
      newXp >= 100 -> "D Rank"
      else -> "E Rank"
    }
    playerDao.updatePlayer(player.copy(xp = newXp, currentRank = updatedRank))
  }
}
