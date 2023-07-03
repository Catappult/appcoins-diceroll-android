plugins {
  id("diceroll.android.feature.ui")
}

android {
  namespace = "com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui"
}

dependencies {
  implementation(project(":feature:settings:data"))
  implementation(project(":core:design"))
  implementation(project(":core:utils"))
}