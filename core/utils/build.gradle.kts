plugins {
  id("diceroll.android.library.compose")
}

android {
  namespace = "com.appcoins.diceroll.core.utils"
}

dependencies {
  implementation(project(":core:design"))
}