package com.appcoins.diceroll.core.navigation.destinations

object DestinationArgs {

  /**
   * Argument to be passed to [Destinations.BottomSheet.Payments] representing the item sku to
   * be used in the payment.
   */
  const val ItemId = "item_id"

  /**
   * Argument to be passed to [Destinations.BottomSheet.Payments] representing the number of
   * attempt left when the payment bottom sheet was called.
   */
  const val AttemptsLeft = "attempts_left"
}