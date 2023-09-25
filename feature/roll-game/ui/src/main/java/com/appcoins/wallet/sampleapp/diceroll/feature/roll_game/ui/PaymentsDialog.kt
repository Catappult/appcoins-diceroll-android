package com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appcoins.wallet.sampleapp.diceroll.core.design.theme.DiceRollTheme
import com.appcoins.wallet.sampleapp.diceroll.core.utils.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsDialog(context: Context, onDismiss: () -> Unit) {
  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
  ) {
    PaymentDialogContent(context)
  }
}

@Composable
fun PaymentDialogContent(context: Context) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 32.dp)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = stringResource(id = R.string.payments_info),
      fontSize = 12.sp,
    )
    Button(onClick = { }) {
      Text(text = stringResource(id = R.string.payments_buy_sdk_button))
    }
    Button(onClick = { }) {
      Text(text = stringResource(id = R.string.payments_buy_osp_button))
    }
  }
}


//fun launchBillingSdkFlow(activity: Activity, billingManager: BillingManager?) {
////  billingManager?.cab?.launchBillingFlow(activity, billingManager.billingFlowParams)
//  Log.d("CUSTOM_TAG", "PaymentOptionsScreen: launchBillingSdkFlow: ")
//  TODO("Not yet implemented")
//}

@Preview
@Composable
fun PreviewPaymentsDialog() {
  DiceRollTheme(darkTheme = true) {
    PaymentDialogContent(LocalContext.current)
  }
}
