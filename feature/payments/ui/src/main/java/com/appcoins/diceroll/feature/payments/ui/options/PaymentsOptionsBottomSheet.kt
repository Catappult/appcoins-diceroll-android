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
import com.appcoins.diceroll.payments.appcoins_sdk.SdkManagerImpl

@Composable
fun PaymentsOptions(
  itemId: String,
  onPaymentClick: (PaymentsIntegration) -> Unit,
) {
  val context = LocalContext.current as Activity
  PaymentsOptionsContent(
    context = context,
    itemId = itemId,
    onResultPayment = onPaymentClick
  )
}

@Composable
fun PaymentsOptionsContent(
  context: Context,
  ospManager: OspManager = requireOspManagerEntryPoint().ospManager,
  itemId: String,
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
      launchBillingSdkFlow(context, itemId, onResultPayment)
    }) {
      Text(text = stringResource(id = R.string.payments_buy_sdk_button)
              + (getAttemptsPrice()?.let { "\n${it}" } ?: ""))
    }
    Button(onClick = {
      launchBillingOspFlow(ospManager, itemId, onResultPayment, context)
    }) {
      Text(text = stringResource(id = R.string.payments_buy_osp_button))
    }
  }
}

fun launchBillingSdkFlow(
  context: Context,
  itemId: String,
  onResultPayment: (PaymentsIntegration) -> Unit,
) {
  SdkManagerImpl.startPayment(context, itemId, "")
  onResultPayment(PaymentsIntegration.SDK)
}

fun launchBillingOspFlow(
  ospManager: OspManager,
  itemId: String,
  onResultPayment: (PaymentsIntegration) -> Unit,
  context: Context,
) {
  val ospCallback = object : OspLaunchCallback {
    override fun onSuccess(orderReference: Result<String>) {
      onResultPayment(PaymentsIntegration.OSP(orderReference))
    }

    override fun onError(error: Result<String>) {
      onResultPayment(PaymentsIntegration.OSP(error))
    }
  }
  ospManager.startPayment(context as Activity, itemId, ospCallback)
}

private fun getAttemptsPrice() : String? =
  SdkManagerImpl.attemptsPrice

@Preview
@Composable
fun PreviewPaymentsOptionsContent() {
  DiceRollTheme(darkTheme = true) {
    PaymentsOptionsContent(
      context = LocalContext.current,
      itemId = "attempts",
      onResultPayment = {}
    )
  }
}
