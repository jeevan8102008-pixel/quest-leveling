package com.example.ui.screens.workouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Timer
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.Exercise
import com.example.data.model.ExerciseLibrary
import com.example.ui.components.GlowingDivider
import com.example.ui.components.LuminousCard
import com.example.ui.components.MuscleHighlightVisual
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardHover
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueDark
import com.example.ui.theme.ElectricBlueNeon
import com.example.ui.theme.LuminousDivider
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun WorkoutsScreen() {
  var selectedCategory by remember { mutableStateOf("All") }
  var expandedExerciseId by remember { mutableStateOf<String?>(null) }
  var showMuscleAnatomyModal by remember { mutableStateOf(false) }

  val categories = listOf("All", "Gym", "Home", "Chest", "Back", "Legs", "Shoulders", "Arms", "Core")

  val filteredExercises = ExerciseLibrary.allExercises.filter { exercise ->
    when (selectedCategory) {
      "All" -> true
      "Gym" -> exercise.category == "Gym" || exercise.category == "Both"
      "Home" -> exercise.category == "Home" || exercise.category == "Both"
      else -> exercise.muscleGroup.equals(selectedCategory, ignoreCase = true)
    }
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

          // Screen Title
          Text(
            text = "HUNTER CODEX: EXERCISES",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            color = TextWhite,
            letterSpacing = 1.5.sp
          )
          Text(
            text = "Technical Form Cues, Demonstration GIFs & Muscle Target Anatomy",
            style = MaterialTheme.typography.bodySmall,
            color = ElectricBlueNeon,
            modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
          )

          // Muscle Anatomy Diagram Card
          LuminousCard(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { showMuscleAnatomyModal = !showMuscleAnatomyModal },
            borderColor = ElectricBlueDark
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = "Anatomy",
                    tint = ElectricBlueNeon,
                    modifier = Modifier.size(20.dp)
                  )
                  Spacer(modifier = Modifier.width(10.dp))
                  Text(
                    text = "SYSTEM MUSCLE TARGET ANATOMY",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                  )
                }

                Icon(
                  imageVector = if (showMuscleAnatomyModal) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                  contentDescription = "Toggle",
                  tint = ElectricBlueNeon
                )
              }

              AnimatedVisibility(visible = showMuscleAnatomyModal) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                  Box(
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(200.dp)
                      .clip(RoundedCornerShape(8.dp))
                      .background(DarkNavyBg),
                    contentAlignment = Alignment.Center
                  ) {
                    Image(
                      painter = painterResource(id = R.drawable.img_muscle_reference),
                      contentDescription = "Holographic human muscle target anatomy chart",
                      modifier = Modifier.fillMaxSize(),
                      contentScale = ContentScale.Fit
                    )
                  }
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                    text = "High-definition biometric muscular activation map. Primary drivers: Pectorals, Deltoids, Lats, Quadriceps, Core, Glutes.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    fontSize = 11.sp
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Filter Chips
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            categories.forEach { cat ->
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(if (selectedCategory == cat) ElectricBlue else DeepNavyCard)
                  .border(
                    1.dp,
                    if (selectedCategory == cat) ElectricBlueNeon else LuminousDivider,
                    RoundedCornerShape(6.dp)
                  )
                  .clickable { selectedCategory = cat }
                  .padding(horizontal = 14.dp, vertical = 8.dp)
                  .testTag("filter_chip_$cat")
              ) {
                Text(
                  text = cat,
                  color = if (selectedCategory == cat) DarkNavyBg else TextWhite,
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Exercise List
          Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            filteredExercises.forEach { exercise ->
              val isExpanded = expandedExerciseId == exercise.id

              LuminousCard(
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("exercise_item_${exercise.id}"),
                borderColor = if (isExpanded) ElectricBlueNeon else LuminousDivider,
                backgroundColor = if (isExpanded) DeepNavyCardHover else DeepNavyCard
              ) {
                Column(
                  modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                      expandedExerciseId = if (isExpanded) null else exercise.id
                    }
                    .padding(16.dp)
                ) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Column(modifier = Modifier.weight(1f)) {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                          text = exercise.name,
                          style = MaterialTheme.typography.titleMedium,
                          fontWeight = FontWeight.Bold,
                          color = TextWhite
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                          shape = RoundedCornerShape(4.dp),
                          color = ElectricBlue.copy(alpha = 0.15f),
                          border = BorderStroke(0.5.dp, ElectricBlueNeon)
                        ) {
                          Text(
                            text = exercise.category.uppercase(),
                            color = ElectricBlueNeon,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                          )
                        }
                      }

                      Spacer(modifier = Modifier.height(4.dp))

                      Text(
                        text = "Muscles: ${exercise.targetMuscles}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                      )

                      Text(
                        text = "${exercise.defaultSets} Sets × ${exercise.defaultReps} ${exercise.unit} • Rest: ${exercise.restSeconds}s",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricBlueNeon,
                        modifier = Modifier.padding(top = 2.dp)
                      )
                    }

                    Icon(
                      imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                      contentDescription = "Expand",
                      tint = ElectricBlueNeon
                    )
                  }

                  // Expanded Details: GIF, Step-by-Step, Muscle diagram & Tips
                  AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                  ) {
                    Column(
                      modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp)
                    ) {
                      GlowingDivider(modifier = Modifier.padding(bottom = 12.dp))

                      // Dedicated Workout Image showing Activated Muscles in Glowing Color
                      Box(
                        modifier = Modifier
                          .fillMaxWidth()
                          .height(200.dp)
                          .clip(RoundedCornerShape(8.dp))
                          .background(DarkNavyBg)
                          .border(1.dp, LuminousDivider, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                      ) {
                        Image(
                          painter = painterResource(id = exercise.imageRes),
                          contentDescription = "Workout form and active muscles for ${exercise.name}",
                          modifier = Modifier.fillMaxSize(),
                          contentScale = ContentScale.Crop
                        )
                        // Label badge overlay
                        Surface(
                          modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp),
                          shape = RoundedCornerShape(4.dp),
                          color = DarkNavyBg.copy(alpha = 0.85f),
                          border = BorderStroke(0.5.dp, ElectricBlueNeon)
                        ) {
                          Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                          ) {
                            Box(
                              modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(ElectricBlueNeon)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                              text = "ACTIVE MUSCLE ENGAGEMENT",
                              fontSize = 9.sp,
                              fontWeight = FontWeight.Bold,
                              color = ElectricBlueNeon,
                              letterSpacing = 0.5.sp
                            )
                          }
                        }
                      }

                      Spacer(modifier = Modifier.height(12.dp))

                      // Dynamic Holographic Muscle Activation Map with Highlighted Colors
                      MuscleHighlightVisual(
                        primaryMuscleGroup = exercise.muscleGroup,
                        targetMusclesText = exercise.targetMuscles,
                        modifier = Modifier.fillMaxWidth()
                      )

                      Spacer(modifier = Modifier.height(12.dp))

                      Text(
                        text = exercise.fallbackDescription,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextWhite,
                        modifier = Modifier.padding(bottom = 8.dp)
                      )

                      Text(
                        text = "STEP-BY-STEP INSTRUCTIONS",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricBlueNeon,
                        letterSpacing = 1.sp
                      )
                      Spacer(modifier = Modifier.height(4.dp))

                      exercise.instructions.forEachIndexed { i, step ->
                        Text(
                          text = "${i + 1}. $step",
                          style = MaterialTheme.typography.bodySmall,
                          color = TextWhite.copy(alpha = 0.9f),
                          lineHeight = 18.sp,
                          modifier = Modifier.padding(vertical = 2.dp)
                        )
                      }

                      Spacer(modifier = Modifier.height(10.dp))

                      Surface(
                        color = DarkNavyBg,
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(0.5.dp, ElectricBlueDark)
                      ) {
                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                          Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Pro Tip",
                            tint = ElectricBlueNeon,
                            modifier = Modifier.size(16.dp)
                          )
                          Spacer(modifier = Modifier.width(8.dp))
                          Text(
                            text = "Form Master Tip: ${exercise.formTips}",
                            color = TextMuted,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                          )
                        }
                      }
                    }
                  }
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(100.dp))
        }
      }
    }
  }
}
