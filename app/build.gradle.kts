import com.appcoins.diceroll.convention.extensions.projectImplementation

plugins {
  id("diceroll.android.app")
}

android {
  namespace = "com.appcoins.diceroll"
  defaultConfig {
    applicationId = "com.appcoins.diceroll"
    versionCode = 10
    versionName = "0.4.3"
    multiDexEnabled = true
  }
}

dependencies {
  releaseImplementation(libs.catappult.appcoins.billing)
  releaseImplementation(libs.catappult.android.appcoins.billing)
  releaseImplementation(libs.catappult.appcoins.adyen)
  releaseImplementation(libs.catappult.appcoins.core)
  releaseImplementation(libs.catappult.appcoins.contract.proxy)
  releaseImplementation(libs.catappult.appcoins.ads)
  releaseImplementation(libs.catappult.communication)
  releaseImplementation(libs.catappult.appcoins.lifecycle)
  releaseImplementation(libs.catappult.appcoins)

  debugImplementation("com.indicative.client.android:Indicative-Android:1.1.0")
  debugImplementation(files("libs/android-appcoins-billing-debug.aar"))
  debugImplementation(files("libs/appcoins-billing-debug.aar"))
  debugImplementation(files("libs/appcoins-adyen-debug.aar"))
  debugImplementation(files("libs/communication-debug.aar"))

  projectImplementation(":core:ui:design")
  projectImplementation(":core:ui:widgets")
  projectImplementation(":core:utils")
  projectImplementation(":core:navigation")
  projectImplementation(":feature:settings:data")
  projectImplementation(":feature:settings:ui")
  projectImplementation(":feature:stats:ui")
  projectImplementation(":feature:roll-game:ui")
  projectImplementation(":feature:payments:ui")
  projectImplementation(":payments:appcoins-osp")
  projectImplementation(":payments:appcoins-sdk")
  implementation(libs.androidx.splashscreen)
  implementation(libs.bundles.androidx.compose)
  implementation(libs.bundles.androidx.compose.accompanist)
  implementation(libs.indicative)
}