package com.appcoins.diceroll.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.appcoins.diceroll.feature.payments.ui.Item
import com.appcoins.diceroll.feature.payments.ui.navigation.navigateToPaymentsBottomSheet
import com.appcoins.diceroll.feature.payments.ui.navigation.paymentsRoute
import com.appcoins.diceroll.feature.payments.ui.toSku
import com.appcoins.diceroll.feature.roll_game.ui.navigation.rollGameRoute

internal fun NavGraphBuilder.rollGameGraph(navController: NavHostController) {
  rollGameRoute(onBuyClick = { item ->
    navController.navigateToPaymentsBottomSheet(
      item.toSku(),
      (item as Item.Attempts).currentAttempts.toString()
    )
  })
  paymentsRoute(onDismiss = {
    navController.navigateUp()
  })
}