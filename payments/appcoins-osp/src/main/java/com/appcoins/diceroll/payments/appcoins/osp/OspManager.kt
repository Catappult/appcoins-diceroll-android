package com.appcoins.diceroll.payments.appcoins.osp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import com.appcoins.diceroll.core.utils.walletPackage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class OspManager @Inject constructor(val ospRepository: OspRepository) {

  fun launchOsp(activity: Activity, product: String) {
    Log.d("OSP_FLOW", "OspManager: invoke: product = $product")

    val scope = CoroutineScope(Dispatchers.IO)

    try {
      scope.launch {
        val ospUrl = ospRepository.getOspUrl(product)
        Log.d("OSP_FLOW", "OspManager: invoke: ospUrl = $ospUrl")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(ospUrl)

        if (isAppCoinsWalletInstalled(activity)) {
          intent.setPackage(walletPackage)
        }
        activity.startActivityForResult(intent, 10003)
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun isAppCoinsWalletInstalled(activity: Activity): Boolean {
    val packageManager = activity.applicationContext.packageManager
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