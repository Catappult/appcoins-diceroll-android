package com.appcoins.diceroll.feature.stats.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.appcoins.diceroll.feature.stats.ui.RollDetailsStatsRoute
import com.appcoins.diceroll.feature.stats.ui.StatsRoute

const val rollDetailsStatsNavigationRoute = "roll_details_stats_route"

fun NavController.navigateToRollDetailsStatsScreen(navOptions: NavOptions? = null) {
  this.navigate(rollDetailsStatsNavigationRoute, navOptions)

}

fun NavGraphBuilder.rollDetailsStatsScreen() {
  composable(route = rollDetailsStatsNavigationRoute) {
    RollDetailsStatsRoute()
  }
}
