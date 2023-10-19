package com.appcoins.diceroll.feature.payments.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.appcoins.diceroll.feature.payments.ui.PaymentsDialogRoute
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

const val paymentsNavigationRoute = "payments_dialog_route"

fun NavController.navigateToPaymentsDialog(navOptions: NavOptions? = null) {
  this.navigate(paymentsNavigationRoute, navOptions)
}

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.paymentsDialog(onDismiss: () -> Unit) {
  bottomSheet(route = paymentsNavigationRoute) {
    PaymentsDialogRoute(onDismiss)
  }
}
