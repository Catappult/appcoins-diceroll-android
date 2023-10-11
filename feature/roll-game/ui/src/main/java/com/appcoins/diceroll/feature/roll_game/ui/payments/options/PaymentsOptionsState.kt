package com.appcoins.diceroll.feature.roll_game.ui.payments.options

/**
 * A sealed hierarchy describing the payments options dialog state for the roll game feature.
 * This is used to control the visibility of the dialog.
 */
sealed interface PaymentsOptionsState {

  /**
   * The dialog is closed or the user dismissed it.
   */
  data object Closed : PaymentsOptionsState

  /**
   * The dialog is opened.
   */
  data object Opened : PaymentsOptionsState

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
