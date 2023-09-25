plugins {
  id("diceroll.android.app")
}

android {
  namespace = "com.appcoins.wallet.sampleapp.diceroll"
  defaultConfig {
    applicationId = "com.appcoins.wallet.sampleapp.diceroll"
    versionCode = 2
    versionName = "0.1.0"
  }
}

dependencies {
  implementation(project(":core:design"))
  implementation(project(":feature:settings:data"))
  implementation(project(":feature:settings:ui"))
  implementation(project(":feature:roll-game:ui"))
  implementation(project(":feature:stats:ui"))
  implementation(project(":feature:catappult-sdk"))
  implementation(libs.androidx.splashscreen)
  implementation(libs.bundles.androidx.compose)
}