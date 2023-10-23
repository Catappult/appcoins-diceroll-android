package com.appcoins.diceroll.payments.appcoins.osp.data.repository

import com.appcoins.diceroll.core.network.model.OspCallbackResultResponse
import com.appcoins.diceroll.core.network.model.OspCallbackStateResponse
import com.appcoins.diceroll.core.network.model.OspUrlResponse
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
    result = this.result.toOspCallbackState()
  )
}

fun OspCallbackStateResponse.toOspCallbackState(): OspCallbackState {
  return when (this) {
    OspCallbackStateResponse.COMPLETED -> OspCallbackState.COMPLETED
    OspCallbackStateResponse.FAILED -> OspCallbackState.FAILED
    OspCallbackStateResponse.CANCELED -> OspCallbackState.CANCELED
    OspCallbackStateResponse.PENDING -> OspCallbackState.PENDING
  }
}