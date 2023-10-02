plugins {
  id("diceroll.android.feature.data")
}

android {
  namespace = "com.appcoins.diceroll.feature.settings.data"
}

dependencies {
  implementation(project(":core:utils"))
  implementation(libs.androidx.datastore.preferences)
}