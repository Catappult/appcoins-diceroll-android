package com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui.StatsRoute

const val statsNavigationRoute = "stats_route"

fun NavController.navigateToStatsScreen(navOptions: NavOptions? = null) {
  this.navigate(statsNavigationRoute, navOptions)

}

fun NavGraphBuilder.statsScreen(onDetailsClick: (List<DiceRoll>) -> Unit) {
  composable(route = statsNavigationRoute) {
    StatsRoute(onDetailsClick)
  }
}
