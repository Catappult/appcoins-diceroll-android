package com.appcoins.diceroll.payments.appcoins_sdk

import android.content.Context
import android.content.Intent
import android.util.Log
import com.appcoins.sdk.billing.AppcoinsBillingClient
import com.appcoins.sdk.billing.Purchase
import com.appcoins.sdk.billing.PurchasesUpdatedListener
import com.appcoins.sdk.billing.ResponseCode
import com.appcoins.sdk.billing.helpers.CatapultBillingAppCoinsFactory

/**
 * Manages the AppCoins SDK integration for in-app billing.
 *
 * This class initializes the AppCoins billing client, sets up
 * listeners for billing events, and provides methods to interact
 * with the billing service.
 *
 * It serves as a wrapper around the AppCoins SDK to handle all the
 * necessary setup and provide callbacks to the app for billing events
 * in order to simplify the call for it.
 *
 */
class SdkManagerImpl(override val context: Context) : SdkManager {
  private val base64EncodedPublicKey: String = BuildConfig.CATAPPULT_PUBLIC_KEY

  override val purchasesUpdatedListener =
    PurchasesUpdatedListener { responseCode: Int, purchases: List<Purchase> ->
      run {
        when (responseCode) {
          ResponseCode.OK.value -> {
            for (purchase in purchases) {
              Log.d(logTAG, "purchasesUpdatedListener for loop Purchases: $purchases")

              cab.consumeAsync(purchase.token, consumeResponseListener)
            }
            // Handle the result
          }

          ResponseCode.USER_CANCELED.value -> {
            Log.d(logTAG, "purchasesUpdatedListener User canceled")
          }

          else -> {
            Log.d(logTAG, "purchasesUpdatedListener Error: ${responseCode.toResponseCode()}")
          }
        }
      }
    }

  override val cab: AppcoinsBillingClient = CatapultBillingAppCoinsFactory.BuildAppcoinsBilling(
    context,
    base64EncodedPublicKey,
    purchasesUpdatedListener
  )

  init {
    cab.startConnection(appCoinsBillingStateListener)
    Log.d(logTAG, "Interface: SdkManager: cab $cab started, is ready ${cab.isReady}")
  }

  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    cab.onActivityResult(requestCode, resultCode, data)
  }
}