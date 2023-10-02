plugins {
  id("diceroll.android.feature.ui")
}

android {
  namespace = "com.appcoins.diceroll.feature.roll_game.ui"
}

dependencies {
  compileOnly(fileTree(mapOf("dir" to "libs", "include" to "*.aar")))
  implementation(project(":feature:settings:data"))
  implementation(project(":feature:stats:data"))
  implementation(project(":feature:catappult-sdk"))
  implementation(project(":payments:appcoins-sdk"))
  implementation(project(":payments:appcoins-osp"))
  implementation(project(":core:design"))
  implementation(project(":core:utils"))
  implementation(libs.bundles.coil)
}