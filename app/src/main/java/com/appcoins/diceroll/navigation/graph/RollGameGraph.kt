package com.appcoins.diceroll.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.appcoins.diceroll.feature.payments.ui.navigation.navigateToPaymentsDialog
import com.appcoins.diceroll.feature.payments.ui.navigation.paymentsDialog
import com.appcoins.diceroll.feature.payments.ui.toSku
import com.appcoins.diceroll.feature.roll_game.ui.navigation.rollGameScreen

internal fun NavGraphBuilder.rollGameGraph(navController: NavHostController) {
  rollGameScreen(onBuyClick = { item ->
    navController.navigateToPaymentsDialog(item.toSku())
  })
  paymentsDialog(onDismiss = {
    navController.navigateUp()
  })
}