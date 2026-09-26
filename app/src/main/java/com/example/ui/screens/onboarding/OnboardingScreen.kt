package com.example.ui.screens.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.PlayerEntity
import com.example.ui.components.GlowingDivider
import com.example.ui.components.LuminousCard
import com.example.ui.components.QuestProgressBar
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
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WarningAmber

@Composable
fun OnboardingScreen(
  initialPlayer: PlayerEntity,
  onComplete: (PlayerEntity) -> Unit
) {
  var currentStep by remember { mutableIntStateOf(1) }
  val totalSteps = 10

  // Form State
  var name by remember { mutableStateOf(initialPlayer.name.ifBlank { "Hunter" }) }
  var gender by remember { mutableStateOf(initialPlayer.gender) }
  var bodyType by remember { mutableStateOf(initialPlayer.bodyType) }
  var ageInput by remember { mutableStateOf(initialPlayer.age.toString()) }
  var ageError by remember { mutableStateOf<String?>(null) }
  var heightCm by remember { mutableFloatStateOf(initialPlayer.heightCm) }
  var weightKg by remember { mutableFloatStateOf(initialPlayer.weightKg) }
  var role by remember { mutableStateOf(initialPlayer.role) }
  var workoutTimePref by remember { mutableStateOf(initialPlayer.workoutTimePref) }
  var preferredStartTime by remember { mutableStateOf(initialPlayer.preferredStartTime) }
  var sessionDurationMinutes by remember { mutableIntStateOf(initialPlayer.sessionDurationMinutes) }
  var trainingLocation by remember { mutableStateOf(initialPlayer.trainingLocation) }
  var fitnessGoal by remember { mutableStateOf(initialPlayer.fitnessGoal) }

  val progress = currentStep.toFloat() / totalSteps.toFloat()

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(DarkNavyBg),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .widthIn(max = 680.dp)
          .fillMaxWidth()
      ) {
        Column {
          // Top HUD Tracker
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "PLAYER CALIBRATION",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = ElectricBlueNeon,
                letterSpacing = 1.5.sp
              )
              Text(
                text = "Phase $currentStep of $totalSteps",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
              )
            }

            Surface(
              shape = RoundedCornerShape(4.dp),
              color = RankEColor.copy(alpha = 0.15f),
              border = BorderStroke(1.dp, RankEColor)
            ) {
              Text(
                text = "INITIAL RANK: E",
                color = RankEColor,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                letterSpacing = 1.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))
          QuestProgressBar(progress = progress, height = 4.dp)
          Spacer(modifier = Modifier.height(24.dp))

          // Question Card
          LuminousCard(
            modifier = Modifier.fillMaxWidth(),
            borderColor = ElectricBlueDark
          ) {
            BoxWithConstraints(modifier = Modifier.padding(24.dp)) {
              val isWideScreen = maxWidth > 500.dp

              AnimatedContent(
                targetState = currentStep,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "onboarding_steps"
              ) { step ->
                when (step) {
                  1 -> Step1PlayerName(name = name, onNameChange = { name = it }, isWide = isWideScreen)
                  2 -> Step2Gender(selected = gender, onSelect = { gender = it }, isWide = isWideScreen)
                  3 -> Step3BodyType(selected = bodyType, onSelect = { bodyType = it }, isWide = isWideScreen)
                  4 -> Step4Age(
                    age = ageInput,
                    errorMessage = ageError,
                    onAgeChange = {
                      ageInput = it
                      ageError = null
                    },
                    isWide = isWideScreen
                  )
                  5 -> Step5HeightWeight(
                    height = heightCm,
                    weight = weightKg,
                    onHeightChange = { heightCm = it },
                    onWeightChange = { weightKg = it },
                    isWide = isWideScreen
                  )
                  6 -> Step6Role(selected = role, onSelect = { role = it }, isWide = isWideScreen)
                  7 -> Step7WorkoutTimePref(selected = workoutTimePref, onSelect = { workoutTimePref = it }, isWide = isWideScreen)
                  8 -> Step8DurationAndTime(
                    startTime = preferredStartTime,
                    duration = sessionDurationMinutes,
                    onTimeChange = { preferredStartTime = it },
                    onDurationChange = { sessionDurationMinutes = it },
                    isWide = isWideScreen
                  )
                  9 -> Step9Location(selected = trainingLocation, onSelect = { trainingLocation = it }, isWide = isWideScreen)
                  10 -> Step10Goal(selected = fitnessGoal, onSelect = { fitnessGoal = it }, isWide = isWideScreen)
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          // Navigation Controls (Back & Continue)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            if (currentStep > 1) {
              SoloButton(
                text = "Back",
                onClick = { currentStep-- },
                isSecondary = true,
                modifier = Modifier.width(120.dp),
                testTag = "onboarding_back_button"
              )
            } else {
              Spacer(modifier = Modifier.width(120.dp))
            }

            SoloButton(
              text = if (currentStep == totalSteps) "Awaken Player" else "Continue",
              onClick = {
                if (currentStep == 4) {
                  val parsedAge = ageInput.toIntOrNull() ?: 0
                  if (parsedAge <= 14) {
                    ageError = "These workout plans are intended for users over 14. To protect adolescent joints, strenuous training is locked."
                    return@SoloButton
                  }
                }

                if (currentStep < totalSteps) {
                  currentStep++
                } else {
                  // Final submission
                  val targetWater = if (gender == "Women") 3000 else 4000
                  val updated = initialPlayer.copy(
                    name = name.ifBlank { "Hunter" },
                    gender = gender,
                    bodyType = bodyType,
                    age = ageInput.toIntOrNull() ?: 20,
                    heightCm = heightCm,
                    weightKg = weightKg,
                    role = role,
                    workoutTimePref = workoutTimePref,
                    preferredStartTime = preferredStartTime,
                    sessionDurationMinutes = sessionDurationMinutes.coerceIn(15, 180),
                    trainingLocation = trainingLocation,
                    fitnessGoal = fitnessGoal,
                    waterTargetMl = targetWater,
                    hasCompletedOnboarding = true
                  )
                  onComplete(updated)
                }
              },
              modifier = Modifier.width(160.dp),
              testTag = "onboarding_continue_button"
            )
          }
        }
      }
    }
  }
}

// STEP 1: PLAYER NAME
@Composable
fun Step1PlayerName(name: String, onNameChange: (String) -> Unit, isWide: Boolean) {
  Column {
    Text(
      text = "QUEST STEP 1: IDENTITY",
      color = ElectricBlueNeon,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "What is your Player Name?",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = TextWhite
    )
    Text(
      text = "Enter your alias in the fitness leveling system.",
      style = MaterialTheme.typography.bodyMedium,
      color = TextMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    OutlinedTextField(
      value = name,
      onValueChange = onNameChange,
      label = { Text("Player Name") },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("onboarding_name_input"),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = ElectricBlueNeon,
        unfocusedBorderColor = LuminousDivider,
        focusedTextColor = TextWhite,
        unfocusedTextColor = TextWhite
      ),
      singleLine = true
    )
  }
}

// STEP 2: GENDER
@Composable
fun Step2Gender(selected: String, onSelect: (String) -> Unit, isWide: Boolean) {
  Column {
    Text(
      text = "QUEST STEP 2: PHYSIQUE CLASSIFICATION",
      color = ElectricBlueNeon,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Select your Gender",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = TextWhite
    )
    Text(
      text = "This sets your biological calorie baselines and initial hydration quest target (3L Women / 4L Men).",
      style = MaterialTheme.typography.bodyMedium,
      color = TextMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    val options = listOf("Men", "Women")
    if (isWide) {
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        options.forEach { opt ->
          SelectionCard(
            text = opt,
            isSelected = selected == opt,
            modifier = Modifier.weight(1f),
            onSelect = { onSelect(opt) }
          )
        }
      }
    } else {
      Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        options.forEach { opt ->
          SelectionCard(
            text = opt,
            isSelected = selected == opt,
            modifier = Modifier.fillMaxWidth(),
            onSelect = { onSelect(opt) }
          )
        }
      }
    }
  }
}

// STEP 3: BODY TYPE
@Composable
fun Step3BodyType(selected: String, onSelect: (String) -> Unit, isWide: Boolean) {
  Column {
    Text(
      text = "QUEST STEP 3: FRAME STRUCTURE",
      color = ElectricBlueNeon,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Current Body Type",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = TextWhite
    )
    Text(
      text = "Select the frame structure that best represents your baseline conditioning.",
      style = MaterialTheme.typography.bodyMedium,
      color = TextMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    val options = listOf(
      Pair("Lean", "Fast metabolism, lower body fat, higher endurance"),
      Pair("Medium", "Balanced muscular frame, athletic baseline"),
      Pair("Fat", "Higher body mass index, primed for strength & cutting")
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
      options.forEach { (type, desc) ->
        SelectionCardWithSub(
          title = type,
          subtitle = desc,
          isSelected = selected == type,
          onSelect = { onSelect(type) }
        )
      }
    }
  }
}

// STEP 4: AGE (Strict validation > 14)
@Composable
fun Step4Age(
  age: String,
  errorMessage: String?,
  onAgeChange: (String) -> Unit,
  isWide: Boolean
) {
  Column {
    Text(
      text = "QUEST STEP 4: AGE VERIFICATION",
      color = ElectricBlueNeon,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "What is your Age?",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = TextWhite
    )
    Text(
      text = "Plans are calibrated strictly for individuals over age 14 to prevent joint strain.",
      style = MaterialTheme.typography.bodyMedium,
      color = TextMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    OutlinedTextField(
      value = age,
      onValueChange = onAgeChange,
      label = { Text("Player Age (Years)") },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("onboarding_age_input"),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = if (errorMessage != null) ErrorRed else ElectricBlueNeon,
        unfocusedBorderColor = if (errorMessage != null) ErrorRed else LuminousDivider,
        focusedTextColor = TextWhite,
        unfocusedTextColor = TextWhite
      ),
      singleLine = true
    )

    if (errorMessage != null) {
      Spacer(modifier = Modifier.height(12.dp))
      Surface(
        color = ErrorRed.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, ErrorRed),
        shape = RoundedCornerShape(8.dp)
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.Top
        ) {
          Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Alert",
            tint = ErrorRed,
            modifier = Modifier.padding(end = 8.dp)
          )
          Text(
            text = errorMessage,
            color = TextWhite,
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 18.sp
          )
        }
      }
    }
  }
}

// STEP 5: HEIGHT & WEIGHT
@Composable
fun Step5HeightWeight(
  height: Float,
  weight: Float,
  onHeightChange: (Float) -> Unit,
  onWeightChange: (Float) -> Unit,
  isWide: Boolean
) {
  Column {
    Text(
      text = "QUEST STEP 5: PHYSICAL STATS",
      color = ElectricBlueNeon,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Height and Weight",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = TextWhite
    )
    Text(
      text = "Calibrates calorie burn calculations and progression metrics.",
      style = MaterialTheme.typography.bodyMedium,
      color = TextMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    Text(
      text = "Height: ${height.toInt()} cm",
      color = TextWhite,
      fontWeight = FontWeight.Bold
    )
    Slider(
      value = height,
      onValueChange = onHeightChange,
      valueRange = 120f..220f,
      colors = SliderDefaults.colors(
        thumbColor = ElectricBlueNeon,
        activeTrackColor = ElectricBlue
      )
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "Weight: ${weight.toInt()} kg",
      color = TextWhite,
      fontWeight = FontWeight.Bold
    )
    Slider(
      value = weight,
      onValueChange = onWeightChange,
      valueRange = 40f..160f,
      colors = SliderDefaults.colors(
        thumbColor = ElectricBlueNeon,
        activeTrackColor = ElectricBlue
      )
    )
  }
}

// STEP 6: ROLE
@Composable
fun Step6Role(selected: String, onSelect: (String) -> Unit, isWide: Boolean) {
  Column {
    Text(
      text = "QUEST STEP 6: REAL-WORLD ROLE",
      color = ElectricBlueNeon,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Select your Profession / Role",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = TextWhite
    )
    Text(
      text = "Every profession has distinct posture and physical demands.",
      style = MaterialTheme.typography.bodyMedium,
      color = TextMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    val roles = listOf("Student", "Engineer", "Doctor", "Other")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      roles.forEach { r ->
        SelectionCard(
          text = r,
          isSelected = selected == r,
          onSelect = { onSelect(r) },
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}

// STEP 7: WORKOUT TIME PREFERENCE
@Composable
fun Step7WorkoutTimePref(selected: String, onSelect: (String) -> Unit, isWide: Boolean) {
  Column {
    Text(
      text = "QUEST STEP 7: CIRCADIAN TIMING",
      color = ElectricBlueNeon,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Workout Time Preference",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = TextWhite
    )
    Text(
      text = "When do you feel most focused and physically energized?",
      style = MaterialTheme.typography.bodyMedium,
      color = TextMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    val options = listOf(
      Pair("Morning", "Rise early, activate metabolism, conquer the day"),
      Pair("Evening", "Decompress after work/studies, release stress"),
      Pair("Both", "Flexible split, training whenever the gate opens")
    )
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
      options.forEach { (time, desc) ->
        SelectionCardWithSub(
          title = time,
          subtitle = desc,
          isSelected = selected == time,
          onSelect = { onSelect(time) }
        )
      }
    }
  }
}

// STEP 8: DURATION AND START TIME
@Composable
fun Step8DurationAndTime(
  startTime: String,
  duration: Int,
  onTimeChange: (String) -> Unit,
  onDurationChange: (Int) -> Unit,
  isWide: Boolean
) {
  Column {
    Text(
      text = "QUEST STEP 8: SESSION DURATION (MAX 3 HOURS)",
      color = ElectricBlueNeon,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Session Duration & Start Time",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = TextWhite
    )
    Text(
      text = "Customized to your schedule. Total workout time never exceeds 3 hours (180 mins). 3-hour sessions are never compulsory.",
      style = MaterialTheme.typography.bodyMedium,
      color = TextMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    val durations = listOf(30, 45, 60, 90, 120)
    Text(
      text = "Planned Duration: $duration Minutes",
      color = ElectricBlueNeon,
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp
    )
    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      durations.forEach { d ->
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(6.dp))
            .background(if (duration == d) ElectricBlue else DeepNavyCardHover)
            .clickable { onDurationChange(d) }
            .padding(vertical = 12.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "${d}m",
            color = if (duration == d) DarkNavyBg else TextWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    val timesRow1 = listOf("06:30", "07:00", "08:00")
    val timesRow2 = listOf("17:30", "19:00", "20:30")
    Text(
      text = "Preferred Start Time: $startTime",
      color = TextWhite,
      fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      timesRow1.forEach { t ->
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(6.dp))
            .background(if (startTime == t) ElectricBlue else DeepNavyCardHover)
            .clickable { onTimeChange(t) }
            .padding(vertical = 10.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = t,
            color = if (startTime == t) DarkNavyBg else TextWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        }
      }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      timesRow2.forEach { t ->
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(6.dp))
            .background(if (startTime == t) ElectricBlue else DeepNavyCardHover)
            .clickable { onTimeChange(t) }
            .padding(vertical = 10.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = t,
            color = if (startTime == t) DarkNavyBg else TextWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        }
      }
    }
  }
}

// STEP 9: TRAINING LOCATION
@Composable
fun Step9Location(selected: String, onSelect: (String) -> Unit, isWide: Boolean) {
  Column {
    Text(
      text = "QUEST STEP 9: TRAINING DOMAIN",
      color = ElectricBlueNeon,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Training Location",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = TextWhite
    )
    Text(
      text = "Gym equips you with barbell and machine quests; Home provides bodyweight & household equipment quests.",
      style = MaterialTheme.typography.bodyMedium,
      color = TextMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    val options = listOf(
      Pair("Gym", "Full gym equipment: Barbells, Dumbbells, Cable Stacks, Leg Press"),
      Pair("Home", "Zero equipment required: Bodyweight, Incline Chairs, Floor Holds")
    )
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
      options.forEach { (loc, desc) ->
        SelectionCardWithSub(
          title = loc,
          subtitle = desc,
          isSelected = selected == loc,
          onSelect = { onSelect(loc) }
        )
      }
    }
  }
}

// STEP 10: FITNESS GOAL
@Composable
fun Step10Goal(selected: String, onSelect: (String) -> Unit, isWide: Boolean) {
  Column {
    Text(
      text = "QUEST STEP 10: ULTIMATE GOAL",
      color = ElectricBlueNeon,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Select your Fitness Goal",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = TextWhite
    )
    Text(
      text = "Directs quest rep schemes, volume, and progressive overload calibrations.",
      style = MaterialTheme.typography.bodyMedium,
      color = TextMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    val goals = listOf(
      "Build muscle",
      "Get lean",
      "Become stronger",
      "Lose fat",
      "Broad and strong",
      "Aesthetic strength"
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      goals.forEach { g ->
        SelectionCard(
          text = g,
          isSelected = selected == g,
          onSelect = { onSelect(g) },
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}

@Composable
fun SelectionCard(
  text: String,
  isSelected: Boolean,
  onSelect: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .border(
        width = if (isSelected) 2.dp else 1.dp,
        color = if (isSelected) ElectricBlueNeon else LuminousDivider,
        shape = RoundedCornerShape(8.dp)
      )
      .background(if (isSelected) DeepNavyCardHover else DeepNavyCard)
      .clickable { onSelect() }
      .padding(horizontal = 16.dp, vertical = 14.dp)
  ) {
    Text(
      text = text,
      color = if (isSelected) ElectricBlueNeon else TextWhite,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
      fontSize = 14.sp
    )
  }
}

@Composable
fun SelectionCardWithSub(
  title: String,
  subtitle: String,
  isSelected: Boolean,
  onSelect: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(8.dp))
      .border(
        width = if (isSelected) 2.dp else 1.dp,
        color = if (isSelected) ElectricBlueNeon else LuminousDivider,
        shape = RoundedCornerShape(8.dp)
      )
      .background(if (isSelected) DeepNavyCardHover else DeepNavyCard)
      .clickable { onSelect() }
      .padding(16.dp)
  ) {
    Column {
      Text(
        text = title,
        color = if (isSelected) ElectricBlueNeon else TextWhite,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = subtitle,
        color = TextMuted,
        fontSize = 12.sp,
        lineHeight = 16.sp
      )
    }
  }
}
