plugins {
  id("diceroll.android.feature.data")
}

android {
  namespace = "com.appcoins.wallet.sampleapp.diceroll.feature.stats.data"
}

dependencies {
  implementation(project(":core:utils"))
  implementation(project(":core:db"))
  implementation(libs.androidx.datastore.preferences)
}