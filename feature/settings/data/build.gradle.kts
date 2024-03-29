import com.appcoins.diceroll.convention.extensions.projectImplementation

plugins {
  id("diceroll.android.feature.data")
}

android {
  namespace = "com.appcoins.diceroll.feature.settings.data"
}

dependencies {
  projectImplementation(":core:utils")
  projectImplementation(":core:datastore")
  projectImplementation(":core:network")
  implementation(libs.androidx.datastore.preferences)
}