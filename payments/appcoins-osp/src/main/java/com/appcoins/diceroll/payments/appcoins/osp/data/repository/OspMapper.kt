package com.appcoins.diceroll.payments.appcoins.osp.data.repository

import com.appcoins.diceroll.core.network.modules.model.OspCallbackResultResponse
import com.appcoins.diceroll.core.network.modules.model.OspUrlResponse
import com.appcoins.diceroll.payments.appcoins.osp.data.model.OspCallbackResult
import com.appcoins.diceroll.payments.appcoins.osp.data.model.OspCallbackState
import com.appcoins.diceroll.payments.appcoins.osp.data.model.OspUrl

fun OspUrlResponse.toOspUrl(): OspUrl {
  return OspUrl(
    url = this.url,
    orderReference = this.orderReference
  )
}

fun OspCallbackResultResponse.toOspCallbackResult(): OspCallbackResult {
  return OspCallbackResult(
    status = this.status.toOspCallbackState()
  )
}

fun String.toOspCallbackState(): OspCallbackState {
  return when (this) {
    "COMPLETED" -> OspCallbackState.COMPLETED
    "FAILED" -> OspCallbackState.FAILED
    "CANCELED" -> OspCallbackState.CANCELED
    "PENDING" -> OspCallbackState.PENDING
    else -> OspCallbackState.FAILED
  }
}