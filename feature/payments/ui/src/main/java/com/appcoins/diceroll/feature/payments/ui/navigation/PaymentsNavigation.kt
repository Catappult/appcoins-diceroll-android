package com.appcoins.diceroll.feature.payments.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navOptions
import com.appcoins.diceroll.core.navigation.destinations.DestinationArgs
import com.appcoins.diceroll.core.navigation.destinations.Destinations
import com.appcoins.diceroll.core.navigation.buildDestinationRoute
import com.appcoins.diceroll.core.navigation.navigateToDestination
import com.appcoins.diceroll.core.utils.extensions.ifLet
import com.appcoins.diceroll.feature.payments.ui.PaymentsBottomSheetRoute


fun NavController.navigateToPaymentsBottomSheet(
  itemId: String,
  attempts : String,
) {
  this.navigateToDestination(
    destination = Destinations.BottomSheet.Payments,
    destinationArgs = listOf(itemId, attempts),
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
fun NavGraphBuilder.paymentsRoute(onDismiss: () -> Unit) {
  this.buildDestinationRoute(
    destination = Destinations.BottomSheet.Payments,
    destinationArgs = listOf(DestinationArgs.ItemId, DestinationArgs.AttemptsLeft),
  ) { args ->
    ifLet(args[DestinationArgs.ItemId], args[DestinationArgs.AttemptsLeft]) { (itemId, attempts) ->
      PaymentsBottomSheetRoute(onDismiss, itemId, attempts)
    }
  }
}
