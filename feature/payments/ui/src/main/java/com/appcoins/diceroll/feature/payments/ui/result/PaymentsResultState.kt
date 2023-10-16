package com.appcoins.diceroll.feature.payments.ui.result

/**
 * A sealed hierarchy describing the result state coming from the integration the user used to pay.
 * This is used to show an information about what happened with the payment.
 */
sealed interface PaymentsResultState {

  /**
   * The dialog is opened and the payment integration has not been called yet.
   */
  data object Initialized : PaymentsResultState

  /**
   * The dialog is opened and its waiting for the payment to complete.
   */
  data object Loading : PaymentsResultState

  /**
   * The dialog is opened, but the user exited the payment dialog.
   */
  data object UserCanceled : PaymentsResultState

  /**
   * The dialog is opened, but the payment was not successful.
   */
  data object Failed : PaymentsResultState

  /**
   * The dialog is opened and the payment was successful.
   */
  data object Success : PaymentsResultState
}
