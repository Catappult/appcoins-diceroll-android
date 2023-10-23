package com.appcoins.diceroll.feature.payments.ui.options

import android.app.Activity
import android.content.Context
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
import com.appcoins.diceroll.payments.appcoins.osp.OspLaunchCallback
import com.appcoins.diceroll.payments.appcoins.osp.OspManager
import com.appcoins.diceroll.payments.appcoins.osp.requireOspManagerEntryPoint
import com.appcoins.diceroll.payments.appcoins_sdk.SdkManager
import com.appcoins.diceroll.payments.appcoins_sdk.SdkManagerImpl

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
    Text(
      text = stringResource(id = R.string.payments_info),
      fontSize = 12.sp,
    )
    Button(onClick = {
      launchBillingSdkFlow(sdkManager, onResultPayment)
    }) {
      Text(text = stringResource(id = R.string.payments_buy_sdk_button))
    }
    Button(onClick = {
      launchBillingOspFlow(ospManager, onResultPayment, context)
    }) {
      Text(text = stringResource(id = R.string.payments_buy_osp_button))
    }
  }
}

fun launchBillingSdkFlow(
  sdkManager: SdkManager,
  onResultPayment: (PaymentsIntegration) -> Unit,
) {
  sdkManager.startPayment("attempts", "")
  onResultPayment(PaymentsIntegration.SDK)
}

fun launchBillingOspFlow(
  ospManager: OspManager,
  onResultPayment: (PaymentsIntegration) -> Unit,
  context: Context,
) {
  val ospCallback = object : OspLaunchCallback {
    override fun onSuccess(orderReference: String) {
      onResultPayment(PaymentsIntegration.OSP(orderReference))
    }

    override fun onError(error: String) {
      onResultPayment(PaymentsIntegration.OSP(error))
    }
  }
  ospManager.startPayment(context as Activity, "attempts", ospCallback)
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
