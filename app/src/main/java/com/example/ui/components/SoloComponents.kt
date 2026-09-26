package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueDark
import com.example.ui.theme.ElectricBlueDeep
import com.example.ui.theme.ElectricBlueNeon
import com.example.ui.theme.LuminousDivider
import com.example.ui.theme.LuminousGlow
import com.example.ui.theme.RankAColor
import com.example.ui.theme.RankBColor
import com.example.ui.theme.RankCColor
import com.example.ui.theme.RankDColor
import com.example.ui.theme.RankEColor
import com.example.ui.theme.RankSColor
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun LuminousCard(
  modifier: Modifier = Modifier,
  borderColor: Color = LuminousDivider,
  borderWidth: Dp = 1.dp,
  backgroundColor: Color = DeepNavyCard,
  onClick: (() -> Unit)? = null,
  content: @Composable () -> Unit
) {
  Card(
    modifier = modifier
      .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = backgroundColor),
    border = BorderStroke(borderWidth, borderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    content()
  }
}

@Composable
fun SoloButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  isSecondary: Boolean = false,
  testTag: String = "action_button"
) {
  if (isSecondary) {
    OutlinedButton(
      onClick = onClick,
      modifier = modifier
        .height(48.dp)
        .testTag(testTag),
      shape = RoundedCornerShape(6.dp),
      border = BorderStroke(1.dp, ElectricBlue),
      colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricBlueNeon),
      enabled = enabled
    ) {
      Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
    }
  } else {
    Button(
      onClick = onClick,
      modifier = modifier
        .height(48.dp)
        .testTag(testTag),
      shape = RoundedCornerShape(6.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = ElectricBlue,
        contentColor = TextDark,
        disabledContainerColor = ElectricBlueDark,
        disabledContentColor = TextMuted
      ),
      enabled = enabled
    ) {
      Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 1.sp
      )
    }
  }
}

@Composable
fun RankBadge(
  rank: String,
  modifier: Modifier = Modifier,
  size: Dp = 28.dp
) {
  val badgeColor = when {
    rank.contains("S") -> RankSColor
    rank.contains("A") -> RankAColor
    rank.contains("B") -> RankBColor
    rank.contains("C") -> RankCColor
    rank.contains("D") -> RankDColor
    else -> RankEColor
  }

  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(4.dp),
    color = badgeColor.copy(alpha = 0.15f),
    border = BorderStroke(1.dp, badgeColor)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(6.dp)
          .clip(CircleShape)
          .background(badgeColor)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = rank.uppercase(),
        color = badgeColor,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
    }
  }
}

@Composable
fun GlowingDivider(
  modifier: Modifier = Modifier,
  color: Color = LuminousDivider
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(1.dp)
      .background(
        Brush.horizontalGradient(
          colors = listOf(
            Color.Transparent,
            color,
            ElectricBlueNeon.copy(alpha = 0.7f),
            color,
            Color.Transparent
          )
        )
      )
  )
}

@Composable
fun QuestProgressBar(
  progress: Float,
  modifier: Modifier = Modifier,
  height: Dp = 6.dp
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(height)
      .clip(RoundedCornerShape(3.dp))
      .background(DarkNavyBg)
      .border(0.5.dp, ElectricBlueDark, RoundedCornerShape(3.dp))
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth(fraction = progress.coerceIn(0f, 1f))
        .height(height)
        .background(
          Brush.horizontalGradient(
            colors = listOf(ElectricBlueDeep, ElectricBlue, ElectricBlueNeon)
          )
        )
    )
  }
}
