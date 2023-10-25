package com.appcoins.diceroll.feature.stats.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.appcoins.diceroll.core.navigation.destinations.Destinations
import com.appcoins.diceroll.core.navigation.buildScreen
import com.appcoins.diceroll.core.navigation.navigateToDestination
import com.appcoins.diceroll.feature.stats.data.model.DiceRoll
import com.appcoins.diceroll.feature.stats.ui.StatsRoute

fun NavController.navigateToStatsScreen(navOptions: NavOptions) {
  this.navigateToDestination(
    destination = Destinations.Screen.Stats,
    navOptions = navOptions
  )
}

fun NavGraphBuilder.statsScreen(onDetailsClick: (List<DiceRoll>) -> Unit) {
  this.buildScreen(
    destination = Destinations.Screen.Stats,
  ) {
    StatsRoute(onDetailsClick)
  }
}
