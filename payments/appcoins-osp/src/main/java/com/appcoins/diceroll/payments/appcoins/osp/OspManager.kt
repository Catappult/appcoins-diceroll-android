package com.appcoins.diceroll.payments.appcoins.osp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import com.appcoins.diceroll.core.utils.walletPackage
import com.appcoins.diceroll.payments.appcoins.osp.repository.OspRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class OspManager @Inject constructor(private val ospRepository: OspRepository) {

  fun launchOsp(context: Context, product: String) {
    Log.d("OSP_FLOW", "OspManager: invoke: product = $product")

    val scope = CoroutineScope(Dispatchers.IO)

    try {
      scope.launch {
        val ospUrl = ospRepository.getOspUrl(product)
        Log.d("OSP_FLOW", "OspManager: invoke: ospUrl = $ospUrl")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(ospUrl)

        if (isAppCoinsWalletInstalled(context)) {
          intent.setPackage(walletPackage)
        }
        (context as Activity).startActivityForResult(intent, 10003)
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun isAppCoinsWalletInstalled(context: Context): Boolean {
    val packageManager = (context as Activity).applicationContext.packageManager
    val intentForCheck = Intent(Intent.ACTION_VIEW)
    if (intentForCheck.resolveActivity(packageManager) != null) {
      try {
        packageManager.getPackageInfo(walletPackage, PackageManager.GET_ACTIVITIES)
        return true
      } catch (e: PackageManager.NameNotFoundException) {
      }
    }

    return false
  }
}