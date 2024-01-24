package com.appcoins.diceroll.core.utils.extensions

import android.content.pm.PackageManager
import android.os.Build
import com.appcoins.diceroll.core.utils.diceRollPackage

fun PackageManager.getInstallerInfo(): String? {
  return try {
    val installerPackageName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      getInstallSourceInfo(diceRollPackage).installingPackageName
    } else {
      @Suppress("DEPRECATION")
      getInstallerPackageName(diceRollPackage)
    }

    installerPackageName?.takeIf { isAppInstalled(it) }
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }
}

fun PackageManager.isAppInstalled(packageName: String): Boolean {
  return try {
    getPackageInfo(packageName, PackageManager.GET_META_DATA)
    true
  } catch (e: PackageManager.NameNotFoundException) {
    false
  }
}