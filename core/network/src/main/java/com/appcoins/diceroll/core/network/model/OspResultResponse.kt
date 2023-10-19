package com.appcoins.diceroll.core.network.model

data class OspCallbackResultResponse(
  val result: OspCallbackStateResponse,
)

enum class OspCallbackStateResponse {
  COMPLETED,
  FAILED,
  CANCELED,
  PENDING,
}