package com.appcoins.diceroll.feature.payments.ui.options

/**
 * A sealed hierarchy describing the payments options content state.
 * This is used to control when to show the available options for the user to pay.
 */
sealed interface PaymentsOptionsState {
  /**
   * The dialog is opened and loading its content.
   */
  data object Loading : PaymentsOptionsState

  /**
   * The dialog is opened, but there was an Error when loading the content.
   */
  data object Error : PaymentsOptionsState

  /**
   * The dialog is opened and the content was loaded successfully.
   */
  data class Success(
    val attemptsLeft: Int?
  ) : PaymentsOptionsState
}
