package com.appcoins.diceroll.feature.roll_game.ui.payments.result

/**
 * A sealed hierarchy describing the payments results dialog state for the roll game feature,
 * as a result of the Payment dialog with whichever integration is used.
 * This is used to control the visibility of the dialog.
 */
sealed interface PaymentsResultState {

  /**
   * The result dialog is closed or the user dismissed it.
   */
  data object Closed : PaymentsResultState

  /**
   * The result dialog is opened.
   */
  data object Opened : PaymentsResultState

  /**
   * The result dialog is opened and loading its content.
   */
  data object Loading : PaymentsResultState

  /**
   * The result dialog is opened, but the user exited the payment dialog.
   */
  data object UserCanceledPayment : PaymentsResultState

  /**
   * The result dialog is opened, but the payment was not successful .
   */
  data object FailedPayment : PaymentsResultState

  /**
   * The result dialog is opened and the payment was successful.
   */
  data object SuccessfulPayment : PaymentsResultState
}
