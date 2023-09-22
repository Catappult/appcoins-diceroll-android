plugins {
  id("diceroll.android.library")
  id("diceroll.room")
}

android {
  namespace = "com.appcoins.wallet.sampleapp.diceroll.core.db"
}

dependencies {
  implementation(project(":core:utils"))
}