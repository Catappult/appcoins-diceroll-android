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

/**
 * Payment dialog route, that receives a [itemId] that is the SKU for the item to buy.
 * This item could be the Item instead of the string but its not recommended to use complex data
 * when navigating, according to the official android documentation.
 * Doing so creates problems, such as the route not being able to be parsed by the navigation
 * since it would need either a Parcelable or a Serializable and that issue wont be fixed as seen
 * in the issue tracker below.
 *
 * @see <a href="https://issuetracker.google.com/issues/148523779">Issue Tracker</a>
 *
 */
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
