package com.appcoins.wallet.sampleapp.diceroll.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui.navigation.rollGameNavigationRoute
import com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui.navigation.rollGameScreen
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui.navigation.navigateToRollDetailsStatsScreen
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui.navigation.rollDetailsStatsScreen
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui.navigation.statsScreen

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
