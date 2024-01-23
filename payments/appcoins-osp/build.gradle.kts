import com.appcoins.diceroll.convention.extensions.projectImplementation

plugins {
  id("diceroll.android.library.compose")
}

android {
  namespace = "com.appcoins.diceroll.payments.appcoins_osp"
}

dependencies {
  projectImplementation(":core:network")
  projectImplementation(":core:utils")
  implementation(libs.network.retrofit)
}