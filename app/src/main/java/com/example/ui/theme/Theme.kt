package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val SoloLevelingColorScheme = darkColorScheme(
  primary = ElectricBlue,
  onPrimary = DarkNavyBg,
  primaryContainer = DeepNavyCard,
  onPrimaryContainer = ElectricBlueNeon,
  secondary = ElectricBlueNeon,
  onSecondary = DarkNavyBg,
  secondaryContainer = DeepNavyCardHover,
  onSecondaryContainer = TextWhite,
  tertiary = WaterBlue,
  onTertiary = TextWhite,
  background = DarkNavyBg,
  onBackground = TextWhite,
  surface = DeepNavySurface,
  onSurface = TextWhite,
  surfaceVariant = DeepNavyCard,
  onSurfaceVariant = TextMuted,
  outline = LuminousDivider,
  outlineVariant = ElectricBlueDark,
  error = ErrorRed,
  onError = TextWhite
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // For the Solo Leveling fantasy RPG theme, we use the custom electric blue theme
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = SoloLevelingColorScheme,
    typography = Typography,
    content = content
  )
}
