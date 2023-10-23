plugins {
  id("diceroll.android.library.compose")
}

android {
  namespace = "com.appcoins.diceroll.payments.appcoins_osp"
}

dependencies {
  implementation(project(":core:network"))
  implementation(project(":core:utils"))
  implementation(libs.network.retrofit)
}