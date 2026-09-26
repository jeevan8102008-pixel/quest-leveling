package com.example.ui.screens.goal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.PlayerEntity
import com.example.data.entity.WorkoutLogEntity
import com.example.ui.components.GlowingDivider
import com.example.ui.components.LuminousCard
import com.example.ui.components.QuestProgressBar
import com.example.ui.components.RankBadge
import com.example.ui.components.SoloButton
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardHover
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueDark
import com.example.ui.theme.ElectricBlueNeon
import com.example.ui.theme.LuminousDivider
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WarningAmber

@Composable
fun GoalScreen(
  player: PlayerEntity,
  workoutLogs: List<WorkoutLogEntity>,
  onUpdateGoal: (String) -> Unit
) {
  var isEditingGoal by remember { mutableStateOf(false) }

  val goals = listOf(
    "Build muscle",
    "Get lean",
    "Become stronger",
    "Lose fat",
    "Broad and strong",
    "Aesthetic strength"
  )

  // Progression metrics: 30 completed workouts = 100% of baseline phase
  val completedCount = workoutLogs.size
  val targetWorkouts = 30
  val goalProgressRatio = (completedCount.toFloat() / targetWorkouts.toFloat()).coerceIn(0f, 1f)

  // Estimated days to goal calculation: requires at least 2 completed workouts to extrapolate
  val hasEnoughData = completedCount >= 2
  val estimatedDaysRemaining = if (hasEnoughData) {
    val remainingWorkouts = (targetWorkouts - completedCount).coerceAtLeast(0)
    // Assume 4-5 workouts per week average cadence
    (remainingWorkouts * 1.75).toInt().coerceAtLeast(3)
  } else null

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(DarkNavyBg)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp, vertical = 20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .widthIn(max = 680.dp)
          .fillMaxWidth()
      ) {
        Column {

          // Header
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "PLAYER GOAL & EVOLUTION",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                color = TextWhite,
                letterSpacing = 1.5.sp
              )
              Text(
                text = "Tracking Systematic Overload & Conditioning Targets",
                style = MaterialTheme.typography.bodySmall,
                color = ElectricBlueNeon
              )
            }

            RankBadge(rank = player.currentRank)
          }

          Spacer(modifier = Modifier.height(16.dp))
          GlowingDivider()
          Spacer(modifier = Modifier.height(16.dp))

          // Current Goal Card
          LuminousCard(
            modifier = Modifier.fillMaxWidth(),
            borderColor = ElectricBlueNeon
          ) {
            Column(modifier = Modifier.padding(20.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = "ACTIVE PROTOCOL GOAL",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElectricBlueNeon,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = player.fitnessGoal,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = TextWhite
                  )
                }

                SoloButton(
                  text = if (isEditingGoal) "Cancel" else "Change Goal",
                  onClick = { isEditingGoal = !isEditingGoal },
                  isSecondary = true,
                  modifier = Modifier.height(36.dp),
                  testTag = "change_goal_button"
                )
              }

              // Change Goal Selection List
              if (isEditingGoal) {
                Spacer(modifier = Modifier.height(16.dp))
                GlowingDivider(modifier = Modifier.padding(bottom = 12.dp))
                Text(
                  text = "Select New Protocol Direction:",
                  style = MaterialTheme.typography.bodyMedium,
                  color = TextWhite,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(bottom = 8.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                  goals.forEach { g ->
                    Box(
                      modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (player.fitnessGoal == g) DeepNavyCardHover else DarkNavyBg)
                        .border(
                          1.dp,
                          if (player.fitnessGoal == g) ElectricBlueNeon else LuminousDivider,
                          RoundedCornerShape(6.dp)
                        )
                        .clickable {
                          onUpdateGoal(g)
                          isEditingGoal = false
                        }
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                      Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                      ) {
                        Text(
                          text = g,
                          color = if (player.fitnessGoal == g) ElectricBlueNeon else TextWhite,
                          fontWeight = FontWeight.Bold,
                          fontSize = 13.sp
                        )
                        if (player.fitnessGoal == g) {
                          Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = ElectricBlueNeon,
                            modifier = Modifier.size(18.dp)
                          )
                        }
                      }
                    }
                  }
                }
              }

              Spacer(modifier = Modifier.height(20.dp))

              // Goal Progress Percentage Ring & Stats
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Box(modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center) {
                  CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.size(100.dp),
                    color = DarkNavyBg,
                    strokeWidth = 8.dp
                  )
                  CircularProgressIndicator(
                    progress = { goalProgressRatio },
                    modifier = Modifier.size(100.dp),
                    color = ElectricBlueNeon,
                    trackColor = ElectricBlueDark.copy(alpha = 0.3f),
                    strokeWidth = 8.dp
                  )
                  Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                      text = "${(goalProgressRatio * 100).toInt()}%",
                      style = MaterialTheme.typography.titleLarge,
                      fontWeight = FontWeight.Black,
                      color = TextWhite
                    )
                    Text(
                      text = "REACHED",
                      fontSize = 8.sp,
                      color = ElectricBlueNeon,
                      fontWeight = FontWeight.Bold
                    )
                  }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "Sessions Logged: $completedCount of $targetWorkouts",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = "Every completed daily quest advances your metabolic adaptation and muscular endurance.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    lineHeight = 16.sp
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // ESTIMATED DAYS TO GOAL CARD
          LuminousCard(
            modifier = Modifier.fillMaxWidth(),
            borderColor = LuminousDivider
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.TrendingUp,
                  contentDescription = "Trend",
                  tint = ElectricBlueNeon,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = "ESTIMATED DAYS TO GOAL",
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = TextWhite
                )
              }

              Spacer(modifier = Modifier.height(10.dp))

              if (hasEnoughData && estimatedDaysRemaining != null) {
                Row(verticalAlignment = Alignment.Bottom) {
                  Text(
                    text = "~$estimatedDaysRemaining",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Black,
                    color = ElectricBlueNeon
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "DAYS REMAINING (ESTIMATE)",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted,
                    modifier = Modifier.padding(bottom = 6.dp)
                  )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                  text = "Calculated from your logged training cadence. Individual biological outcomes vary based on nutrition, recovery quality, and consistency.",
                  style = MaterialTheme.typography.bodySmall,
                  color = TextMuted,
                  fontSize = 11.sp,
                  lineHeight = 16.sp
                )
              } else {
                Text(
                  text = "Awaiting sufficient training telemetry. Complete at least 2 daily workout sessions to calculate your projected timeline.",
                  style = MaterialTheme.typography.bodyMedium,
                  color = TextMuted,
                  lineHeight = 18.sp
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // WEEKLY CONSISTENCY & RECENT PROGRESS
          LuminousCard(
            modifier = Modifier.fillMaxWidth(),
            borderColor = LuminousDivider
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Text(
                text = "WEEKLY CONSISTENCY HEAT MAP",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TextWhite
              )
              Text(
                text = "7-Day Active Quest Frequency",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                modifier = Modifier.padding(bottom = 12.dp)
              )

              // 7 Day Dots
              val dayLabels = listOf("M", "T", "W", "T", "F", "S", "S")
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                dayLabels.forEachIndexed { idx, label ->
                  val isDayComplete = workoutLogs.any { it.dayOfWeek == idx + 1 }
                  Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                      modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isDayComplete) SuccessGreen else DeepNavyCardHover)
                        .border(
                          1.dp,
                          if (isDayComplete) SuccessGreen else LuminousDivider,
                          RoundedCornerShape(8.dp)
                        ),
                      contentAlignment = Alignment.Center
                    ) {
                      if (isDayComplete) {
                        Icon(
                          imageVector = Icons.Default.Check,
                          contentDescription = "Active",
                          tint = DarkNavyBg,
                          modifier = Modifier.size(18.dp)
                        )
                      }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = label, color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(30.dp))
        }
      }
    }
  }
}
