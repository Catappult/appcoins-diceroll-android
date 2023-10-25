package com.appcoins.diceroll.feature.payments.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navOptions
import com.appcoins.diceroll.core.navigation.destinations.DestinationArgs
import com.appcoins.diceroll.core.navigation.destinations.Destinations
import com.appcoins.diceroll.core.navigation.buildScreen
import com.appcoins.diceroll.core.navigation.navigateToDestination
import com.appcoins.diceroll.feature.payments.ui.PaymentsDialogRoute


fun NavController.navigateToPaymentsDialog(
  itemId: String,
) {
  this.navigateToDestination(
    destination = Destinations.BottomSheet.Payments,
    destinationArgs = listOf(itemId),
    navOptions = navOptions {
      launchSingleTop = true
    }
  )
}

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.paymentsDialog(onDismiss: () -> Unit) {
  this.buildScreen(
    destination = Destinations.BottomSheet.Payments,
    destinationArgs = listOf(DestinationArgs.ItemId),
  ) { args ->
    args[DestinationArgs.ItemId]?.let {
      PaymentsDialogRoute(onDismiss, it)
    }
  }
}
