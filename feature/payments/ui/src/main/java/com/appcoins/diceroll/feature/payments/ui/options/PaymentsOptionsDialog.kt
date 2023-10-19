package com.appcoins.diceroll.feature.payments.ui.options

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appcoins.diceroll.core.ui.design.theme.DiceRollTheme
import com.appcoins.diceroll.core.utils.R
import com.appcoins.diceroll.feature.payments.ui.PaymentsIntegration
import com.appcoins.diceroll.feature.payments.ui.result.PaymentsResultUiState
import com.appcoins.diceroll.payments.appcoins.osp.OspManager
import com.appcoins.diceroll.payments.appcoins.osp.requireOspManagerEntryPoint
import com.appcoins.diceroll.payments.appcoins_sdk.SdkManager
import com.appcoins.diceroll.payments.appcoins_sdk.SdkManagerImpl
import kotlin.random.Random

@Composable
fun PaymentsOptions(
  onPaymentClick: (PaymentsIntegration) -> Unit,
) {
  val context = LocalContext.current
  val sdkManager = SdkManagerImpl(context)
  PaymentsOptionsContent(
    context = context,
    sdkManager = sdkManager,
    onResultPayment = onPaymentClick
  )
}

@Composable
fun PaymentsOptionsContent(
  context: Context,
  sdkManager: SdkManager,
  ospManager: OspManager = requireOspManagerEntryPoint().ospManager,
  onResultPayment: (PaymentsIntegration) -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 32.dp)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Log.d("OSP_FLOW", "Compose PaymentsOptionsContent: ")
    Text(
      text = stringResource(id = R.string.payments_info),
      fontSize = 12.sp,
    )
    Button(onClick = {
      launchBillingSdkFlow(sdkManager)
      onResultPayment(PaymentsIntegration.SDK)
    }) {
      Text(text = stringResource(id = R.string.payments_buy_sdk_button))
    }
    Button(onClick = {
      launchBillingOspFlow(ospManager, context)
      onResultPayment(PaymentsIntegration.OSP)
    }) {
      Text(text = stringResource(id = R.string.payments_buy_osp_button))
    }
  }
}

fun launchBillingSdkFlow(sdkManager: SdkManager) {
  sdkManager.startPayment("attempts", "")
}

fun launchBillingOspFlow(ospManager: OspManager, context: Context) {
  Log.d("OSP_FLOW", "Compose launchBillingOspFlow: ")
  ospManager.launchOsp(context as Activity, "attempts")
}

fun testResultCall(onResultPayment: (PaymentsResultUiState) -> Unit) {
  when (Random.nextInt(0, 3)) {
    0 -> onResultPayment(PaymentsResultUiState.Success)
    1 -> onResultPayment(PaymentsResultUiState.Failed)
    2 -> onResultPayment(PaymentsResultUiState.UserCanceled)
  }
}

@Preview
@Composable
fun PreviewPaymentsDialog() {
  DiceRollTheme(darkTheme = true) {
    PaymentsOptionsContent(
      context = LocalContext.current,
      sdkManager = SdkManagerImpl(LocalContext.current as Activity),
      onResultPayment = {}
    )
  }
}
