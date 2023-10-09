plugins {
  id("diceroll.android.feature.data")
}

android {
  namespace = "com.appcoins.diceroll.feature.roll_game.data"
}

dependencies {
  implementation(project(":core:utils"))
  implementation(project(":core:datastore"))
  implementation(libs.androidx.datastore.preferences)
}