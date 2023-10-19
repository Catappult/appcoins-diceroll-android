plugins {
  id("diceroll.android.library")
}

android {
  namespace = "com.appcoins.diceroll.core.network"
}

dependencies {
  implementation(project(":core:utils"))
  implementation(libs.bundles.network)
}