import com.appcoins.diceroll.convention.extensions.projectImplementation

plugins {
  id("diceroll.android.app")
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
  projectImplementation(":core:design")
  projectImplementation(":feature:settings:data")
  projectImplementation(":feature:settings:ui")
  projectImplementation(":feature:stats:ui")
  projectImplementation(":feature:roll-game:ui")
  implementation(libs.androidx.splashscreen)
  implementation(libs.bundles.androidx.compose)
}