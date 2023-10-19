package com.appcoins.diceroll.payments.appcoins.osp.repository

import com.appcoins.diceroll.core.network.model.OspCallbackResultResponse
import com.appcoins.diceroll.core.network.model.OspCallbackStateResponse

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