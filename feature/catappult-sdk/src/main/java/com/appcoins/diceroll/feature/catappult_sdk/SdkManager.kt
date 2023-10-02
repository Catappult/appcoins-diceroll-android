package com.appcoins.diceroll.feature.catappult_sdk

import android.app.Activity
import android.content.Context
import android.util.Log
import com.appcoins.sdk.billing.AppcoinsBillingClient
import com.appcoins.sdk.billing.BillingFlowParams
import com.appcoins.sdk.billing.Purchase
import com.appcoins.sdk.billing.PurchasesUpdatedListener
import com.appcoins.sdk.billing.ResponseCode
import com.appcoins.sdk.billing.SkuDetailsParams
import com.appcoins.sdk.billing.helpers.CatapultBillingAppCoinsFactory
import com.appcoins.sdk.billing.listeners.AppCoinsBillingStateListener
import com.appcoins.sdk.billing.listeners.SkuDetailsResponseListener
import com.appcoins.sdk.billing.types.SkuType


class SdkManager(private val context: Context) {

  fun startPayment(sku: String, developerPayload: String) {
    val billingFlowParams = BillingFlowParams(
      sku,
      SkuType.inapp.toString(),
      "orderId=" + System.currentTimeMillis(),
      developerPayload,
      "BDS"
    )

    Log.d(LOG_TAG, "startPayment: cab is ready ${cab.isReady}")
    Thread {
      val responseCode = cab.launchBillingFlow(context as Activity, billingFlowParams)
      Log.d(LOG_TAG, "startPayment Response code: ${responseCode.toResponseCode()}")
    }.start()
  }

  private val appCoinsBillingStateListener = object : AppCoinsBillingStateListener {
    override fun onBillingSetupFinished(responseCode: Int) {
      when (responseCode) {
        ResponseCode.OK.value -> {
          checkPurchases() // Check for pending and/or owned purchases
          queryInapps() // Query in-app sku details
          Log.d(LOG_TAG, "Setup successful. Querying inventory.")
        }

        else -> {
          Log.d(LOG_TAG, "Problem setting up in-app billing: ${responseCode.toResponseCode()}")
        }
      }
    }

    override fun onBillingServiceDisconnected() {
      Log.d(LOG_TAG, "Disconnected")
    }
  }

  private val base64EncodedPublicKey: String = BuildConfig.CATAPPULT_PUBLIC_KEY

  private val purchasesUpdatedListener =
    PurchasesUpdatedListener { responseCode: Int, purchases: List<Purchase> ->
      run {
        when (responseCode) {
          ResponseCode.OK.value -> {
            Log.d(LOG_TAG, "Purchases: $purchases")
            // Handle the result
          }

          ResponseCode.USER_CANCELED.value -> {
            Log.d(LOG_TAG, "User canceled")
          }

          else -> {
            Log.d(LOG_TAG, "Error: ${responseCode.toResponseCode()}")
          }
        }
      }
    }

  private val cab: AppcoinsBillingClient = CatapultBillingAppCoinsFactory.BuildAppcoinsBilling(
    context,
    base64EncodedPublicKey,
    purchasesUpdatedListener
  )

  init {
    cab.startConnection(appCoinsBillingStateListener)
  }

  private val skuDetailsResponseListener =
    SkuDetailsResponseListener { responseCode, skuDetailsList ->
      Log.d(LOG_TAG, "SkuDetailsResponseListener: cab is ready ${cab.isReady}")
      Log.d(LOG_TAG, "Received skus ${responseCode.toResponseCode()} $skuDetailsList")
      for (sku in skuDetailsList) {
        Log.d(LOG_TAG, "sku details: $sku")
        // You can add these details to a list in order to update
        // UI or use it in any other way
      }
    }

  private fun checkPurchases() {
    Log.d(LOG_TAG, "checkPurchases: cab is ready ${cab.isReady}")
    val purchasesResult = cab.queryPurchases(SkuType.inapp.toString())
    val purchases = purchasesResult.purchases
    Log.d(LOG_TAG, "checkPurchases: purchases $purchases")
    for (purchase in purchases) {
      Log.d(LOG_TAG, "purchase: $purchase")
      cab.consumeAsync(purchase.token) { responseCode, purchaseToken ->
        Log.d(LOG_TAG, "onConsumeResponse: ${responseCode.toResponseCode()} $purchaseToken")
      }
      // Handle the result
    }
  }

  private fun queryInapps() {
    Log.d(LOG_TAG, "queryInapps: cab is ready ${cab.isReady}")
    val skuList: ArrayList<String> = ArrayList(listOf("attempts"))
    cab.querySkuDetailsAsync(
      SkuDetailsParams().apply {
        itemType = SkuType.inapp.toString()
        moreItemSkus = skuList // Fill with the skus of items
      },
      skuDetailsResponseListener // Assume skuDetailsResponseListener is globally available or defined elsewhere
    )
  }


  fun Int.toResponseCode(): ResponseCode {
    for (code in ResponseCode.values()) {
      if (code.value == this) {
        return code
      }
    }
    throw IllegalArgumentException("Invalid ResponseCode value: $this")
  }

  companion object {
    private const val LOG_TAG = "SDK_Manager"
  }
}