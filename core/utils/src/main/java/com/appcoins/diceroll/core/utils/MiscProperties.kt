package com.appcoins.diceroll.core.utils

const val ospUrl = "https://osp.diceroll.catappult.io"
const val storeDeepLinkUrl = "https://store-link-mapper.aptoide.com"

val diceRollPackage = if(BuildConfig.DEBUG) "com.appcoins.diceroll.dev" else "com.appcoins.diceroll"
val walletPackage = if(BuildConfig.DEBUG) "com.appcoins.wallet.dev" else "com.appcoins.wallet"
val gamesHubPackage = if(BuildConfig.DEBUG) "com.dti.hub.stg" else "com.dti.folderlauncher"
