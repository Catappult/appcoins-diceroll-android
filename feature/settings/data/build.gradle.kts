plugins {
  id("diceroll.android.feature.data")
}

android {
  namespace = "com.appcoins.wallet.sampleapp.diceroll.feature.settings.data"
}

dependencies {
  implementation(project(":core:utils"))
  implementation(libs.androidx.datastore.preferences)
}