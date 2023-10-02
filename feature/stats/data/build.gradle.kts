plugins {
  id("diceroll.android.feature.data")
}

android {
  namespace = "com.appcoins.diceroll.feature.stats.data"
}

dependencies {
  implementation(project(":core:utils"))
  implementation(project(":core:db"))
  implementation(libs.androidx.datastore.preferences)
}