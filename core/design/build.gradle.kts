plugins {
  id("diceroll.android.library.compose")
}

android {
  namespace = "com.appcoins.diceroll.core.design"
}

dependencies {
  implementation(libs.bundles.androidx.compose)
  implementation(libs.androidx.compose.material.iconsExtended)
}