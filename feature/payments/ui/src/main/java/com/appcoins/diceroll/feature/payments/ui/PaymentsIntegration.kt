package com.appcoins.diceroll.feature.payments.ui

/**
 * A sealed hierarchy describing the type of payment integration for the chosen item.
 */
sealed interface PaymentsIntegration {

  /**
   * The integration is with AppCoins SDK.
   */
  data object SDK : PaymentsIntegration

  /**
   * The integration is with One Step Payment url.
   */
  data object OSP : PaymentsIntegration
}
