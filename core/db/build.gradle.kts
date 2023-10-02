plugins {
  id("diceroll.android.library")
  id("diceroll.room")
}

android {
  namespace = "com.appcoins.diceroll.core.db"
}

dependencies {
  implementation(project(":core:utils"))
}