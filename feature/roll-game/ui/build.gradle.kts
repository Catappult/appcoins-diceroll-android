import com.appcoins.diceroll.convention.extensions.projectImplementation

plugins {
  id("diceroll.android.feature.ui")
}

android {
  namespace = "com.appcoins.diceroll.feature.roll_game.ui"
}

dependencies {
  compileOnly(fileTree(mapOf("dir" to "libs", "include" to "*.aar")))
  projectImplementation(":feature:settings:data")
  projectImplementation(":feature:stats:data")
  projectImplementation(":payments:appcoins-sdk")
  projectImplementation(":payments:appcoins-osp")
  projectImplementation(":core:design")
  projectImplementation(":core:utils")
  implementation(libs.bundles.coil)
}