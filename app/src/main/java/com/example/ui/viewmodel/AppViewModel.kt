package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entity.DailyQuestEntity
import com.example.data.entity.PlayerEntity
import com.example.data.entity.WaterLogEntity
import com.example.data.entity.WorkoutLogEntity
import com.example.data.model.Exercise
import com.example.data.model.ExerciseLibrary
import com.example.data.repository.AppRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModel(private val repository: AppRepository) : ViewModel() {

  private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
  val todayDateString: String = dateFormat.format(Date())

  // Default day of week: 1 (Day 1) to 7 (Day 7)
  private val currentCalendarDay: Int = run {
    val cal = Calendar.getInstance()
    when (cal.get(Calendar.DAY_OF_WEEK)) {
      Calendar.MONDAY -> 1
      Calendar.TUESDAY -> 2
      Calendar.WEDNESDAY -> 3
      Calendar.THURSDAY -> 4
      Calendar.FRIDAY -> 5
      Calendar.SATURDAY -> 6
      Calendar.SUNDAY -> 7
      else -> 1
    }
  }

  val activePlayer: StateFlow<PlayerEntity?> = repository.activePlayer
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

  private val _selectedDay = MutableStateFlow(currentCalendarDay)
  val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

  val allQuests: StateFlow<List<DailyQuestEntity>> = activePlayer
    .flatMapLatest { player ->
      if (player != null) repository.getAllQuestsForUser(player.id)
      else flowOf(emptyList())
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val currentDayQuests: StateFlow<List<DailyQuestEntity>> = combine(
    activePlayer,
    _selectedDay
  ) { player, day ->
    Pair(player, day)
  }.flatMapLatest { (player, day) ->
    if (player != null) repository.getQuestsForDay(player.id, day)
    else flowOf(emptyList())
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val waterLog: StateFlow<WaterLogEntity?> = activePlayer
    .flatMapLatest { player ->
      if (player != null) repository.getWaterLog(player.id, todayDateString)
      else flowOf(null)
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

  val workoutLogs: StateFlow<List<WorkoutLogEntity>> = activePlayer
    .flatMapLatest { player ->
      if (player != null) repository.getWorkoutLogs(player.id)
      else flowOf(emptyList())
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Today completion percentage
  val todayCompletionRatio: StateFlow<Float> = currentDayQuests
    .combine(_selectedDay) { quests, _ ->
      if (quests.isEmpty()) 0f
      else {
        val completed = quests.count { it.isCompleted }
        completed.toFloat() / quests.size.toFloat()
      }
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

  // UI state overlays
  private val _selectedExercise = MutableStateFlow<Exercise?>(null)
  val selectedExercise: StateFlow<Exercise?> = _selectedExercise.asStateFlow()

  private val _isWaterDialogVisible = MutableStateFlow(false)
  val isWaterDialogVisible: StateFlow<Boolean> = _isWaterDialogVisible.asStateFlow()

  private val _isEditProfileVisible = MutableStateFlow(false)
  val isEditProfileVisible: StateFlow<Boolean> = _isEditProfileVisible.asStateFlow()

  private val _isCompletionSummaryVisible = MutableStateFlow(false)
  val isCompletionSummaryVisible: StateFlow<Boolean> = _isCompletionSummaryVisible.asStateFlow()

  private val _authError = MutableStateFlow<String?>(null)
  val authError: StateFlow<String?> = _authError.asStateFlow()

  private val _missedQuestDismissed = MutableStateFlow(false)
  val missedQuestDismissed: StateFlow<Boolean> = _missedQuestDismissed.asStateFlow()

  fun selectDay(day: Int) {
    _selectedDay.value = day
  }

  fun setExerciseDetails(exercise: Exercise?) {
    _selectedExercise.value = exercise
  }

  fun setExerciseDetailsByKey(key: String) {
    _selectedExercise.value = ExerciseLibrary.getById(key)
  }

  fun setWaterDialogVisible(visible: Boolean) {
    _isWaterDialogVisible.value = visible
  }

  fun setEditProfileVisible(visible: Boolean) {
    _isEditProfileVisible.value = visible
  }

  fun setCompletionSummaryVisible(visible: Boolean) {
    _isCompletionSummaryVisible.value = visible
  }

  fun dismissMissedQuestNotice() {
    _missedQuestDismissed.value = true
  }

  fun clearAuthError() {
    _authError.value = null
  }

  // Auth Operations
  fun register(email: String, pass: String, name: String) {
    viewModelScope.launch {
      try {
        if (email.isBlank() || pass.isBlank()) {
          _authError.value = "Please provide email and password"
          return@launch
        }
        repository.registerPlayer(email, pass, name)
        _authError.value = null
      } catch (e: Exception) {
        _authError.value = e.message ?: "Registration failed"
      }
    }
  }

  fun signIn(email: String, pass: String) {
    viewModelScope.launch {
      try {
        val result = repository.signInPlayer(email, pass)
        if (result.isSuccess) {
          _authError.value = null
        } else {
          _authError.value = "Invalid credentials. Try again or register."
        }
      } catch (e: Exception) {
        _authError.value = e.message ?: "Sign in failed"
      }
    }
  }

  fun signOut() {
    viewModelScope.launch {
      repository.signOut()
    }
  }

  // Onboarding Completion
  fun completeOnboarding(player: PlayerEntity) {
    viewModelScope.launch {
      repository.finishOnboarding(player)
    }
  }

  // Quest Actions
  fun toggleQuest(quest: DailyQuestEntity) {
    viewModelScope.launch {
      val newCompleted = !quest.isCompleted
      val newProgress = if (newCompleted) quest.targetValue else 0
      repository.updateQuestProgress(quest.id, newProgress, newCompleted)

      // Check if all quests in current day are now complete
      val currentQuests = currentDayQuests.value
      val otherQuestsCompleted = currentQuests.filter { it.id != quest.id }.all { it.isCompleted }
      if (newCompleted && otherQuestsCompleted) {
        _isCompletionSummaryVisible.value = true
        val player = activePlayer.value
        if (player != null) {
          repository.logWorkoutCompleted(
            player = player,
            dayOfWeek = quest.dayOfWeek,
            durationMinutes = player.sessionDurationMinutes,
            caloriesBurned = player.sessionDurationMinutes * 8
          )
        }
      }
    }
  }

  fun updateQuestProgress(quest: DailyQuestEntity, newProgress: Int) {
    viewModelScope.launch {
      val clampedProgress = newProgress.coerceIn(0, quest.targetValue)
      val isCompleted = clampedProgress >= quest.targetValue
      repository.updateQuestProgress(quest.id, clampedProgress, isCompleted)

      if (isCompleted && !quest.isCompleted) {
        val currentQuests = currentDayQuests.value
        val otherQuestsCompleted = currentQuests.filter { it.id != quest.id }.all { it.isCompleted }
        if (otherQuestsCompleted) {
          _isCompletionSummaryVisible.value = true
          val player = activePlayer.value
          if (player != null) {
            repository.logWorkoutCompleted(
              player = player,
              dayOfWeek = quest.dayOfWeek,
              durationMinutes = player.sessionDurationMinutes,
              caloriesBurned = player.sessionDurationMinutes * 8
            )
          }
        }
      }
    }
  }

  fun updateQuestTarget(quest: DailyQuestEntity, newTarget: Int) {
    viewModelScope.launch {
      repository.updateQuestTarget(quest, newTarget)
    }
  }

  // Water Tracker
  fun addWater(deltaMl: Int) {
    val player = activePlayer.value ?: return
    viewModelScope.launch {
      repository.addWater(player.id, todayDateString, deltaMl, player.waterTargetMl)
    }
  }

  fun resetWater() {
    val player = activePlayer.value ?: return
    viewModelScope.launch {
      repository.resetWater(player.id, todayDateString)
    }
  }

  fun updateWaterTarget(newTargetMl: Int) {
    val player = activePlayer.value ?: return
    viewModelScope.launch {
      repository.setWaterTarget(player.id, todayDateString, newTargetMl)
      repository.updatePlayerProfile(player.copy(waterTargetMl = newTargetMl))
    }
  }

  // Profile Edit
  fun updateProfile(updated: PlayerEntity) {
    viewModelScope.launch {
      repository.updatePlayerProfile(updated)
      _isEditProfileVisible.value = false
    }
  }

  // Update Fitness Goal
  fun updateGoal(newGoal: String) {
    val player = activePlayer.value ?: return
    viewModelScope.launch {
      repository.updatePlayerProfile(player.copy(fitnessGoal = newGoal))
    }
  }

  // Reschedule missed quest to tomorrow or today
  fun rescheduleMissedQuest(dayOfWeek: Int) {
    selectDay(dayOfWeek)
    _missedQuestDismissed.value = true
  }
}
