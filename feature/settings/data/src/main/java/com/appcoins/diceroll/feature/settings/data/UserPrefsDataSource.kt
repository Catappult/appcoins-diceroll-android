package com.appcoins.diceroll.feature.settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.appcoins.diceroll.core.utils.Dispatchers
import com.appcoins.diceroll.feature.settings.data.PreferencesKeys.CACHE_STRATEGY
import com.appcoins.diceroll.feature.settings.data.PreferencesKeys.THEME_CONFIG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserPrefsDataSource @Inject constructor(
  private val preferences: DataStore<Preferences>,
  private val dispatchers: Dispatchers
) {

  /**
   * Sets the desired theme config.
   */
  suspend fun saveThemeConfig(themeConfig: ThemeConfig) {
    withContext(dispatchers.io) {
      preferences.edit { prefs ->
        prefs[THEME_CONFIG] = themeConfig.ordinal
      }
    }
  }

  /**
   * Sets the desired cache strategy when fetching info.
   */
  suspend fun saveCacheStrategy(cacheStrategy: CacheStrategy) {
    withContext(dispatchers.io) {
      preferences.edit { prefs ->
        prefs[CACHE_STRATEGY] = cacheStrategy.ordinal
      }
    }
  }

  /**
   * Stream of [UserPrefs]
   */
  fun getUserPrefs(): Flow<UserPrefs> {
    return preferences.data.map { prefs ->
      val themeConfig = ThemeConfig.values()[prefs[THEME_CONFIG] ?: 0]
      val cacheStrategy = CacheStrategy.values()[prefs[CACHE_STRATEGY] ?: 0]
      UserPrefs(themeConfig = themeConfig, cacheStrategy = cacheStrategy)
    }.distinctUntilChanged()
  }
}

object PreferencesKeys {
  val THEME_CONFIG = intPreferencesKey("theme_config")
  val CACHE_STRATEGY = intPreferencesKey("cache_strategy")
}