package com.appcoins.diceroll.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.appcoins.diceroll.feature.payments.ui.navigation.navigateToPaymentsDialog
import com.appcoins.diceroll.feature.payments.ui.navigation.paymentsRoute
import com.appcoins.diceroll.feature.payments.ui.toSku
import com.appcoins.diceroll.feature.roll_game.ui.navigation.rollGameRoute

internal fun NavGraphBuilder.rollGameGraph(navController: NavHostController) {
  rollGameRoute(onBuyClick = { item ->
    navController.navigateToPaymentsDialog(item.toSku())
  })
  paymentsRoute(onDismiss = {
    navController.navigateUp()
  })
}