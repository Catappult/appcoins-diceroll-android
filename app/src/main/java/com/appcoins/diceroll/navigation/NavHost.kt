package com.appcoins.diceroll.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.appcoins.diceroll.feature.roll_game.ui.navigation.rollGameNavigationRoute
import com.appcoins.diceroll.feature.roll_game.ui.navigation.rollGameScreen
import com.appcoins.diceroll.feature.stats.ui.navigation.navigateToRollDetailsStatsScreen
import com.appcoins.diceroll.feature.stats.ui.navigation.rollDetailsStatsScreen
import com.appcoins.diceroll.feature.stats.ui.navigation.statsScreen

@Composable
fun DiceRollNavHost(
  navController: NavHostController,
  startDestination: String = rollGameNavigationRoute,
  scaffoldPadding: PaddingValues
) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
    modifier = Modifier.padding(scaffoldPadding)
  ) {
    rollGameScreen()
    statsScreen(onDetailsClick = {
      navController.navigateToRollDetailsStatsScreen()
    })
    rollDetailsStatsScreen()
  }
}
