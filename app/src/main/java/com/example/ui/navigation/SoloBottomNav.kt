package com.example.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.ElectricBlueNeon
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

enum class SoloNavDestination(
  val route: String,
  val label: String,
  val activeIcon: ImageVector,
  val inactiveIcon: ImageVector
) {
  HOME("home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
  WORKOUTS("workouts", "Workouts", Icons.Filled.FitnessCenter, Icons.Outlined.FitnessCenter),
  GOAL("goal", "Goal", Icons.Filled.TrackChanges, Icons.Outlined.TrackChanges),
  PROFILE("profile", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

@Composable
fun SoloBottomNav(
  currentDestination: SoloNavDestination,
  onNavigate: (SoloNavDestination) -> Unit
) {
  NavigationBar(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("solo_bottom_navigation_bar"),
    containerColor = DeepNavyCard,
    contentColor = TextWhite,
    tonalElevation = 4.dp
  ) {
    SoloNavDestination.values().forEach { destination ->
      val isSelected = currentDestination == destination

      NavigationBarItem(
        selected = isSelected,
        onClick = { onNavigate(destination) },
        icon = {
          Icon(
            imageVector = if (isSelected) destination.activeIcon else destination.inactiveIcon,
            contentDescription = destination.label
          )
        },
        label = {
          Text(
            text = destination.label.uppercase(),
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
            letterSpacing = 0.5.sp
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = DarkNavyBg,
          selectedTextColor = ElectricBlueNeon,
          indicatorColor = ElectricBlueNeon,
          unselectedIconColor = TextMuted,
          unselectedTextColor = TextMuted
        ),
        modifier = Modifier.testTag("nav_item_${destination.route}")
      )
    }
  }
}
