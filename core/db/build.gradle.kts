plugins {
  id("diceroll.android.library")
}

android {
  namespace = "com.appcoins.wallet.sampleapp.diceroll.core.db"
}

dependencies {
  implementation(project(":core:utils"))
  implementation(libs.bundles.androidx.room)
  kapt(libs.androidx.room.compiler)
}