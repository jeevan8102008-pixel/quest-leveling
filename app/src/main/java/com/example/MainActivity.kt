package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.database.AppDatabase
import com.example.data.repository.AppRepository
import com.example.ui.navigation.SoloBottomNav
import com.example.ui.navigation.SoloNavDestination
import com.example.ui.screens.auth.AuthScreen
import com.example.ui.screens.dailyquest.DailyCompletionDialog
import com.example.ui.screens.dailyquest.DailyQuestScreen
import com.example.ui.screens.goal.GoalScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.onboarding.OnboardingScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.water.WaterTrackerDialog
import com.example.ui.screens.workouts.WorkoutsScreen
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      MyApplicationTheme {
        val context = LocalContext.current
        val database = remember { AppDatabase.getDatabase(context) }
        val repository = remember {
          AppRepository(
            playerDao = database.playerDao(),
            questDao = database.questDao(),
            waterDao = database.waterDao(),
            workoutDao = database.workoutDao()
          )
        }
        val appViewModel: AppViewModel = viewModel { AppViewModel(repository) }

        SoloFitnessApp(viewModel = appViewModel)
      }
    }
  }
}

@Composable
fun SoloFitnessApp(viewModel: AppViewModel) {
  val player by viewModel.activePlayer.collectAsStateWithLifecycle()
  val selectedDay by viewModel.selectedDay.collectAsStateWithLifecycle()
  val allQuests by viewModel.allQuests.collectAsStateWithLifecycle()
  val currentDayQuests by viewModel.currentDayQuests.collectAsStateWithLifecycle()
  val waterLog by viewModel.waterLog.collectAsStateWithLifecycle()
  val workoutLogs by viewModel.workoutLogs.collectAsStateWithLifecycle()
  val todayCompletionRatio by viewModel.todayCompletionRatio.collectAsStateWithLifecycle()
  val authError by viewModel.authError.collectAsStateWithLifecycle()
  val isWaterDialogVisible by viewModel.isWaterDialogVisible.collectAsStateWithLifecycle()
  val isCompletionSummaryVisible by viewModel.isCompletionSummaryVisible.collectAsStateWithLifecycle()
  val missedQuestDismissed by viewModel.missedQuestDismissed.collectAsStateWithLifecycle()

  var currentNavDestination by remember { mutableStateOf(SoloNavDestination.HOME) }
  var isDailyQuestViewOpen by remember { mutableStateOf(false) }

  // Flow Routing:
  // 1. Unauthenticated -> AuthScreen
  // 2. First Visit -> OnboardingScreen
  // 3. Returning Player -> Main Navigation (Home, Workouts, Goal, Profile, DailyQuest)

  val activePlayer = player
  if (activePlayer == null) {
    AuthScreen(
      errorMessage = authError,
      onSignIn = { email, pass -> viewModel.signIn(email, pass) },
      onRegister = { email, pass, name -> viewModel.register(email, pass, name) },
      onClearError = { viewModel.clearAuthError() }
    )
  } else if (!activePlayer.hasCompletedOnboarding) {
    OnboardingScreen(
      initialPlayer = activePlayer,
      onComplete = { completedPlayer ->
        viewModel.completeOnboarding(completedPlayer)
      }
    )
  } else {
    // Handling Back Navigation cleanly
    if (isDailyQuestViewOpen) {
      BackHandler {
        isDailyQuestViewOpen = false
      }
    } else if (currentNavDestination != SoloNavDestination.HOME) {
      BackHandler {
        currentNavDestination = SoloNavDestination.HOME
      }
    }

    Scaffold(
      modifier = Modifier
        .fillMaxSize()
        .background(DarkNavyBg),
      containerColor = DarkNavyBg,
      contentWindowInsets = WindowInsets.safeDrawing,
      bottomBar = {
        if (!isDailyQuestViewOpen) {
          SoloBottomNav(
            currentDestination = currentNavDestination,
            onNavigate = { destination ->
              currentNavDestination = destination
            }
          )
        }
      }
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        if (isDailyQuestViewOpen) {
          DailyQuestScreen(
            player = activePlayer,
            dayOfWeek = selectedDay,
            quests = currentDayQuests,
            completionRate = todayCompletionRatio,
            onToggleQuest = { quest -> viewModel.toggleQuest(quest) },
            onUpdateProgress = { quest, progress -> viewModel.updateQuestProgress(quest, progress) },
            onUpdateTarget = { quest, target -> viewModel.updateQuestTarget(quest, target) },
            onOpenWaterTracker = { viewModel.setWaterDialogVisible(true) },
            onBack = { isDailyQuestViewOpen = false },
            onFinishDay = {
              isDailyQuestViewOpen = false
              viewModel.setCompletionSummaryVisible(true)
            }
          )
        } else {
          when (currentNavDestination) {
            SoloNavDestination.HOME -> {
              HomeScreen(
                player = activePlayer,
                todayDayOfWeek = selectedDay,
                allQuests = allQuests,
                todayCompletionRate = todayCompletionRatio,
                onSelectDay = { day ->
                  viewModel.selectDay(day)
                  isDailyQuestViewOpen = true
                },
                onOpenWaterTracker = { viewModel.setWaterDialogVisible(true) }
              )
            }
            SoloNavDestination.WORKOUTS -> {
              WorkoutsScreen()
            }
            SoloNavDestination.GOAL -> {
              GoalScreen(
                player = activePlayer,
                workoutLogs = workoutLogs,
                onUpdateGoal = { newGoal -> viewModel.updateGoal(newGoal) }
              )
            }
            SoloNavDestination.PROFILE -> {
              ProfileScreen(
                player = activePlayer,
                workoutLogs = workoutLogs,
                allQuests = allQuests,
                missedQuestDismissed = missedQuestDismissed,
                onUpdateProfile = { updated -> viewModel.updateProfile(updated) },
                onRescheduleMissedQuest = { day ->
                  viewModel.rescheduleMissedQuest(day)
                  isDailyQuestViewOpen = true
                },
                onDismissMissedQuest = { viewModel.dismissMissedQuestNotice() },
                onSignOut = { viewModel.signOut() }
              )
            }
          }
        }

        // Water Tracker Modal Dialog
        if (isWaterDialogVisible) {
          WaterTrackerDialog(
            waterLog = waterLog,
            defaultTarget = activePlayer.waterTargetMl,
            onAddWater = { amount -> viewModel.addWater(amount) },
            onResetWater = { viewModel.resetWater() },
            onUpdateTarget = { newTarget -> viewModel.updateWaterTarget(newTarget) },
            onDismiss = { viewModel.setWaterDialogVisible(false) }
          )
        }

        // Daily Completion Summary Dialog
        if (isCompletionSummaryVisible) {
          DailyCompletionDialog(
            dayOfWeek = selectedDay,
            durationMinutes = activePlayer.sessionDurationMinutes,
            caloriesBurned = activePlayer.sessionDurationMinutes * 8,
            onDismiss = { viewModel.setCompletionSummaryVisible(false) }
          )
        }
      }
    }
  }
}
