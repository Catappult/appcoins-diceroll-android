package com.appcoins.wallet.sampleapp.diceroll.feature.settings.data

data class UserPrefs(
  val themeConfig: ThemeConfig = ThemeConfig.FOLLOW_SYSTEM,
  val cacheStrategy: CacheStrategy = CacheStrategy.NEVER
)

enum class ThemeConfig {
  FOLLOW_SYSTEM, LIGHT, DARK
}

enum class CacheStrategy {
  NEVER, ALWAYS
}