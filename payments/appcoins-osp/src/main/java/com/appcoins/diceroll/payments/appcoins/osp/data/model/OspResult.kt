package com.appcoins.diceroll.payments.appcoins.osp.data.model

data class OspCallbackResult(
  val result: OspCallbackState,
)

enum class OspCallbackState {
  COMPLETED,
  FAILED,
  CANCELED,
  PENDING,
}