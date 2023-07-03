package com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui.RollGameRoute


const val rollGameNavigationRoute = "dice_roll_game_route"

fun NavController.navigateToRollGame(navOptions: NavOptions? = null) {
  this.navigate(rollGameNavigationRoute, navOptions)

}

fun NavGraphBuilder.rollGameScreen(onClick: (String) -> Unit) {
  composable(route = rollGameNavigationRoute) {
    RollGameRoute()
  }
}
