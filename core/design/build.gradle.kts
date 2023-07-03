plugins {
  id("diceroll.android.library.compose")
}

android {
  namespace = "com.appcoins.wallet.sampleapp.diceroll.core.design"
}

dependencies {
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.ui.ui)
  implementation(libs.androidx.compose.material.iconsExtended)
}