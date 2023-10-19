package com.appcoins.diceroll.feature.payments.ui.options

/**
 * A sealed hierarchy describing the UI payments options content state.
 * This is used to control when to show the available options for the user to pay.
 */
sealed interface PaymentsOptionsUiState {
  /**
   * The dialog is opened and loading its content.
   */
  data object Loading : PaymentsOptionsUiState

  /**
   * The dialog is opened, but there was an Error when loading the content.
   */
  data object Error : PaymentsOptionsUiState

  /**
   * The dialog is opened, but the payment option is not available.
   */
  data object NotAvailable : PaymentsOptionsUiState

  /**
   * The dialog is opened and the payment option is not available.
   */
  data object Available : PaymentsOptionsUiState

  /**
   * The dialog is opened and payment option was selected.
   */
  data object OptionSelected : PaymentsOptionsUiState
}
