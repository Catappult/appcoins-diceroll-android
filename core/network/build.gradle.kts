import com.appcoins.diceroll.convention.extensions.projectImplementation

plugins {
  id("diceroll.android.library")
}

android {
  namespace = "com.appcoins.diceroll.core.network"
}

dependencies {
  projectImplementation(":core:utils")
  implementation(libs.bundles.network)
}