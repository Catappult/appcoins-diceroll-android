package com.appcoins.wallet.sampleapp.diceroll.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.appcoins.wallet.sampleapp.diceroll.core.design.DiceRollIcons
import com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui.R as GameUiR
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui.R as StatsUiR

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val iconTextId: Int,
  val titleTextId: Int,
) {
  GAME(
    selectedIcon = DiceRollIcons.game,
    unselectedIcon = DiceRollIcons.game,
    iconTextId = GameUiR.string.roll_game_title,
    titleTextId = GameUiR.string.top_bar_title,
  ),
  STATS(
    selectedIcon = DiceRollIcons.stats,
    unselectedIcon = DiceRollIcons.stats,
    iconTextId = StatsUiR.string.stats_title,
    titleTextId = StatsUiR.string.top_bar_title,
  ),
}
