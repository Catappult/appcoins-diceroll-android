plugins {
  id("diceroll.android.app")
}

android {
  namespace = "com.appcoins.wallet.sampleapp.diceroll"
  defaultConfig {
    applicationId = "com.appcoins.wallet.sampleapp.diceroll"
    versionCode = 1
    versionName = "0.0.1"
  }
}

dependencies {
//  implementation(project(":core:network:base"))
  implementation(project(":core:design"))
  implementation(project(":feature:settings:data"))
  implementation(project(":feature:settings:ui"))
  implementation(project(":feature:roll-game:ui"))
  implementation(project(":feature:stats:ui"))
  implementation(libs.androidx.splashscreen)
  implementation(libs.bundles.androidx.compose)
}