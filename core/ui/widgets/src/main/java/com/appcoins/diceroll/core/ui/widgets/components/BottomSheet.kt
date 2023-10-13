package com.appcoins.diceroll.core.ui.widgets.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceRollBottomSheet(
  onDismissRequest: () -> Unit,
  bottomSheetState: SheetState = rememberModalBottomSheetState(false),
  content: @Composable ColumnScope.() -> Unit
) {
  ModalBottomSheet(
    onDismissRequest = onDismissRequest,
    sheetState = bottomSheetState,
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 32.dp)
        .imePadding(),
    ) {
      content()
    }
  }
}