package com.appcoins.diceroll.feature.settings.data.repository

import com.appcoins.diceroll.core.network.modules.model.StoreDeeplinkResponse
import com.appcoins.diceroll.feature.settings.data.model.StoreDeeplink

fun StoreDeeplinkResponse.toStoreDeeplink(): StoreDeeplink {
  return StoreDeeplink(
    url = this.url,
  )
}
