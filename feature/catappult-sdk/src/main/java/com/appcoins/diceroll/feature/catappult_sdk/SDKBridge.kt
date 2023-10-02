package com.appcoins.diceroll.feature.catappult_sdk

import android.app.Activity
import android.util.Log
import com.appcoins.sdk.billing.AppcoinsBillingClient
import com.appcoins.sdk.billing.BillingFlowParams
import com.appcoins.sdk.billing.CatapultAppcoinsBilling
import com.appcoins.sdk.billing.Purchase
import com.appcoins.sdk.billing.PurchasesUpdatedListener
import com.appcoins.sdk.billing.ResponseCode
import com.appcoins.sdk.billing.SkuDetails
import com.appcoins.sdk.billing.SkuDetailsParams
import com.appcoins.sdk.billing.helpers.CatapultBillingAppCoinsFactory
import com.appcoins.sdk.billing.listeners.AppCoinsBillingStateListener
import com.appcoins.sdk.billing.listeners.ConsumeResponseListener
import com.appcoins.sdk.billing.listeners.SkuDetailsResponseListener
import com.appcoins.sdk.billing.types.SkuType
import com.appcoins.diceroll.feature.catappult_sdk.BuildConfig
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Arrays


class SDKBridge(private val activity: Activity) {
  companion object {
    private const val MSG_INITIAL_RESULT = "InitialResult"
    private const val MSG_CONNECTION_LOST = "ConnectionLost"
    private const val MSG_PRODUCTS_GET_RESULT = "ProductsGetResult"
    private const val MSG_LAUNCH_BILLING_RESULT = "LaunchBillingResult"
    private const val MSG_PRODUCTS_PAY_RESULT = "ProductsPayResult"
    private const val MSG_PRODUCTS_CONSUME_RESULT = "ProductsConsumeResult"
    private const val MSG_QUERY_PURCHASES_RESULT = "QueryPurchasesResult"
    private const val LOG_TAG = "[AptoBridge]"
  }

  private var unityClassName = ""
  private var publicKey = ""
  private var needLog = false
  private val appCoinsBillingStateListener: AppCoinsBillingStateListener =
    object : AppCoinsBillingStateListener {
      override fun onBillingSetupFinished(responseCode: Int) {
        AptoLog("onBillingSetupFinished responseCode = $responseCode")
        val jsonObject = JSONObject()
        try {
          jsonObject.put("msg", MSG_INITIAL_RESULT)
          jsonObject.put("succeed", responseCode == ResponseCode.OK.value)
          jsonObject.put("responseCode", responseCode)
        } catch (e: JSONException) {
          e.printStackTrace()
        }
        SendUnityMessage(jsonObject)
      }

      override fun onBillingServiceDisconnected() {
        AptoLog("onBillingServiceDisconnected")
        val jsonObject = JSONObject()
        try {
          jsonObject.put("msg", MSG_CONNECTION_LOST)
        } catch (e: JSONException) {
          e.printStackTrace()
        }
        SendUnityMessage(jsonObject)
      }
    }
  var cab: AppcoinsBillingClient? = null
  private val purchasesUpdatedListener =
    PurchasesUpdatedListener { responseCode, purchases ->
      AptoLog("purchasesUpdatedListener $responseCode")
      val jsonObject = JSONObject()
      val purchasesJson = JSONArray()
      for (purchase in purchases) {
        val purchaseJson = GetPurchaseJson(purchase)
        purchasesJson.put(purchaseJson)
      }
      try {
        jsonObject.put("msg", MSG_PRODUCTS_PAY_RESULT)
        jsonObject.put("succeed", responseCode == ResponseCode.OK.value)
        jsonObject.put("responseCode", responseCode)
        jsonObject.put("purchases", purchasesJson)
      } catch (e: JSONException) {
        e.printStackTrace()
      }
      SendUnityMessage(jsonObject)
    }
  private val consumeResponseListener =
    ConsumeResponseListener { responseCode, purchaseToken ->
      AptoLog("Consumption finished. Purchase: $purchaseToken, result: $responseCode")
      val jsonObject = JSONObject()
      try {
        jsonObject.put("msg", MSG_PRODUCTS_CONSUME_RESULT)
        jsonObject.put("succeed", responseCode == ResponseCode.OK.value)
        jsonObject.put("purchaseToken", purchaseToken)
      } catch (e: JSONException) {
        e.printStackTrace()
      }
      SendUnityMessage(jsonObject)
    }
  private val skuDetailsResponseListener =
    SkuDetailsResponseListener { responseCode, skuDetailsList ->
      AptoLog("Received skus $responseCode")
      val jsonObject = JSONObject()
      if (responseCode == ResponseCode.OK.value) {
        val jsonSkus = JSONArray()
        for (skuDetails in skuDetailsList) {
          val detailJson = GetSkuDetailsJson(skuDetails)
          jsonSkus.put(detailJson)
        }
        try {
          jsonObject.put("msg", MSG_PRODUCTS_GET_RESULT)
          jsonObject.put("succeed", true)
          jsonObject.put("responseCode", responseCode)
          jsonObject.put("products", jsonSkus)
        } catch (e: JSONException) {
          e.printStackTrace()
        }
      } else {
        try {
          jsonObject.put("msg", MSG_PRODUCTS_GET_RESULT)
          jsonObject.put("succeed", false)
          jsonObject.put("responseCode", responseCode)
        } catch (e: JSONException) {
          e.printStackTrace()
        }
      }
      SendUnityMessage(jsonObject)
    }

  fun Initialize(_needLog: Boolean) {
    AptoLog("Apto Initialize")
    needLog = _needLog
    cab = CatapultBillingAppCoinsFactory.BuildAppcoinsBilling(
      activity,
      BuildConfig.CATAPPULT_PUBLIC_KEY,
      purchasesUpdatedListener
    )
    (cab as CatapultAppcoinsBilling).startConnection(appCoinsBillingStateListener)
  }

  fun ProductsStartGet(strSku: String) {
    AptoLog("Products Start Get")
    val skuList: List<String> =
      ArrayList(Arrays.asList(*strSku.split(";".toRegex()).dropLastWhile { it.isEmpty() }
        .toTypedArray()))
    AptoLog("skuList = $skuList")
    val skuDetailsParams = SkuDetailsParams()
    skuDetailsParams.itemType = SkuType.inapp.toString()
    skuDetailsParams.moreItemSkus = skuList
    cab!!.querySkuDetailsAsync(skuDetailsParams, skuDetailsResponseListener)
  }

  fun ProductsStartPay(sku: String?, developerPayload: String?) {
    AptoLog("Launching purchase flow.")
    // Your sku type, can also be SkuType.subs.toString()
    val skuType = SkuType.inapp.toString()
    val billingFlowParams = BillingFlowParams(
      sku,
      skuType,
      "orderId=" + System.currentTimeMillis(),
      developerPayload,
      "BDS"
    )
    val thread = Thread {
      val responseCode =
        cab!!.launchBillingFlow(activity, billingFlowParams)
      val jsonObject = JSONObject()
      try {
        jsonObject.put("msg", MSG_LAUNCH_BILLING_RESULT)
        jsonObject.put("succeed", responseCode == ResponseCode.OK.value)
        jsonObject.put("responseCode", responseCode)
      } catch (e: JSONException) {
        e.printStackTrace()
      }
      SendUnityMessage(jsonObject)
    }
    thread.start()
  }

  fun QueryPurchases() {
    val purchasesResult = cab!!.queryPurchases(SkuType.inapp.toString())
    val purchases = purchasesResult.purchases
    val purchasesJson = JSONArray()
    for (purchase in purchases) {
      val detailJson = GetPurchaseJson(purchase)
      purchasesJson.put(detailJson)
    }
    val jsonObject = JSONObject()
    try {
      jsonObject.put("msg", MSG_QUERY_PURCHASES_RESULT)
      jsonObject.put("succeed", true)
      jsonObject.put("purchases", purchasesJson)
    } catch (e: JSONException) {
      e.printStackTrace()
    }
    SendUnityMessage(jsonObject)
  }

  fun ProductsStartConsume(strToken: String) {
    AptoLog("Products Start Consume")
    val tokenList: List<String> =
      ArrayList(Arrays.asList(*strToken.split(";".toRegex()).dropLastWhile { it.isEmpty() }
        .toTypedArray()))
    AptoLog("tokenList = $tokenList")
    for (token in tokenList) {
      cab!!.consumeAsync(token, consumeResponseListener)
    }
  }

  fun SendUnityMessage(jsonObject: JSONObject) {
    Log.d("CUSTOM_TAG", "SDKBridge: SendUnityMessage: jsonObject: $jsonObject")
  }

  fun GetSkuDetailsJson(skuDetails: SkuDetails): JSONObject {
    val jsonObject = JSONObject()
    try {
      jsonObject.put("appPrice", skuDetails.appcPrice)
      jsonObject.put("appcPriceAmountMicros", skuDetails.appcPriceAmountMicros)
      jsonObject.put("appcPriceCurrencyCode", skuDetails.appcPriceCurrencyCode)
      jsonObject.put("description", skuDetails.description)
      jsonObject.put("fiatPrice", skuDetails.fiatPrice)
      jsonObject.put("fiatPriceAmountMicros", skuDetails.fiatPriceAmountMicros)
      jsonObject.put("fiatPriceCurrencyCode", skuDetails.fiatPriceCurrencyCode)
      jsonObject.put("itemType", skuDetails.itemType)
      jsonObject.put("price", skuDetails.price)
      jsonObject.put("priceAmountMicros", skuDetails.priceAmountMicros)
      jsonObject.put("priceCurrencyCode", skuDetails.priceCurrencyCode)
      jsonObject.put("sku", skuDetails.sku)
      jsonObject.put("title", skuDetails.title)
      jsonObject.put("type", skuDetails.type)
    } catch (e: JSONException) {
      e.printStackTrace()
    }
    return jsonObject
  }

  fun GetPurchaseJson(purchase: Purchase): JSONObject {
    val jsonObject = JSONObject()
    try {
      jsonObject.put("developerPayload", purchase.developerPayload)
      jsonObject.put("isAutoRenewing", purchase.isAutoRenewing)
      jsonObject.put("itemType", purchase.itemType)
      jsonObject.put("orderId", purchase.orderId)
      jsonObject.put("originalJson", purchase.originalJson)
      jsonObject.put("packageName", purchase.packageName)
      jsonObject.put("purchaseState", purchase.purchaseState)
      jsonObject.put("purchaseTime", purchase.purchaseTime)
      jsonObject.put("sku", purchase.sku)
      jsonObject.put("token", purchase.token)
    } catch (e: JSONException) {
      e.printStackTrace()
    }
    return jsonObject
  }

  fun AptoLog(msg: String?) {
    if (needLog) {
      Log.d(LOG_TAG, msg!!)
    }
  }
}
