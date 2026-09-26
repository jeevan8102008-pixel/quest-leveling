package com.example.ui.screens.water

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.entity.WaterLogEntity
import com.example.ui.components.GlowingDivider
import com.example.ui.components.LuminousCard
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
import com.example.ui.theme.WaterBlue

@Composable
fun WaterTrackerDialog(
  waterLog: WaterLogEntity?,
  defaultTarget: Int,
  onAddWater: (deltaMl: Int) -> Unit,
  onResetWater: () -> Unit,
  onUpdateTarget: (newTargetMl: Int) -> Unit,
  onDismiss: () -> Unit
) {
  val loggedMl = waterLog?.amountMl ?: 0
  val targetMl = waterLog?.targetMl ?: defaultTarget
  val ratio = if (targetMl > 0) (loggedMl.toFloat() / targetMl.toFloat()).coerceIn(0f, 1f) else 0f
  var isAdjustingTarget by remember { mutableStateOf(false) }
  var tempTarget by remember { mutableIntStateOf(targetMl) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .border(1.dp, ElectricBlueNeon, RoundedCornerShape(16.dp)),
      color = DeepNavyCard
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Dialog Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(WaterBlue.copy(alpha = 0.2f))
                .border(1.dp, WaterBlue, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Opacity,
                contentDescription = "Water",
                tint = ElectricBlueNeon,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "HYDRATION TRACKER",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Black,
              color = TextWhite,
              letterSpacing = 1.sp
            )
          }

          IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))
        GlowingDivider()
        Spacer(modifier = Modifier.height(18.dp))

        // Circular Water Progress Gauge
        Box(
          modifier = Modifier.size(150.dp),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.size(150.dp),
            color = DarkNavyBg,
            strokeWidth = 10.dp
          )
          CircularProgressIndicator(
            progress = { ratio },
            modifier = Modifier.size(150.dp),
            color = WaterBlue,
            trackColor = ElectricBlueDark.copy(alpha = 0.3f),
            strokeWidth = 10.dp
          )

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "${loggedMl} ml",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.ExtraBold,
              color = TextWhite
            )
            Text(
              text = "of ${targetMl} ml",
              style = MaterialTheme.typography.bodySmall,
              color = ElectricBlueNeon
            )
            Text(
              text = "${(ratio * 100).toInt()}% Done",
              style = MaterialTheme.typography.labelSmall,
              color = if (ratio >= 1f) SuccessGreen else TextMuted,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Quick Add Buttons
        Text(
          text = "LOG INTAKE",
          style = MaterialTheme.typography.labelSmall,
          color = TextMuted,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          listOf(250, 500, 750, 1000).forEach { amount ->
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(DeepNavyCardHover)
                .border(1.dp, WaterBlue.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                .clickable { onAddWater(amount) }
                .padding(vertical = 10.dp)
                .testTag("water_add_${amount}"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "+${amount}",
                color = ElectricBlueNeon,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Reset or Adjust Target Actions
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier
              .clickable { isAdjustingTarget = !isAdjustingTarget }
              .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = if (isAdjustingTarget) "Done Adjusting" else "Adjust Target (${targetMl}ml)",
              color = ElectricBlueNeon,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Row(
            modifier = Modifier
              .clickable { onResetWater() }
              .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = TextMuted, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Reset Log",
              color = TextMuted,
              fontSize = 12.sp
            )
          }
        }

        if (isAdjustingTarget) {
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Target: ${tempTarget} ml (${(tempTarget / 1000.0).let { "%.1f".format(it) }} L)",
            color = TextWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          )
          Slider(
            value = tempTarget.toFloat(),
            onValueChange = {
              tempTarget = it.toInt()
              onUpdateTarget(tempTarget)
            },
            valueRange = 1500f..6000f,
            steps = 8,
            colors = SliderDefaults.colors(
              thumbColor = WaterBlue,
              activeTrackColor = ElectricBlueNeon
            )
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Non-medical disclaimer
        Surface(
          color = DarkNavyBg,
          shape = RoundedCornerShape(8.dp),
          border = BorderStroke(0.5.dp, LuminousDivider)
        ) {
          Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = "Notice",
              tint = TextMuted,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Target represents a personal tracking goal, not a medical requirement. Adjust according to thirst, activity level, and climate.",
              style = MaterialTheme.typography.bodySmall,
              color = TextMuted,
              fontSize = 10.sp,
              lineHeight = 14.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SoloButton(
          text = "Close Tracker",
          onClick = onDismiss,
          modifier = Modifier.fillMaxWidth(),
          testTag = "water_close_button"
        )
      }
    }
  }
}
