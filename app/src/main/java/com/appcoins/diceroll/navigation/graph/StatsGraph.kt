package com.appcoins.diceroll.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.appcoins.diceroll.feature.stats.ui.navigation.navigateToRollDetailsStatsScreen
import com.appcoins.diceroll.feature.stats.ui.navigation.rollDetailsStatsScreen
import com.appcoins.diceroll.feature.stats.ui.navigation.statsScreen

internal fun NavGraphBuilder.statsGraph(navController: NavHostController) {
  statsScreen(onDetailsClick = {
    navController.navigateToRollDetailsStatsScreen()
  })
  rollDetailsStatsScreen()
}