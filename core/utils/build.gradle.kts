plugins {
  id("diceroll.android.library.compose")
}

android {
  namespace = "com.appcoins.wallet.sampleapp.diceroll.core.utils"
}

dependencies {
  implementation(project(":core:design"))
  implementation(libs.bundles.androidx.compose)
}