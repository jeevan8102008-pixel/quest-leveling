package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardHover
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueDark
import com.example.ui.theme.ElectricBlueNeon
import com.example.ui.theme.LuminousDivider
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

/**
 * High-tech Solo Leveling Anatomical Muscle Activation Visualizer.
 * Renders an offline biometric body map showing precisely which muscles
 * are activated during an exercise by highlighting them in distinct electric neon colors.
 */
@Composable
fun MuscleHighlightVisual(
  primaryMuscleGroup: String, // e.g. "Chest", "Back", "Legs", "Shoulders", "Arms", "Core", "Mobility"
  targetMusclesText: String,   // detailed description e.g. "Pectorals, Anterior Deltoids, Triceps"
  modifier: Modifier = Modifier
) {
  var viewMode by remember {
    mutableStateOf(
      if (primaryMuscleGroup.equals("Back", ignoreCase = true)) "BACK" else "FRONT"
    )
  }

  val isChestActive = primaryMuscleGroup.equals("Chest", ignoreCase = true) ||
      targetMusclesText.contains("Pectoral", ignoreCase = true) ||
      targetMusclesText.contains("Chest", ignoreCase = true)

  val isBackActive = primaryMuscleGroup.equals("Back", ignoreCase = true) ||
      targetMusclesText.contains("Lat", ignoreCase = true) ||
      targetMusclesText.contains("Rhomboid", ignoreCase = true) ||
      targetMusclesText.contains("Trapezius", ignoreCase = true) ||
      targetMusclesText.contains("Back", ignoreCase = true)

  val isShouldersActive = primaryMuscleGroup.equals("Shoulders", ignoreCase = true) ||
      targetMusclesText.contains("Deltoid", ignoreCase = true) ||
      targetMusclesText.contains("Shoulder", ignoreCase = true)

  val isArmsActive = primaryMuscleGroup.equals("Arms", ignoreCase = true) ||
      targetMusclesText.contains("Bicep", ignoreCase = true) ||
      targetMusclesText.contains("Tricep", ignoreCase = true)

  val isCoreActive = primaryMuscleGroup.equals("Core", ignoreCase = true) ||
      targetMusclesText.contains("Abdominis", ignoreCase = true) ||
      targetMusclesText.contains("Core", ignoreCase = true) ||
      targetMusclesText.contains("Plank", ignoreCase = true)

  val isLegsActive = primaryMuscleGroup.equals("Legs", ignoreCase = true) ||
      targetMusclesText.contains("Quad", ignoreCase = true) ||
      targetMusclesText.contains("Glute", ignoreCase = true) ||
      targetMusclesText.contains("Hamstring", ignoreCase = true) ||
      targetMusclesText.contains("Calf", ignoreCase = true) ||
      targetMusclesText.contains("Calves", ignoreCase = true)

  val isFullBody = primaryMuscleGroup.equals("Mobility", ignoreCase = true) ||
      targetMusclesText.contains("Full Body", ignoreCase = true)

  // Color Tokens
  val activeColor = ElectricBlueNeon // Glowing Cyan/Electric Blue for primary activated muscles
  val secondaryActiveColor = Color(0xFFFF9E00) // Glowing Amber for secondary/assist muscles
  val inactiveColor = Color(0xFF131D33) // Muted Dark Navy for resting muscles
  val outlineColor = Color(0xFF1F355C) // Luminous wireframe

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .background(DarkNavyBg)
      .border(1.dp, LuminousDivider, RoundedCornerShape(10.dp))
      .padding(14.dp)
  ) {
    // Header with View Toggle & Status
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(ElectricBlueNeon)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "MUSCULAR ACTIVATION MAP",
          style = MaterialTheme.typography.labelSmall,
          color = ElectricBlueNeon,
          fontWeight = FontWeight.Black,
          letterSpacing = 1.sp
        )
      }

      // Front / Back View Switcher
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(DeepNavyCard)
          .border(0.5.dp, LuminousDivider, RoundedCornerShape(6.dp))
          .padding(2.dp)
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(if (viewMode == "FRONT") ElectricBlue else Color.Transparent)
            .clickable { viewMode = "FRONT" }
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = "FRONT",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = if (viewMode == "FRONT") DarkNavyBg else TextMuted
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(if (viewMode == "BACK") ElectricBlue else Color.Transparent)
            .clickable { viewMode = "BACK" }
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = "BACK",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = if (viewMode == "BACK") DarkNavyBg else TextMuted
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Interactive Vector Canvas Map
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(DeepNavyCard.copy(alpha = 0.7f))
        .border(1.dp, ElectricBlueDark.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
      contentAlignment = Alignment.Center
    ) {
      Canvas(
        modifier = Modifier
          .fillMaxSize()
          .padding(vertical = 12.dp, horizontal = 24.dp)
      ) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        if (viewMode == "FRONT") {
          drawFrontBody(
            centerX = centerX,
            canvasHeight = size.height,
            isChest = isChestActive || isFullBody,
            isShoulders = isShouldersActive || isFullBody,
            isArms = isArmsActive || (isChestActive && !isLegsActive) || isFullBody,
            isCore = isCoreActive || isFullBody,
            isLegs = isLegsActive || isFullBody,
            activeColor = activeColor,
            secondaryColor = secondaryActiveColor,
            inactiveColor = inactiveColor,
            outlineColor = outlineColor
          )
        } else {
          drawBackBody(
            centerX = centerX,
            canvasHeight = size.height,
            isBack = isBackActive || isFullBody,
            isShoulders = isShouldersActive || isFullBody,
            isArms = isArmsActive || isFullBody,
            isGlutes = isLegsActive || isFullBody,
            isHamstrings = isLegsActive || isFullBody,
            isCalves = isLegsActive || isFullBody,
            activeColor = activeColor,
            secondaryColor = secondaryActiveColor,
            inactiveColor = inactiveColor,
            outlineColor = outlineColor
          )
        }
      }

      // Legend in bottom corner
      Row(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(ElectricBlueNeon)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "ACTIVE TARGET",
          fontSize = 9.sp,
          color = ElectricBlueNeon,
          fontWeight = FontWeight.Bold
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Active Muscle Tags Row
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = "PRIMARY TARGET: ${primaryMuscleGroup.uppercase()}",
          fontSize = 11.sp,
          fontWeight = FontWeight.Black,
          color = ElectricBlueNeon,
          letterSpacing = 0.5.sp
        )
        Text(
          text = targetMusclesText,
          fontSize = 11.sp,
          color = TextWhite,
          modifier = Modifier.padding(top = 1.dp)
        )
      }

      Surface(
        shape = RoundedCornerShape(4.dp),
        color = ElectricBlueNeon.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, ElectricBlueNeon)
      ) {
        Text(
          text = "ENGAGED",
          color = ElectricBlueNeon,
          fontSize = 9.sp,
          fontWeight = FontWeight.ExtraBold,
          modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
          letterSpacing = 1.sp
        )
      }
    }
  }
}

// Draw Front Anatomical Silhouette
private fun DrawScope.drawFrontBody(
  centerX: Float,
  canvasHeight: Float,
  isChest: Boolean,
  isShoulders: Boolean,
  isArms: Boolean,
  isCore: Boolean,
  isLegs: Boolean,
  activeColor: Color,
  secondaryColor: Color,
  inactiveColor: Color,
  outlineColor: Color
) {
  val scale = canvasHeight / 200f
  val headY = 16f * scale
  val headRadius = 11f * scale

  // Head
  drawCircle(
    color = inactiveColor,
    radius = headRadius,
    center = Offset(centerX, headY)
  )
  drawCircle(
    color = outlineColor,
    radius = headRadius,
    center = Offset(centerX, headY),
    style = Stroke(width = 1.5f)
  )

  // Neck
  drawRect(
    color = inactiveColor,
    topLeft = Offset(centerX - 4f * scale, headY + 8f * scale),
    size = Size(8f * scale, 8f * scale)
  )

  // Shoulders (Left & Right Deltoids)
  val shoulderColor = if (isShoulders) activeColor else inactiveColor
  // Left Deltoid
  drawRoundRect(
    color = shoulderColor,
    topLeft = Offset(centerX - 34f * scale, headY + 12f * scale),
    size = Size(14f * scale, 16f * scale),
    cornerRadius = CornerRadius(6f * scale)
  )
  drawRoundRect(
    color = if (isShoulders) activeColor else outlineColor,
    topLeft = Offset(centerX - 34f * scale, headY + 12f * scale),
    size = Size(14f * scale, 16f * scale),
    cornerRadius = CornerRadius(6f * scale),
    style = Stroke(width = 1.5f)
  )
  // Right Deltoid
  drawRoundRect(
    color = shoulderColor,
    topLeft = Offset(centerX + 20f * scale, headY + 12f * scale),
    size = Size(14f * scale, 16f * scale),
    cornerRadius = CornerRadius(6f * scale)
  )
  drawRoundRect(
    color = if (isShoulders) activeColor else outlineColor,
    topLeft = Offset(centerX + 20f * scale, headY + 12f * scale),
    size = Size(14f * scale, 16f * scale),
    cornerRadius = CornerRadius(6f * scale),
    style = Stroke(width = 1.5f)
  )

  // Chest (Pectorals: Left & Right)
  val chestColor = if (isChest) activeColor else inactiveColor
  // Left Pec
  drawRoundRect(
    color = chestColor,
    topLeft = Offset(centerX - 19f * scale, headY + 15f * scale),
    size = Size(18f * scale, 16f * scale),
    cornerRadius = CornerRadius(4f * scale)
  )
  drawRoundRect(
    color = if (isChest) activeColor else outlineColor,
    topLeft = Offset(centerX - 19f * scale, headY + 15f * scale),
    size = Size(18f * scale, 16f * scale),
    cornerRadius = CornerRadius(4f * scale),
    style = Stroke(width = 1.5f)
  )
  // Right Pec
  drawRoundRect(
    color = chestColor,
    topLeft = Offset(centerX + 1f * scale, headY + 15f * scale),
    size = Size(18f * scale, 16f * scale),
    cornerRadius = CornerRadius(4f * scale)
  )
  drawRoundRect(
    color = if (isChest) activeColor else outlineColor,
    topLeft = Offset(centerX + 1f * scale, headY + 15f * scale),
    size = Size(18f * scale, 16f * scale),
    cornerRadius = CornerRadius(4f * scale),
    style = Stroke(width = 1.5f)
  )

  // Biceps / Forearms (Left & Right)
  val armsColor = if (isArms) secondaryColor else inactiveColor
  // Left Bicep
  drawRoundRect(
    color = armsColor,
    topLeft = Offset(centerX - 35f * scale, headY + 30f * scale),
    size = Size(11f * scale, 24f * scale),
    cornerRadius = CornerRadius(5f * scale)
  )
  drawRoundRect(
    color = if (isArms) secondaryColor else outlineColor,
    topLeft = Offset(centerX - 35f * scale, headY + 30f * scale),
    size = Size(11f * scale, 24f * scale),
    cornerRadius = CornerRadius(5f * scale),
    style = Stroke(width = 1.5f)
  )
  // Right Bicep
  drawRoundRect(
    color = armsColor,
    topLeft = Offset(centerX + 24f * scale, headY + 30f * scale),
    size = Size(11f * scale, 24f * scale),
    cornerRadius = CornerRadius(5f * scale)
  )
  drawRoundRect(
    color = if (isArms) secondaryColor else outlineColor,
    topLeft = Offset(centerX + 24f * scale, headY + 30f * scale),
    size = Size(11f * scale, 24f * scale),
    cornerRadius = CornerRadius(5f * scale),
    style = Stroke(width = 1.5f)
  )

  // Core / Abdominals (Abs)
  val coreColor = if (isCore) activeColor else inactiveColor
  drawRoundRect(
    color = coreColor,
    topLeft = Offset(centerX - 16f * scale, headY + 33f * scale),
    size = Size(32f * scale, 26f * scale),
    cornerRadius = CornerRadius(5f * scale)
  )
  drawRoundRect(
    color = if (isCore) activeColor else outlineColor,
    topLeft = Offset(centerX - 16f * scale, headY + 33f * scale),
    size = Size(32f * scale, 26f * scale),
    cornerRadius = CornerRadius(5f * scale),
    style = Stroke(width = 1.5f)
  )
  // Abdominal segments
  drawLine(
    color = if (isCore) DarkNavyBg else outlineColor,
    start = Offset(centerX, headY + 34f * scale),
    end = Offset(centerX, headY + 58f * scale),
    strokeWidth = 1.5f
  )

  // Pelvis / Hips
  drawRect(
    color = inactiveColor,
    topLeft = Offset(centerX - 18f * scale, headY + 60f * scale),
    size = Size(36f * scale, 12f * scale)
  )

  // Legs: Quadriceps (Front Thighs)
  val legsColor = if (isLegs) activeColor else inactiveColor
  // Left Quad
  drawRoundRect(
    color = legsColor,
    topLeft = Offset(centerX - 18f * scale, headY + 74f * scale),
    size = Size(16f * scale, 42f * scale),
    cornerRadius = CornerRadius(6f * scale)
  )
  drawRoundRect(
    color = if (isLegs) activeColor else outlineColor,
    topLeft = Offset(centerX - 18f * scale, headY + 74f * scale),
    size = Size(16f * scale, 42f * scale),
    cornerRadius = CornerRadius(6f * scale),
    style = Stroke(width = 1.5f)
  )
  // Right Quad
  drawRoundRect(
    color = legsColor,
    topLeft = Offset(centerX + 2f * scale, headY + 74f * scale),
    size = Size(16f * scale, 42f * scale),
    cornerRadius = CornerRadius(6f * scale)
  )
  drawRoundRect(
    color = if (isLegs) activeColor else outlineColor,
    topLeft = Offset(centerX + 2f * scale, headY + 74f * scale),
    size = Size(16f * scale, 42f * scale),
    cornerRadius = CornerRadius(6f * scale),
    style = Stroke(width = 1.5f)
  )

  // Calves (Front Shins & Calves)
  val calvesColor = if (isLegs) secondaryColor else inactiveColor
  // Left Calf
  drawRoundRect(
    color = calvesColor,
    topLeft = Offset(centerX - 16f * scale, headY + 118f * scale),
    size = Size(13f * scale, 38f * scale),
    cornerRadius = CornerRadius(5f * scale)
  )
  drawRoundRect(
    color = if (isLegs) secondaryColor else outlineColor,
    topLeft = Offset(centerX - 16f * scale, headY + 118f * scale),
    size = Size(13f * scale, 38f * scale),
    cornerRadius = CornerRadius(5f * scale),
    style = Stroke(width = 1.5f)
  )
  // Right Calf
  drawRoundRect(
    color = calvesColor,
    topLeft = Offset(centerX + 3f * scale, headY + 118f * scale),
    size = Size(13f * scale, 38f * scale),
    cornerRadius = CornerRadius(5f * scale)
  )
  drawRoundRect(
    color = if (isLegs) secondaryColor else outlineColor,
    topLeft = Offset(centerX + 3f * scale, headY + 118f * scale),
    size = Size(13f * scale, 38f * scale),
    cornerRadius = CornerRadius(5f * scale),
    style = Stroke(width = 1.5f)
  )
}

// Draw Back Anatomical Silhouette
private fun DrawScope.drawBackBody(
  centerX: Float,
  canvasHeight: Float,
  isBack: Boolean,
  isShoulders: Boolean,
  isArms: Boolean,
  isGlutes: Boolean,
  isHamstrings: Boolean,
  isCalves: Boolean,
  activeColor: Color,
  secondaryColor: Color,
  inactiveColor: Color,
  outlineColor: Color
) {
  val scale = canvasHeight / 200f
  val headY = 16f * scale
  val headRadius = 11f * scale

  // Head
  drawCircle(
    color = inactiveColor,
    radius = headRadius,
    center = Offset(centerX, headY)
  )
  drawCircle(
    color = outlineColor,
    radius = headRadius,
    center = Offset(centerX, headY),
    style = Stroke(width = 1.5f)
  )

  // Trapezius / Upper Back
  val backColor = if (isBack) activeColor else inactiveColor
  drawRoundRect(
    color = backColor,
    topLeft = Offset(centerX - 24f * scale, headY + 12f * scale),
    size = Size(48f * scale, 22f * scale),
    cornerRadius = CornerRadius(6f * scale)
  )
  drawRoundRect(
    color = if (isBack) activeColor else outlineColor,
    topLeft = Offset(centerX - 24f * scale, headY + 12f * scale),
    size = Size(48f * scale, 22f * scale),
    cornerRadius = CornerRadius(6f * scale),
    style = Stroke(width = 1.5f)
  )

  // Latissimus Dorsi (Lats)
  drawRoundRect(
    color = backColor,
    topLeft = Offset(centerX - 20f * scale, headY + 32f * scale),
    size = Size(40f * scale, 26f * scale),
    cornerRadius = CornerRadius(5f * scale)
  )
  drawRoundRect(
    color = if (isBack) activeColor else outlineColor,
    topLeft = Offset(centerX - 20f * scale, headY + 32f * scale),
    size = Size(40f * scale, 26f * scale),
    cornerRadius = CornerRadius(5f * scale),
    style = Stroke(width = 1.5f)
  )

  // Triceps (Back of Arms)
  val tricepsColor = if (isArms) secondaryColor else inactiveColor
  // Left Tricep
  drawRoundRect(
    color = tricepsColor,
    topLeft = Offset(centerX - 35f * scale, headY + 26f * scale),
    size = Size(11f * scale, 26f * scale),
    cornerRadius = CornerRadius(5f * scale)
  )
  drawRoundRect(
    color = if (isArms) secondaryColor else outlineColor,
    topLeft = Offset(centerX - 35f * scale, headY + 26f * scale),
    size = Size(11f * scale, 26f * scale),
    cornerRadius = CornerRadius(5f * scale),
    style = Stroke(width = 1.5f)
  )
  // Right Tricep
  drawRoundRect(
    color = tricepsColor,
    topLeft = Offset(centerX + 24f * scale, headY + 26f * scale),
    size = Size(11f * scale, 26f * scale),
    cornerRadius = CornerRadius(5f * scale)
  )
  drawRoundRect(
    color = if (isArms) secondaryColor else outlineColor,
    topLeft = Offset(centerX + 24f * scale, headY + 26f * scale),
    size = Size(11f * scale, 26f * scale),
    cornerRadius = CornerRadius(5f * scale),
    style = Stroke(width = 1.5f)
  )

  // Glutes (Gluteus Maximus)
  val glutesColor = if (isGlutes) activeColor else inactiveColor
  drawRoundRect(
    color = glutesColor,
    topLeft = Offset(centerX - 19f * scale, headY + 60f * scale),
    size = Size(38f * scale, 18f * scale),
    cornerRadius = CornerRadius(7f * scale)
  )
  drawRoundRect(
    color = if (isGlutes) activeColor else outlineColor,
    topLeft = Offset(centerX - 19f * scale, headY + 60f * scale),
    size = Size(38f * scale, 18f * scale),
    cornerRadius = CornerRadius(7f * scale),
    style = Stroke(width = 1.5f)
  )

  // Hamstrings (Back of Thighs)
  val hamstringsColor = if (isHamstrings) secondaryColor else inactiveColor
  // Left Hamstring
  drawRoundRect(
    color = hamstringsColor,
    topLeft = Offset(centerX - 18f * scale, headY + 80f * scale),
    size = Size(16f * scale, 38f * scale),
    cornerRadius = CornerRadius(6f * scale)
  )
  drawRoundRect(
    color = if (isHamstrings) secondaryColor else outlineColor,
    topLeft = Offset(centerX - 18f * scale, headY + 80f * scale),
    size = Size(16f * scale, 38f * scale),
    cornerRadius = CornerRadius(6f * scale),
    style = Stroke(width = 1.5f)
  )
  // Right Hamstring
  drawRoundRect(
    color = hamstringsColor,
    topLeft = Offset(centerX + 2f * scale, headY + 80f * scale),
    size = Size(16f * scale, 38f * scale),
    cornerRadius = CornerRadius(6f * scale)
  )
  drawRoundRect(
    color = if (isHamstrings) secondaryColor else outlineColor,
    topLeft = Offset(centerX + 2f * scale, headY + 80f * scale),
    size = Size(16f * scale, 38f * scale),
    cornerRadius = CornerRadius(6f * scale),
    style = Stroke(width = 1.5f)
  )

  // Calves (Gastrocnemius / Soleus)
  val calvesColor = if (isCalves) activeColor else inactiveColor
  // Left Calf
  drawRoundRect(
    color = calvesColor,
    topLeft = Offset(centerX - 16f * scale, headY + 120f * scale),
    size = Size(13f * scale, 38f * scale),
    cornerRadius = CornerRadius(6f * scale)
  )
  drawRoundRect(
    color = if (isCalves) activeColor else outlineColor,
    topLeft = Offset(centerX - 16f * scale, headY + 120f * scale),
    size = Size(13f * scale, 38f * scale),
    cornerRadius = CornerRadius(6f * scale),
    style = Stroke(width = 1.5f)
  )
  // Right Calf
  drawRoundRect(
    color = calvesColor,
    topLeft = Offset(centerX + 3f * scale, headY + 120f * scale),
    size = Size(13f * scale, 38f * scale),
    cornerRadius = CornerRadius(6f * scale)
  )
  drawRoundRect(
    color = if (isCalves) activeColor else outlineColor,
    topLeft = Offset(centerX + 3f * scale, headY + 120f * scale),
    size = Size(13f * scale, 38f * scale),
    cornerRadius = CornerRadius(6f * scale),
    style = Stroke(width = 1.5f)
  )
}
