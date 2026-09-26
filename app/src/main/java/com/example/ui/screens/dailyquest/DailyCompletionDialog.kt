package com.example.ui.screens.dailyquest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.components.GlowingDivider
import com.example.ui.components.SoloButton
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueNeon
import com.example.ui.theme.RankEColor
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun DailyCompletionDialog(
  dayOfWeek: Int,
  durationMinutes: Int,
  caloriesBurned: Int,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = DeepNavyCard,
      border = BorderStroke(2.dp, ElectricBlueNeon),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(DarkNavyBg)
            .border(2.dp, SuccessGreen, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = "Cleared",
            tint = SuccessGreen,
            modifier = Modifier.size(40.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "DAY $dayOfWeek QUEST CLEARED!",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Black,
          color = TextWhite,
          letterSpacing = 1.5.sp,
          textAlign = TextAlign.Center
        )

        Text(
          text = "System parameters updated. Vital signs stabilized.",
          style = MaterialTheme.typography.bodySmall,
          color = ElectricBlueNeon,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        GlowingDivider()
        Spacer(modifier = Modifier.height(16.dp))

        // Rewards matrix
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "XP REWARD", style = MaterialTheme.typography.labelSmall, color = TextMuted)
            Text(text = "+100 XP", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = ElectricBlueNeon)
          }

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "CALORIES", style = MaterialTheme.typography.labelSmall, color = TextMuted)
            Text(text = "~$caloriesBurned kcal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = TextWhite)
          }

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "TIME", style = MaterialTheme.typography.labelSmall, color = TextMuted)
            Text(text = "${durationMinutes}m", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = TextWhite)
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SoloButton(
          text = "Collect Reward & Return",
          onClick = onDismiss,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}
