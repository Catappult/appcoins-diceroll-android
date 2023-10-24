package com.appcoins.diceroll.feature.roll_game.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.appcoins.diceroll.feature.payments.ui.Item
import com.appcoins.diceroll.feature.roll_game.ui.RollGameRoute


const val rollGameNavigationRoute = "dice_roll_game_route"

fun NavController.navigateToRollGame(navOptions: NavOptions? = null) {
  this.navigate(rollGameNavigationRoute, navOptions)

}

fun NavGraphBuilder.rollGameScreen(onBuyClick: (Item) -> Unit) {
  composable(route = rollGameNavigationRoute) {
    RollGameRoute(onBuyClick)
  }
}
