package com.appcoins.diceroll.feature.payments.ui

/**
 * A sealed hierarchy describing the payments dialog state.
 * This is used to control the visibility of the dialog.
 */
sealed interface PaymentsDialogState {
  /**
   * The dialog is closed or the user dismissed it.
   */
  data object Closed : PaymentsDialogState

  /**
   * The dialog is opened.
   */
  data object Opened : PaymentsDialogState
}
