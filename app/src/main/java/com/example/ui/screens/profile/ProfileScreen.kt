package com.example.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.entity.DailyQuestEntity
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
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.LuminousDivider
import com.example.ui.theme.RankEColor
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WarningAmber

@Composable
fun ProfileScreen(
  player: PlayerEntity,
  workoutLogs: List<WorkoutLogEntity>,
  allQuests: List<DailyQuestEntity>,
  missedQuestDismissed: Boolean,
  onUpdateProfile: (PlayerEntity) -> Unit,
  onRescheduleMissedQuest: (day: Int) -> Unit,
  onDismissMissedQuest: () -> Unit,
  onSignOut: () -> Unit
) {
  var isEditDialogOpen by remember { mutableStateOf(false) }

  // Activity Stats Calculation
  val workoutsCompleted = workoutLogs.size
  val activeWorkoutDays = workoutLogs.map { it.dateCompleted }.distinct().size
  val totalWorkoutMinutes = workoutLogs.sumOf { it.durationMinutes }
  val totalCaloriesBurned = workoutLogs.sumOf { it.caloriesBurned }
  val hours = totalWorkoutMinutes / 60
  val minutes = totalWorkoutMinutes % 60

  // Goal metrics
  val targetWorkouts = 30
  val goalProgressRatio = (workoutsCompleted.toFloat() / targetWorkouts.toFloat()).coerceIn(0f, 1f)
  val hasEnoughData = workoutsCompleted >= 2
  val estimatedDaysRemaining = if (hasEnoughData) {
    val remaining = (targetWorkouts - workoutsCompleted).coerceAtLeast(0)
    (remaining * 1.75).toInt().coerceAtLeast(3)
  } else null

  // Check for missed quests in past days (e.g. days with incomplete quests)
  val hasIncompleteDays = allQuests.groupBy { it.dayOfWeek }.any { (_, quests) ->
    quests.any { !it.isCompleted }
  }

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

          // PLAYER IDENTITY CARD
          LuminousCard(
            modifier = Modifier.fillMaxWidth(),
            borderColor = ElectricBlueNeon
          ) {
            Column(modifier = Modifier.padding(20.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(68.dp)
                      .clip(CircleShape)
                      .border(2.dp, ElectricBlueNeon, CircleShape)
                      .background(DarkNavyBg),
                    contentAlignment = Alignment.Center
                  ) {
                    Image(
                      painter = painterResource(id = R.drawable.img_player_avatar),
                      contentDescription = "Player Portrait",
                      modifier = Modifier.fillMaxSize(),
                      contentScale = ContentScale.Crop
                    )
                  }

                  Spacer(modifier = Modifier.width(16.dp))

                  Column {
                    Text(
                      text = player.name,
                      style = MaterialTheme.typography.titleLarge,
                      fontWeight = FontWeight.Black,
                      color = TextWhite
                    )
                    Text(
                      text = "Role: ${player.role} • ${player.gender}",
                      style = MaterialTheme.typography.bodySmall,
                      color = ElectricBlueNeon
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    RankBadge(rank = player.currentRank)
                  }
                }

                IconButton(
                  onClick = { isEditDialogOpen = true },
                  modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(DarkNavyBg)
                    .border(1.dp, ElectricBlueDark, CircleShape)
                    .testTag("edit_profile_button")
                ) {
                  Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = ElectricBlueNeon,
                    modifier = Modifier.size(18.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(16.dp))
              GlowingDivider()
              Spacer(modifier = Modifier.height(14.dp))

              // Player Stats Matrix
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                StatColumn(label = "AGE", value = "${player.age}")
                StatColumn(label = "BODY TYPE", value = player.bodyType)
                StatColumn(label = "HEIGHT", value = "${player.heightCm.toInt()} cm")
                StatColumn(label = "WEIGHT", value = "${player.weightKg.toInt()} kg")
              }

              Spacer(modifier = Modifier.height(12.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                StatColumn(label = "LOCATION", value = player.trainingLocation)
                StatColumn(label = "SCHEDULE", value = player.workoutTimePref)
                StatColumn(label = "SESSION", value = "${player.sessionDurationMinutes}m")
                StatColumn(label = "START TIME", value = player.preferredStartTime)
              }
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // ACTIVITY STATS GRID (Mandatory Activity Stats)
          Text(
            text = "SYSTEM ACTIVITY TELEMETRY",
            style = MaterialTheme.typography.labelSmall,
            color = ElectricBlueNeon,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            ActivityCard(
              title = "Workouts Completed",
              value = "$workoutsCompleted",
              icon = Icons.Default.FitnessCenter,
              modifier = Modifier.weight(1f)
            )

            ActivityCard(
              title = "Active Workout Days",
              value = "$activeWorkoutDays",
              icon = Icons.Default.Schedule,
              modifier = Modifier.weight(1f)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            ActivityCard(
              title = "Estimated Calories",
              value = if (totalCaloriesBurned > 0) "$totalCaloriesBurned kcal" else "0 kcal",
              subtitle = "(estimate)",
              icon = Icons.Default.LocalFireDepartment,
              modifier = Modifier.weight(1f)
            )

            ActivityCard(
              title = "Total Workout Time",
              value = if (hours > 0) "${hours}h ${minutes}m" else "${minutes}m",
              icon = Icons.Default.Star,
              modifier = Modifier.weight(1f)
            )
          }

          Spacer(modifier = Modifier.height(20.dp))

          // GOAL PROGRESS & DAYS TO GOAL
          LuminousCard(modifier = Modifier.fillMaxWidth()) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                  progress = { 1f },
                  modifier = Modifier.size(80.dp),
                  color = DarkNavyBg,
                  strokeWidth = 7.dp
                )
                CircularProgressIndicator(
                  progress = { goalProgressRatio },
                  modifier = Modifier.size(80.dp),
                  color = ElectricBlueNeon,
                  strokeWidth = 7.dp
                )
                Text(
                  text = "${(goalProgressRatio * 100).toInt()}%",
                  fontWeight = FontWeight.Black,
                  fontSize = 14.sp,
                  color = TextWhite
                )
              }

              Spacer(modifier = Modifier.width(16.dp))

              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "Goal: ${player.fitnessGoal}",
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = TextWhite
                )
                Spacer(modifier = Modifier.height(2.dp))
                if (hasEnoughData && estimatedDaysRemaining != null) {
                  Text(
                    text = "Estimated Days Remaining: ~$estimatedDaysRemaining days",
                    style = MaterialTheme.typography.bodySmall,
                    color = ElectricBlueNeon,
                    fontWeight = FontWeight.Bold
                  )
                  Text(
                    text = "Based on activity cadence; results vary biologically.",
                    fontSize = 10.sp,
                    color = TextMuted
                  )
                } else {
                  Text(
                    text = "Complete at least 2 workouts to calculate days to goal estimate.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          // MISSED QUESTS & RECOVERY SUGGESTIONS SECTION
          if (!missedQuestDismissed && hasIncompleteDays) {
            LuminousCard(
              modifier = Modifier.fillMaxWidth(),
              borderColor = WarningAmber.copy(alpha = 0.8f),
              backgroundColor = DarkNavyBg
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.SelfImprovement,
                      contentDescription = "Mobility",
                      tint = WarningAmber,
                      modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = "RECOVERY & RESCHEDULING SUGGESTIONS",
                      style = MaterialTheme.typography.labelSmall,
                      color = WarningAmber,
                      fontWeight = FontWeight.Bold,
                      letterSpacing = 0.5.sp
                    )
                  }

                  IconButton(
                    onClick = onDismissMissedQuest,
                    modifier = Modifier.size(28.dp)
                  ) {
                    Icon(Icons.Default.Close, contentDescription = "Dismiss", tint = TextMuted)
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                  text = "You have unscheduled or open daily quests. In this system, there are no punitive penalties. Gentle recovery options are always available:",
                  style = MaterialTheme.typography.bodySmall,
                  color = TextWhite,
                  lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Suggestion 1: Gentle Walk
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .background(DeepNavyCard, RoundedCornerShape(6.dp))
                    .padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DirectionsWalk, contentDescription = "Walk", tint = ElectricBlueNeon)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                      Text("Gentle 15-Minute Recovery Walk", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextWhite)
                      Text("Promotes blood flow and joint mobility", fontSize = 10.sp, color = TextMuted)
                    }
                  }
                  SoloButton(
                    text = "Do Today",
                    onClick = { onRescheduleMissedQuest(4) },
                    modifier = Modifier.height(32.dp),
                    isSecondary = true
                  )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Suggestion 2: Reschedule
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .background(DeepNavyCard, RoundedCornerShape(6.dp))
                    .padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reschedule", tint = ElectricBlueNeon)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                      Text("Reschedule Session to Rest Day", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextWhite)
                      Text("Shift volume smoothly without overload", fontSize = 10.sp, color = TextMuted)
                    }
                  }
                  SoloButton(
                    text = "Reschedule",
                    onClick = { onRescheduleMissedQuest(7) },
                    modifier = Modifier.height(32.dp),
                    isSecondary = true
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(20.dp))
          }

          // Sign Out Button
          SoloButton(
            text = "Sign Out Hunter",
            onClick = onSignOut,
            isSecondary = true,
            modifier = Modifier.fillMaxWidth(),
            testTag = "sign_out_button"
          )

          Spacer(modifier = Modifier.height(32.dp))
        }
      }
    }
  }

  // Edit Profile Dialog
  if (isEditDialogOpen) {
    EditProfileDialog(
      player = player,
      onSave = { updated ->
        onUpdateProfile(updated)
        isEditDialogOpen = false
      },
      onDismiss = { isEditDialogOpen = false }
    )
  }
}

@Composable
fun StatColumn(label: String, value: String) {
  Column {
    Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextMuted, fontSize = 10.sp)
    Text(text = value, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextWhite)
  }
}

@Composable
fun ActivityCard(
  title: String,
  value: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  modifier: Modifier = Modifier,
  subtitle: String? = null
) {
  LuminousCard(modifier = modifier, borderColor = LuminousDivider) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = title, style = MaterialTheme.typography.labelSmall, color = TextMuted, fontSize = 11.sp)
        Icon(imageVector = icon, contentDescription = title, tint = ElectricBlueNeon, modifier = Modifier.size(16.dp))
      }
      Spacer(modifier = Modifier.height(6.dp))
      Row(verticalAlignment = Alignment.Bottom) {
        Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = TextWhite)
        if (subtitle != null) {
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = subtitle, fontSize = 10.sp, color = TextMuted, modifier = Modifier.padding(bottom = 2.dp))
        }
      }
    }
  }
}

@Composable
fun EditProfileDialog(
  player: PlayerEntity,
  onSave: (PlayerEntity) -> Unit,
  onDismiss: () -> Unit
) {
  var name by remember { mutableStateOf(player.name) }
  var role by remember { mutableStateOf(player.role) }
  var location by remember { mutableStateOf(player.trainingLocation) }
  var duration by remember { mutableIntStateOf(player.sessionDurationMinutes) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(12.dp),
      color = DeepNavyCard,
      border = BorderStroke(1.dp, ElectricBlueNeon),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .padding(20.dp)
          .verticalScroll(rememberScrollState())
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "EDIT HUNTER PROFILE",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Black,
            color = TextWhite
          )
          IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("Player Name") },
          modifier = Modifier.fillMaxWidth(),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ElectricBlueNeon,
            unfocusedBorderColor = LuminousDivider,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = role,
          onValueChange = { role = it },
          label = { Text("Role / Occupation") },
          modifier = Modifier.fillMaxWidth(),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ElectricBlueNeon,
            unfocusedBorderColor = LuminousDivider,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Training Location: $location", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          listOf("Gym", "Home").forEach { loc ->
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(if (location == loc) ElectricBlue else DeepNavyCardHover)
                .clickable { location = loc }
                .padding(vertical = 8.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = loc,
                color = if (location == loc) DarkNavyBg else TextWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(text = "Session Duration (Max 3h): ${duration}m", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          listOf(30, 45, 60, 90, 120).forEach { d ->
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(if (duration == d) ElectricBlue else DeepNavyCardHover)
                .clickable { duration = d }
                .padding(vertical = 8.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "${d}m",
                color = if (duration == d) DarkNavyBg else TextWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        SoloButton(
          text = "Save Changes",
          onClick = {
            onSave(
              player.copy(
                name = name.ifBlank { player.name },
                role = role.ifBlank { player.role },
                trainingLocation = location,
                sessionDurationMinutes = duration.coerceIn(15, 180)
              )
            )
          },
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}
