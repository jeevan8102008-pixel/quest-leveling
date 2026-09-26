package com.example.ui.screens.home

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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.entity.DailyQuestEntity
import com.example.data.entity.PlayerEntity
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
import com.example.ui.theme.RankEColor
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WaterBlue

@Composable
fun HomeScreen(
  player: PlayerEntity,
  todayDayOfWeek: Int,
  allQuests: List<DailyQuestEntity>,
  todayCompletionRate: Float,
  onSelectDay: (day: Int) -> Unit,
  onOpenWaterTracker: () -> Unit
) {
  val dayNames = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
  val dayTitles = listOf(
    "Chest & Triceps Power",
    "Legs & Core Fortitude",
    "Back & Biceps Drive",
    "Active Mobility & Recovery",
    "Shoulder & Arm Precision",
    "Full Body Conditioning",
    "Rest & Deep Regeneration"
  )

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

          // TOP PLAYER HEADER
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              // Player Avatar
              Box(
                modifier = Modifier
                  .size(48.dp)
                  .clip(CircleShape)
                  .border(2.dp, ElectricBlueNeon, CircleShape)
                  .background(DeepNavyCard),
                contentAlignment = Alignment.Center
              ) {
                Image(
                  painter = painterResource(id = R.drawable.img_player_avatar),
                  contentDescription = "Player Avatar",
                  modifier = Modifier.fillMaxSize(),
                  contentScale = ContentScale.Crop
                )
              }

              Spacer(modifier = Modifier.width(12.dp))

              Column {
                Text(
                  text = "PLAYER",
                  style = MaterialTheme.typography.labelSmall,
                  color = ElectricBlueNeon,
                  letterSpacing = 1.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = player.name.ifBlank { "Hunter" },
                  style = MaterialTheme.typography.titleLarge,
                  fontWeight = FontWeight.Black,
                  color = TextWhite
                )
              }
            }

            // Visible E-Rank Badge
            RankBadge(rank = player.currentRank)
          }

          Spacer(modifier = Modifier.height(16.dp))
          GlowingDivider()
          Spacer(modifier = Modifier.height(16.dp))

          // PROMINENT CIRCULAR PROGRESS INDICATOR (Today's Completion)
          LuminousCard(
            modifier = Modifier.fillMaxWidth(),
            borderColor = ElectricBlueDark
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "TODAY'S QUEST CLEARANCE",
                  style = MaterialTheme.typography.labelSmall,
                  color = ElectricBlueNeon,
                  letterSpacing = 1.5.sp,
                  fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                  text = "Day $todayDayOfWeek: ${dayTitles.getOrElse(todayDayOfWeek - 1) { "Daily Quest" }}",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = TextWhite
                )

                Text(
                  text = if (todayCompletionRate >= 1f) "All daily goals cleared! System reward ready." else "Quests waiting. Tap to continue daily training.",
                  style = MaterialTheme.typography.bodySmall,
                  color = if (todayCompletionRate >= 1f) SuccessGreen else TextMuted,
                  modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                SoloButton(
                  text = if (todayCompletionRate >= 1f) "Review Day $todayDayOfWeek" else "Enter Day $todayDayOfWeek Quest",
                  onClick = { onSelectDay(todayDayOfWeek) },
                  modifier = Modifier.height(40.dp),
                  testTag = "enter_today_quest_button"
                )
              }

              Spacer(modifier = Modifier.width(16.dp))

              // Prominent Circular Progress Ring
              Box(
                modifier = Modifier.size(110.dp),
                contentAlignment = Alignment.Center
              ) {
                CircularProgressIndicator(
                  progress = { 1f },
                  modifier = Modifier.size(110.dp),
                  color = DarkNavyBg,
                  strokeWidth = 9.dp
                )
                CircularProgressIndicator(
                  progress = { todayCompletionRate },
                  modifier = Modifier.size(110.dp),
                  color = if (todayCompletionRate >= 1f) SuccessGreen else ElectricBlueNeon,
                  trackColor = ElectricBlueDark.copy(alpha = 0.3f),
                  strokeWidth = 9.dp
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(
                    text = "${(todayCompletionRate * 100).toInt()}%",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = TextWhite
                  )
                  Text(
                    text = "PROGRESS",
                    fontSize = 9.sp,
                    color = ElectricBlueNeon,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // QUICK WATER HUD BAR
          LuminousCard(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onOpenWaterTracker() },
            borderColor = WaterBlue.copy(alpha = 0.5f),
            backgroundColor = DeepNavyCardHover
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(WaterBlue.copy(alpha = 0.2f))
                    .border(1.dp, WaterBlue, CircleShape),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Opacity,
                    contentDescription = "Water Tracker",
                    tint = ElectricBlueNeon,
                    modifier = Modifier.size(20.dp)
                  )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = "DAILY WATER TRACKER",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                  )
                  Text(
                    text = "Target: ${player.waterTargetMl} ml • Tap to log hydration",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                  )
                }
              }

              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Open",
                tint = ElectricBlueNeon,
                modifier = Modifier.size(18.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          // 7-DAY QUEST SCHEDULE GRID
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
                text = "SEVEN-DAY QUEST SCHEDULE",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = TextWhite,
                letterSpacing = 1.sp
              )
            }

            Text(
              text = "Tap day to view",
              style = MaterialTheme.typography.bodySmall,
              color = TextMuted
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            for (day in 1..7) {
              val dayQuests = allQuests.filter { it.dayOfWeek == day }
              val totalQuests = dayQuests.size
              val completedQuests = dayQuests.count { it.isCompleted }
              val isToday = day == todayDayOfWeek
              val dayRate = if (totalQuests > 0) completedQuests.toFloat() / totalQuests.toFloat() else 0f
              val isAllDone = totalQuests > 0 && completedQuests == totalQuests

              LuminousCard(
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("schedule_day_card_$day"),
                borderColor = if (isToday) ElectricBlueNeon else if (isAllDone) SuccessGreen.copy(alpha = 0.5f) else LuminousDivider,
                backgroundColor = if (isToday) DeepNavyCardHover else DeepNavyCard,
                onClick = { onSelectDay(day) }
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    // Day indicator badge
                    Box(
                      modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isToday) ElectricBlue else DarkNavyBg)
                        .border(
                          1.dp,
                          if (isToday) ElectricBlueNeon else LuminousDivider,
                          RoundedCornerShape(8.dp)
                        ),
                      contentAlignment = Alignment.Center
                    ) {
                      Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                          text = "D$day",
                          fontWeight = FontWeight.Black,
                          fontSize = 12.sp,
                          color = if (isToday) DarkNavyBg else ElectricBlueNeon
                        )
                        Text(
                          text = dayNames.getOrElse(day - 1) { "D$day" },
                          fontSize = 9.sp,
                          color = if (isToday) DarkNavyBg else TextMuted
                        )
                      }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                          text = dayTitles.getOrElse(day - 1) { "Daily Quest" },
                          style = MaterialTheme.typography.titleSmall,
                          fontWeight = FontWeight.Bold,
                          color = TextWhite
                        )
                        if (isToday) {
                          Spacer(modifier = Modifier.width(8.dp))
                          Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = ElectricBlue.copy(alpha = 0.2f),
                            border = BorderStroke(0.5.dp, ElectricBlueNeon)
                          ) {
                            Text(
                              text = "TODAY",
                              color = ElectricBlueNeon,
                              fontSize = 9.sp,
                              fontWeight = FontWeight.ExtraBold,
                              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                          }
                        }
                      }

                      Spacer(modifier = Modifier.height(2.dp))

                      Text(
                        text = "$completedQuests of $totalQuests Quests Cleared (${(dayRate * 100).toInt()}%)",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isAllDone) SuccessGreen else TextMuted
                      )
                    }
                  }

                  // Status Icon
                  if (isAllDone) {
                    Icon(
                      imageVector = Icons.Default.CheckCircle,
                      contentDescription = "Completed",
                      tint = SuccessGreen,
                      modifier = Modifier.size(24.dp)
                    )
                  } else {
                    Icon(
                      imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                      contentDescription = "Open",
                      tint = if (isToday) ElectricBlueNeon else TextMuted,
                      modifier = Modifier.size(18.dp)
                    )
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
