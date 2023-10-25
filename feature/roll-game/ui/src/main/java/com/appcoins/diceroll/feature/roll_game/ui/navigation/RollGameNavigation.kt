package com.appcoins.diceroll.feature.roll_game.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.appcoins.diceroll.core.navigation.destinations.Destinations
import com.appcoins.diceroll.core.navigation.buildScreen
import com.appcoins.diceroll.core.navigation.navigateToDestination
import com.appcoins.diceroll.feature.payments.ui.Item
import com.appcoins.diceroll.feature.roll_game.ui.RollGameRoute

fun NavController.navigateToRollGame(navOptions: NavOptions) {
  this.navigateToDestination(
    destination = Destinations.Screen.RollGame,
    navOptions = navOptions
  )
}

fun NavGraphBuilder.rollGameScreen(onBuyClick: (Item) -> Unit) {
  this.buildScreen(
    destination = Destinations.Screen.RollGame,
  ) {
    RollGameRoute(onBuyClick)
  }
}
