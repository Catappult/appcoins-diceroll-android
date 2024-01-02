import com.appcoins.diceroll.convention.extensions.projectImplementation

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
}

dependencies {
  projectImplementation(":core:utils")
  implementation(libs.catappult.billing)
}