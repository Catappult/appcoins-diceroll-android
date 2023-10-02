plugins {
  id("diceroll.android.library")
}

android {
  namespace = "com.appcoins.diceroll.payments.appcoins_sdk"
  defaultConfig {
    buildConfigField("String", "CATAPPULT_PUBLIC_KEY", project.property("CATAPPULT_PUBLIC_KEY").toString())
  }
  buildFeatures {
    buildConfig = true
  }
  repositories {
    flatDir {
      dirs("libs")
    }
  }
}

dependencies {
  compileOnly(fileTree(mapOf("dir" to "libs", "include" to "*.aar")))
  implementation(project(":core:utils"))
//  implementation(libs.catappult.billing)
}