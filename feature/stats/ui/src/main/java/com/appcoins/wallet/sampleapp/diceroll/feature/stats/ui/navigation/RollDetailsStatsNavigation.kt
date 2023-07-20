package com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui.RollDetailsStatsRoute
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui.StatsRoute

const val rollDetailsStatsNavigationRoute = "roll_details_stats_route"

fun NavController.navigateToRollDetailsStatsScreen(navOptions: NavOptions? = null) {
  this.navigate(rollDetailsStatsNavigationRoute, navOptions)

}

fun NavGraphBuilder.rollDetailsStatsScreen(onClick: (String) -> Unit) {
  composable(route = rollDetailsStatsNavigationRoute) {
//    RollDetailsStatsRoute()
  }
}
