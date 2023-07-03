import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

group = "com.appcoins.wallet.convention"

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

dependencies {
  implementation(libs.gradlePlugin.android)
  implementation(libs.gradlePlugin.kotlin)
  implementation(libs.gradlePlugin.hilt)
}

gradlePlugin {
  plugins {
    register("AndroidApp") {
      id = "diceroll.android.app"
      implementationClass = "com.appcoins.wallet.convention.plugins.AndroidAppPlugin"
    }
    register("AndroidFeatureData") {
      id = "diceroll.android.feature.data"
      implementationClass = "com.appcoins.wallet.convention.plugins.AndroidFeatureDataPlugin"
    }
    register("AndroidFeature") {
      id = "diceroll.android.feature.ui"
      implementationClass = "com.appcoins.wallet.convention.plugins.AndroidFeatureUiPlugin"
    }
    register("AndroidLibrary") {
      id = "diceroll.android.library"
      implementationClass = "com.appcoins.wallet.convention.plugins.AndroidLibraryPlugin"
    }
    register("AndroidLibraryCompose") {
      id = "diceroll.android.library.compose"
      implementationClass = "com.appcoins.wallet.convention.plugins.AndroidLibraryComposePlugin"
    }
    register("JvmLibrary") {
      id = "diceroll.jvm.library"
      implementationClass = "com.appcoins.wallet.convention.plugins.JvmLibraryPlugin"
    }
    register("Room") {
      id = "diceroll.room"
      implementationClass = "com.appcoins.wallet.convention.plugins.RoomPlugin"
    }
    register("Hilt") {
      id = "diceroll.hilt"
      implementationClass = "com.appcoins.wallet.convention.plugins.HiltPlugin"
    }
  }
}