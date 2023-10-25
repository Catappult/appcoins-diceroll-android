package com.appcoins.diceroll.core.navigation.destinations

import com.appcoins.diceroll.core.navigation.NavigationType

sealed class Destinations(open val route: String) {
  sealed class Screen(override val route: String) : Destinations(route) {
    data object RollGame : Screen("roll_game_screen")
    data object Stats : Screen("stats_screen")
    data object StatsRollDetails : Screen("stats_roll_details_screen")
  }

  sealed class BottomSheet(override val route: String) : Destinations(route) {
    data object Payments : BottomSheet("payments_bottom_sheet")
  }

  sealed class Dialog(override val route: String) : Destinations(route) {
    data object Settings : Dialog("settings_dialog")
  }
}

fun Destinations.toNavigationType(): NavigationType {
  return when (this) {
    is Destinations.Screen -> NavigationType.Composable
    is Destinations.BottomSheet -> NavigationType.BottomSheet
    is Destinations.Dialog -> NavigationType.Dialog
  }
}
