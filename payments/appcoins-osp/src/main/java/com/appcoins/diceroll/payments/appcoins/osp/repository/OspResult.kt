package com.appcoins.diceroll.payments.appcoins.osp.repository

data class OspCallbackResult(
  val result: OspCallbackState
)

enum class OspCallbackState {
  COMPLETED,
  FAILED,
  CANCELED,
  PENDING,
}