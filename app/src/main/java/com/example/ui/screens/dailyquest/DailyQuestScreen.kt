package com.example.ui.screens.dailyquest

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.entity.DailyQuestEntity
import com.example.data.entity.PlayerEntity
import com.example.data.model.ExerciseLibrary
import com.example.ui.components.GlowingDivider
import com.example.ui.components.LuminousCard
import com.example.ui.components.MuscleHighlightVisual
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
import com.example.ui.theme.LuminousGlow
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WaterBlue

@Composable
fun DailyQuestScreen(
  player: PlayerEntity,
  dayOfWeek: Int,
  quests: List<DailyQuestEntity>,
  completionRate: Float,
  onToggleQuest: (DailyQuestEntity) -> Unit,
  onUpdateProgress: (DailyQuestEntity, Int) -> Unit,
  onUpdateTarget: (DailyQuestEntity, Int) -> Unit,
  onOpenWaterTracker: () -> Unit,
  onBack: () -> Unit,
  onFinishDay: () -> Unit
) {
  var editingQuestTargetId by remember { mutableStateOf<Long?>(null) }
  var expandedQuestId by remember { mutableStateOf<Long?>(null) }
  var showRescheduleDialog by remember { mutableStateOf(false) }

  val dayTitles = listOf(
    "Day 1: Upper Body Power",
    "Day 2: Lower Body Fortitude",
    "Day 3: Back & Biceps Hunting Drive",
    "Day 4: Active Recovery & Core",
    "Day 5: Shoulder & Arm Precision",
    "Day 6: Full Body Conditioning",
    "Day 7: Deep Rest & Regeneration"
  )
  val dayTitle = dayTitles.getOrElse(dayOfWeek - 1) { "Day $dayOfWeek Quest" }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(DarkNavyBg)
  ) {
    // Atmospheric HUD background overlay
    Image(
      painter = painterResource(id = R.drawable.img_quest_banner),
      contentDescription = null,
      modifier = Modifier
        .fillMaxWidth()
        .height(180.dp),
      contentScale = ContentScale.Crop,
      alpha = 0.22f
    )

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

          // TOP SOLO HUD HEADER (solo.png reference styling)
          Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = DeepNavyCard.copy(alpha = 0.95f),
            border = BorderStroke(1.dp, ElectricBlueDark)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  // Information Icon with subtle blue glow
                  Box(
                    modifier = Modifier
                      .size(36.dp)
                      .clip(CircleShape)
                      .border(1.dp, ElectricBlueNeon, CircleShape)
                      .background(DarkNavyBg),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(
                      imageVector = Icons.Default.Info,
                      contentDescription = "Quest Info",
                      tint = ElectricBlueNeon,
                      modifier = Modifier.size(20.dp)
                    )
                  }

                  Spacer(modifier = Modifier.width(12.dp))

                  Column {
                    Text(
                      text = "DAILY QUEST INFO",
                      style = MaterialTheme.typography.titleLarge,
                      fontWeight = FontWeight.Black,
                      color = TextWhite,
                      letterSpacing = 1.5.sp
                    )
                    Text(
                      text = "Your Day $dayOfWeek quest is ready",
                      style = MaterialTheme.typography.bodySmall,
                      color = ElectricBlueNeon
                    )
                  }
                }

                IconButton(
                  onClick = onBack,
                  modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(DarkNavyBg)
                    .border(1.dp, LuminousDivider, CircleShape)
                    .testTag("daily_quest_close_button")
                ) {
                  Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = TextWhite,
                    modifier = Modifier.size(20.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(14.dp))
              GlowingDivider()
              Spacer(modifier = Modifier.height(14.dp))

              // Player Rank & Day Title Row
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = dayTitle.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                  )
                  Text(
                    text = "Player: ${player.name} • Location: ${player.trainingLocation}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                  )
                }

                RankBadge(rank = player.currentRank)
              }

              Spacer(modifier = Modifier.height(16.dp))

              // Daily Completion Ratio Bar & HUD percentage
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "QUEST PROGRESSION",
                  style = MaterialTheme.typography.labelSmall,
                  color = TextMuted,
                  letterSpacing = 1.sp
                )

                Text(
                  text = "${(completionRate * 100).toInt()}% COMPLETED",
                  style = MaterialTheme.typography.labelLarge,
                  color = if (completionRate >= 1.0f) SuccessGreen else ElectricBlueNeon,
                  fontWeight = FontWeight.Black
                )
              }

              Spacer(modifier = Modifier.height(8.dp))
              QuestProgressBar(progress = completionRate, height = 8.dp)
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // GOALS SECTION HEADER
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .background(ElectricBlueNeon, CircleShape)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "GOALS",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = TextWhite,
                letterSpacing = 2.sp
              )
            }

            Text(
              text = "${quests.count { it.isCompleted }}/${quests.size} CLEARED",
              color = TextMuted,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          // TAPPABLE QUEST ROWS
          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            quests.forEach { quest ->
              val isExpanded = expandedQuestId == quest.id
              val exercise = ExerciseLibrary.getById(quest.exerciseKey)

              LuminousCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = if (quest.isCompleted) SuccessGreen.copy(alpha = 0.6f) else LuminousDivider,
                backgroundColor = if (quest.isCompleted) DeepNavyCardHover else DeepNavyCard
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    // Checkbox & Info
                    Row(
                      modifier = Modifier
                        .weight(1f)
                        .clickable {
                          if (quest.category == "WATER") {
                            onOpenWaterTracker()
                          } else {
                            expandedQuestId = if (isExpanded) null else quest.id
                          }
                        },
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Box(
                        modifier = Modifier
                          .size(36.dp)
                          .clip(RoundedCornerShape(6.dp))
                          .background(
                            when (quest.category) {
                              "WATER" -> WaterBlue.copy(alpha = 0.2f)
                              "DAILY_CORE" -> ElectricBlue.copy(alpha = 0.2f)
                              else -> DarkNavyBg
                            }
                          )
                          .border(
                            1.dp,
                            if (quest.isCompleted) SuccessGreen else ElectricBlueDark,
                            RoundedCornerShape(6.dp)
                          ),
                        contentAlignment = Alignment.Center
                      ) {
                        if (quest.category == "WATER") {
                          Icon(
                            imageVector = Icons.Default.Opacity,
                            contentDescription = "Water",
                            tint = WaterBlue,
                            modifier = Modifier.size(20.dp)
                          )
                        } else {
                          Icon(
                            imageVector = Icons.Default.FitnessCenter,
                            contentDescription = "Workout",
                            tint = if (quest.isCompleted) SuccessGreen else ElectricBlueNeon,
                            modifier = Modifier.size(18.dp)
                          )
                        }
                      }

                      Spacer(modifier = Modifier.width(12.dp))

                      Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                          Text(
                            text = quest.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (quest.isCompleted) TextWhite else TextWhite
                          )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        // Progress count representation: e.g. "Push-ups — 0/20 reps"
                        Text(
                          text = "${quest.currentProgress}/${quest.targetValue} ${quest.unit} • ${quest.targetMuscles}",
                          style = MaterialTheme.typography.bodySmall,
                          color = if (quest.isCompleted) SuccessGreen else TextMuted
                        )
                      }
                    }

                    // Checkbox control
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      if (quest.exerciseKey.startsWith("push_up")) {
                        IconButton(
                          onClick = {
                            editingQuestTargetId = if (editingQuestTargetId == quest.id) null else quest.id
                          }
                        ) {
                          Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Adjust target",
                            tint = ElectricBlueNeon,
                            modifier = Modifier.size(18.dp)
                          )
                        }
                      }

                      Checkbox(
                        checked = quest.isCompleted,
                        onCheckedChange = { onToggleQuest(quest) },
                        modifier = Modifier.testTag("quest_checkbox_${quest.id}"),
                        colors = CheckboxDefaults.colors(
                          checkedColor = SuccessGreen,
                          uncheckedColor = ElectricBlueNeon,
                          checkmarkColor = DarkNavyBg
                        )
                      )
                    }
                  }

                  // Inline Rep Stepper for Quick Logging
                  if (!quest.isCompleted && quest.category != "WATER") {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                      modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkNavyBg, RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                      Text(
                        text = "Log reps: ${quest.currentProgress} / ${quest.targetValue}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                      )

                      Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        IconButton(
                          onClick = {
                            onUpdateProgress(quest, (quest.currentProgress - 5).coerceAtLeast(0))
                          },
                          modifier = Modifier.size(30.dp)
                        ) {
                          Icon(Icons.Default.Remove, contentDescription = "-5", tint = TextWhite)
                        }

                        IconButton(
                          onClick = {
                            onUpdateProgress(quest, quest.currentProgress + 5)
                          },
                          modifier = Modifier.size(30.dp)
                        ) {
                          Icon(Icons.Default.Add, contentDescription = "+5", tint = ElectricBlueNeon)
                        }
                      }
                    }
                  }

                  // Target adjuster for Push-ups
                  if (editingQuestTargetId == quest.id) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                      color = DarkNavyBg,
                      shape = RoundedCornerShape(6.dp),
                      border = BorderStroke(1.dp, ElectricBlueDark),
                      modifier = Modifier.fillMaxWidth()
                    ) {
                      Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                          text = "Adjust Daily Push-up Target Reps",
                          color = ElectricBlueNeon,
                          fontSize = 12.sp,
                          fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                          horizontalArrangement = Arrangement.spacedBy(8.dp),
                          verticalAlignment = Alignment.CenterVertically
                        ) {
                          listOf(10, 15, 20, 30, 50).forEach { target ->
                            Box(
                              modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (quest.targetValue == target) ElectricBlue else DeepNavyCardHover)
                                .clickable {
                                  onUpdateTarget(quest, target)
                                  editingQuestTargetId = null
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                              Text(
                                text = "$target",
                                color = if (quest.targetValue == target) DarkNavyBg else TextWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                              )
                            }
                          }
                        }
                      }
                    }
                  }

                  // Expanded Exercise Details with Form Demonstration GIF & Instructions
                  AnimatedVisibility(
                    visible = isExpanded && exercise != null,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                  ) {
                    if (exercise != null) {
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
                            .height(190.dp)
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
                          text = "EXECUTION & FORM CUES",
                          style = MaterialTheme.typography.labelSmall,
                          color = ElectricBlueNeon,
                          letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        exercise.instructions.forEachIndexed { idx, step ->
                          Text(
                            text = "${idx + 1}. $step",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextWhite,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                          )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Surface(
                          color = DeepNavyCardHover,
                          shape = RoundedCornerShape(6.dp),
                          border = BorderStroke(1.dp, ElectricBlueDark)
                        ) {
                          Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                              imageVector = Icons.Default.Info,
                              contentDescription = "Tips",
                              tint = ElectricBlueNeon,
                              modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                              text = exercise.formTips,
                              color = TextMuted,
                              fontSize = 11.sp
                            )
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          // ACTION BUTTONS (Finish quest or Return to Schedule)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            SoloButton(
              text = "Return to Schedule",
              onClick = onBack,
              isSecondary = true,
              modifier = Modifier.weight(1f),
              testTag = "return_schedule_button"
            )

            SoloButton(
              text = if (completionRate >= 1.0f) "Complete Day $dayOfWeek" else "Save Progress",
              onClick = {
                if (completionRate >= 1.0f) {
                  onFinishDay()
                } else {
                  onBack()
                }
              },
              modifier = Modifier.weight(1f),
              testTag = "finish_quest_button"
            )
          }

          Spacer(modifier = Modifier.height(28.dp))

          // SUPPORTIVE MISSED-QUEST NOTICE (Solo Leveling inspired without penalty!)
          LuminousCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkNavyBg,
            borderColor = LuminousDivider
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Schedule,
                  contentDescription = "Recovery",
                  tint = WarningAmber,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "RECOVERY & FLEXIBILITY NOTICE",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = WarningAmber,
                  letterSpacing = 1.sp
                )
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = "Missed a day or need extra rest? In this realm, recovery is part of progression. Never train through acute joint pain. You can flexibly reschedule this session or complete a gentle 10-minute mobility walk anytime.",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                lineHeight = 18.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(80.dp))
        }
      }
    }
  }
}
