package com.appcoins.diceroll.payments.appcoins.osp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.appcoins.diceroll.core.utils.gamesHubPackage
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
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ospUrl)).apply {
        getSupportedServicePackage(context, ospUrl)?.let { packageName ->
          setPackage(packageName)
        }
        addCategory(Intent.CATEGORY_BROWSABLE)
      }
      (context as Activity).startActivityForResult(intent, 10003)
      callback.onSuccess(orderReference = Result.success(ospOrderReference))
    }.onFailure {
      callback.onError(error = Result.failure(it))
    }
  }

  private fun getSupportedServicePackage(context: Context, ospUrl: String): String? {
    val packageManager = (context as Activity).packageManager
    val packagesToCheck = listOf(walletPackage, gamesHubPackage)
    return packagesToCheck.find { packageName ->
      val isPackageInstalled = runCatching {
        packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
      }.isSuccess

      if (isPackageInstalled) {
        val intentForCheck = Intent(Intent.ACTION_VIEW).apply {
          data = Uri.parse(ospUrl)
          `package` = packageName
        }
        intentForCheck.resolveActivity(packageManager) != null
      } else {
        false
      }
    }
  }
}
