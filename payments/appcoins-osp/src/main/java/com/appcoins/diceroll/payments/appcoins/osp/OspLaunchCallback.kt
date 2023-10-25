package com.appcoins.diceroll.payments.appcoins.osp

interface OspLaunchCallback {
  fun onSuccess(orderReference: String)
  fun onError(error: String)
}
