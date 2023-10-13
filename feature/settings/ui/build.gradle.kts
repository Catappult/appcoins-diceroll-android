import com.appcoins.diceroll.convention.extensions.projectImplementation

plugins {
  id("diceroll.android.feature.ui")
}

android {
  namespace = "com.appcoins.diceroll.feature.settings.ui"
}

dependencies {
  projectImplementation(":feature:settings:data")
  projectImplementation(":core:ui:design")
  projectImplementation(":core:utils")
}