import com.appcoins.diceroll.convention.extensions.projectImplementation

plugins {
  id("diceroll.android.library")
}

android {
  namespace = "com.appcoins.diceroll.payments.appcoins_sdk"
  buildTypes {
    debug {
      buildConfigField(
        "String",
        "CATAPPULT_PUBLIC_KEY",
        project.property("CATAPPULT_PUBLIC_KEY_DEV").toString()
      )

    }
    release {
      buildConfigField(
        "String",
        "CATAPPULT_PUBLIC_KEY",
        project.property("CATAPPULT_PUBLIC_KEY").toString()
      )
    }
  }
  buildFeatures {
    buildConfig = true
  }
}

dependencies {
  projectImplementation(":core:utils")
  releaseImplementation(libs.catappult.appcoins.billing)
  releaseImplementation(libs.catappult.android.appcoins.billing)
  releaseImplementation(libs.catappult.appcoins.adyen)
  releaseImplementation(libs.catappult.appcoins.core)
  releaseImplementation(libs.catappult.appcoins.contract.proxy)
  releaseImplementation(libs.catappult.appcoins.ads)
  releaseImplementation(libs.catappult.communication)
  releaseImplementation(libs.catappult.appcoins.lifecycle)
  releaseImplementation(libs.catappult.appcoins)

  debugCompileOnly(files("libs/android-appcoins-billing-debug.aar"))
  debugCompileOnly(files("libs/appcoins-billing-debug.aar"))
  debugCompileOnly(files("libs/appcoins-adyen-debug.aar"))
  debugCompileOnly(files("libs/communication-debug.aar"))
}