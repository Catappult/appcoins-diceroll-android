package com.appcoins.diceroll.core.utils

const val ospUrl = "https://osp.diceroll.catappult.io"
const val storeDeepLinkUrl = "https://store-link-mapper.aptoide.com"

val walletPackage = if(BuildConfig.DEBUG) "com.appcoins.wallet.dev" else "com.appcoins.wallet"
val diceRollPackage = if(BuildConfig.DEBUG) "com.appcoins.diceroll.dev" else "com.appcoins.diceroll"