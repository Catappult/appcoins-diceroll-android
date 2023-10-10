import com.appcoins.diceroll.convention.extensions.projectImplementation

plugins {
  id("diceroll.android.library.compose")
}

android {
  namespace = "com.appcoins.diceroll.core.utils"
}

dependencies {
  projectImplementation(":core:design")
}