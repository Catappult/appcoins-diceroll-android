import com.appcoins.diceroll.convention.extensions.projectImplementation

plugins {
  id("diceroll.android.feature.ui")
}

android {
  namespace = "com.appcoins.diceroll.feature.roll_game.ui"
}

dependencies {
  projectImplementation(":feature:settings:data")
  projectImplementation(":feature:stats:data")
  projectImplementation(":feature:roll-game:data")
  projectImplementation(":payments:appcoins-sdk")
  projectImplementation(":payments:appcoins-osp")
  projectImplementation(":core:design")
  projectImplementation(":core:utils")
  implementation(libs.bundles.coil)
}