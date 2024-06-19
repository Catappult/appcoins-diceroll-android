package com.appcoins.diceroll.payments.appcoins_sdk

import android.content.Context
import com.appcoins.diceroll.core.utils.ActivityResultStream
import com.appcoins.diceroll.core.utils.listen
import com.appcoins.sdk.billing.AppcoinsBillingClient
import com.appcoins.sdk.billing.helpers.CatapultBillingAppCoinsFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
object SdkManagerImpl : SdkManager {

    override lateinit var cab: AppcoinsBillingClient

    private val base64EncodedPublicKey = BuildConfig.CATAPPULT_PUBLIC_KEY

    fun setupSdkConnection(context: Context) {
        cab =
            CatapultBillingAppCoinsFactory.BuildAppcoinsBilling(
                context,
                base64EncodedPublicKey,
                purchasesUpdatedListener
            )
        cab.startConnection(appCoinsBillingStateListener)
        sdkResultHandle()
    }

    private fun sdkResultHandle() {
        CoroutineScope(Job()).launch {
            ActivityResultStream.listen<SdkResult>().collect {
                cab.onActivityResult(it.requestCode, it.resultCode, it.data)
            }
        }
    }
}