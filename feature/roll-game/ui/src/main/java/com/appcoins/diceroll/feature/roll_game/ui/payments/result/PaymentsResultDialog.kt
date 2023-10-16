package com.appcoins.diceroll.feature.roll_game.ui.payments.result

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appcoins.diceroll.core.design.theme.DiceRollTheme
import com.appcoins.diceroll.feature.roll_game.ui.payments.options.PaymentsOptionsState
import com.appcoins.diceroll.feature.roll_game.ui.payments.options.PaymentsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsResultRoute(onDismiss: () -> Unit) {
  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
  ) {
    PaymentsResultDialogContent()
  }
}


@Composable
fun PaymentsResultDialogContent() {

}

@Preview
@Composable
fun PreviewPaymentsDialog() {
  DiceRollTheme(darkTheme = true) {
    PaymentsResultDialogContent()
  }
}
