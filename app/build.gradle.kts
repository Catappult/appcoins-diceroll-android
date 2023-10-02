plugins {
  id("diceroll.android.app")
  apply { id("com.android.application") }
}

android {
  namespace = "com.appcoins.diceroll"
  defaultConfig {
    applicationId = "com.appcoins.diceroll"
    versionCode = 2
    versionName = "0.1.0"
  }
  buildTypes {
    getByName("release") {
      signingConfig = signingConfigs.getByName("debug")
    }
  }
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to "*.aar")))
  implementation(project(":core:design"))
  implementation(project(":feature:settings:data"))
  implementation(project(":feature:settings:ui"))
  implementation(project(":feature:roll-game:ui"))
  implementation(project(":feature:stats:ui"))
  implementation(project(":feature:catappult-sdk"))
  implementation(libs.androidx.splashscreen)
  implementation(libs.bundles.androidx.compose)
}