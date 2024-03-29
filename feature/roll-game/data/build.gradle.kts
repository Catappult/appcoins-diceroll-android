import com.appcoins.diceroll.convention.extensions.projectImplementation

plugins {
  id("diceroll.android.feature.data")
}

android {
  namespace = "com.appcoins.diceroll.feature.roll_game.data"
}

dependencies {
  projectImplementation(":core:utils")
  projectImplementation(":core:datastore")
  implementation(libs.androidx.datastore.preferences)
}