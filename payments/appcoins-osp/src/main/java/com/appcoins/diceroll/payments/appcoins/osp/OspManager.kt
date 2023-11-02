package com.appcoins.diceroll.payments.appcoins.osp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.appcoins.diceroll.core.utils.walletPackage
import com.appcoins.diceroll.payments.appcoins.osp.data.repository.OspRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class OspManager @Inject constructor(private val ospRepository: OspRepository) {

  fun startPayment(context: Context, product: String, callback: OspLaunchCallback) {
    CoroutineScope(Dispatchers.IO).launch {
      ospRepository.getOspUrl(product)
        .onSuccess { ospUrl ->
          openPaymentActivity(
            ospUrl = ospUrl.url,
            ospOrderReference = ospUrl.orderReference.orEmpty(),
            context = context,
            callback = callback
          )
        }
        .onFailure {
          callback.onError(error = Result.failure(it))
        }
    }
  }

  private fun openPaymentActivity(
    ospUrl: String,
    ospOrderReference: String,
    context: Context,
    callback: OspLaunchCallback
  ) {
    runCatching {
      val intent = Intent(Intent.ACTION_VIEW)
      intent.data = Uri.parse(ospUrl)

      if (isAppCoinsWalletInstalled(context)) {
        intent.setPackage(walletPackage)
      }
      (context as Activity).startActivity(intent)
      callback.onSuccess(orderReference = Result.success(ospOrderReference))
    }.onFailure {
      callback.onError(error = Result.failure(it))
    }
  }

  private fun isAppCoinsWalletInstalled(context: Context): Boolean {
    val packageManager = (context as Activity).packageManager
    return runCatching {
      packageManager.getPackageInfo(walletPackage, PackageManager.GET_ACTIVITIES)
    }.isSuccess
  }
}