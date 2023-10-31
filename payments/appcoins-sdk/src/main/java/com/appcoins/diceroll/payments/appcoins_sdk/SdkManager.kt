package com.appcoins.diceroll.payments.appcoins_sdk

import android.app.Activity
import android.content.Context
import android.util.Log
import com.appcoins.sdk.billing.AppcoinsBillingClient
import com.appcoins.sdk.billing.BillingFlowParams
import com.appcoins.sdk.billing.Purchase
import com.appcoins.sdk.billing.PurchasesUpdatedListener
import com.appcoins.sdk.billing.ResponseCode
import com.appcoins.sdk.billing.SkuDetailsParams
import com.appcoins.sdk.billing.listeners.AppCoinsBillingStateListener
import com.appcoins.sdk.billing.listeners.ConsumeResponseListener
import com.appcoins.sdk.billing.listeners.SkuDetailsResponseListener
import com.appcoins.sdk.billing.types.SkuType

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
interface SdkManager {

  val context: Context

  /**
   * The AppCoins billing client instance.
   */
  val cab: AppcoinsBillingClient

  /**
   * Listener for AppCoins billing client state changes.
   *
   * This listener handles events related to the connection state
   * of the AppCoins billing client and has two methods to act on connection and
   * disconnection events.
   *
   * @param responseCode The response code from the billing client
   */
  val appCoinsBillingStateListener: AppCoinsBillingStateListener
    get() =
      object : AppCoinsBillingStateListener {
        override fun onBillingSetupFinished(responseCode: Int) {
          when (responseCode) {
            ResponseCode.OK.value -> {
              queryPurchases()
              queryInapps(ArrayList(listOf("attempts")))
              Log.d(Companion.logTAG, "AppCoinsBillingStateListener: AppCoins SDK Setup successful. Querying inventory.")
            }

            else -> {
              Log.d(
                Companion.logTAG,
                "AppCoinsBillingStateListener: Problem setting up AppCoins SDK: ${responseCode.toResponseCode()}"
              )
            }
          }
        }

        override fun onBillingServiceDisconnected() {
          Log.d(Companion.logTAG, "AppCoinsBillingStateListener: AppCoins SDK Disconnected")
        }
      }

  /**
   * Listener that gets called when purchases are updated.
   *
   * This listener handles the response codes and purchase data
   * from the billing client after a purchase flow completes.
   *
   * It will be called with the response code and list of purchases.
   * Based on the response code, it can process the purchases or
   * handle errors.
   *
   * @param responseCode The response code from the billing client
   * @param purchases The list of Purchase objects with the purchase data
   */
  val purchasesUpdatedListener: PurchasesUpdatedListener
    get() = PurchasesUpdatedListener { responseCode: Int, purchases: List<Purchase> ->
      when (responseCode) {
        ResponseCode.OK.value -> {
          for (purchase in purchases) {
            cab.consumeAsync(purchase.token, consumeResponseListener)
          }
        }

        else -> {
          Log.d(logTAG, "PurchasesUpdatedListener: response ${responseCode.toResponseCode()}")
        }
      }
    }

  /**
   * Listener for handling consume purchase responses.
   *
   * This listener receives the response code and purchase token
   * after consuming a purchase with the AppCoins billing client.
   *
   * It can be used to determine if the consumption was successful.
   *
   * @param responseCode The response code from consuming purchase
   * @param purchaseToken The token of the consumed purchase
   */
  val consumeResponseListener: ConsumeResponseListener
    get() =
      ConsumeResponseListener { responseCode, purchaseToken ->
        Log.d(
          logTAG,
          "ConsumeResponseListener: Consumption finished. Purchase: $purchaseToken, result: $responseCode"
        )
      }

  /**
   * Listener for SKU details responses.
   *
   * Called when the requested SKU details are retrieved from the
   * AppCoins billing client.
   *
   * The SKU details list contains the details about each SKU.
   * This can be used to show SKU information in the app UI.
   *
   * @param responseCode The response code from the billing client
   * @param skuDetailsList List of SkuDetails objects
   */
  val skuDetailsResponseListener: SkuDetailsResponseListener
    get() =
      SkuDetailsResponseListener { responseCode, skuDetailsList ->
        for (sku in skuDetailsList) {
          Log.d(logTAG, "SkuDetailsResponseListener: item response ${responseCode.toResponseCode()}, sku $sku")
          // You can add these details to a list in order to update
          // UI or use it in any other way
        }
      }

  private fun queryPurchases() {
    val purchasesResult = cab.queryPurchases(SkuType.inapp.toString())
    val purchases = purchasesResult.purchases
    for (purchase in purchases) {
      cab.consumeAsync(purchase.token, consumeResponseListener)
    }
  }

  private fun queryInapps(skuList: List<String>) {
    cab.querySkuDetailsAsync(
      SkuDetailsParams().apply {
        itemType = SkuType.inapp.toString()
        moreItemSkus = skuList
      },
      skuDetailsResponseListener
    )
  }

  /**
   * Starts the payment flow for the given SKU.
   *
   * @param sku The SKU identifier for the in-app product.
   * @param developerPayload A developer-defined string that will be returned with the purchase data.
   *
   * This will launch the Google Play billing flow. The result will be delivered
   * via the PurchasesUpdatedListener callback.
   */
  fun startPayment(sku: String, developerPayload: String): Result<Int> {
    val billingFlowParams = BillingFlowParams(
      sku,
      SkuType.inapp.toString(),
      "orderId=" + System.currentTimeMillis(),
      developerPayload,
      "BDS"
    )

    return runCatching {
      cab.launchBillingFlow(context as Activity, billingFlowParams)
    }.onSuccess {
      Log.d(logTAG, "StartPayment: Payment started with response code: ${it.toResponseCode()}")
    }.onFailure {
      Log.e(logTAG, "StartPayment: Error starting payment: ${it.message}")
    }
  }

  companion object {
    const val logTAG: String = "SdkManager"
  }
}
