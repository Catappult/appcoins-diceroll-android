package com.appcoins.diceroll.payments.appcoins.osp

interface OspLaunchCallback {
  fun onSuccess(orderReference: Result<String>)
  fun onError(error: Result<String>)
}
