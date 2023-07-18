package com.appcoins.wallet.sampleapp.diceroll.feature.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appcoins.wallet.sampleapp.diceroll.core.design.theme.DiceRollTheme
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.data.CacheStrategy
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.data.ThemeConfig
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.data.UserPrefs
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.ui.SettingsUiState.Loading
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.ui.SettingsUiState.Success
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.ui.R as SettingsR

@Composable
fun SettingsRoute(
  onDismiss: () -> Unit,
  viewModel: SettingsViewModel = hiltViewModel(),
) {
  val settingsUiState by viewModel.uiState.collectAsStateWithLifecycle()
  SettingsDialog(
    onDismiss = onDismiss,
    settingsUiState = settingsUiState,
    onChangeThemeConfig = viewModel::updateThemeConfig,
    onChangeCacheStrategy = viewModel::updateCacheStrategy,
  )
}

@Composable
fun SettingsDialog(
  settingsUiState: SettingsUiState,
  onDismiss: () -> Unit,
  onChangeThemeConfig: (themeConfig: ThemeConfig) -> Unit,
  onChangeCacheStrategy: (cacheStrategy: CacheStrategy) -> Unit,
) {
  val configuration = LocalConfiguration.current
  AlertDialog(
    properties = DialogProperties(usePlatformDefaultWidth = false),
    modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
    onDismissRequest = { onDismiss() },
    title = {
      Text(
        text = stringResource(SettingsR.string.settings_title),
        style = MaterialTheme.typography.titleLarge,
      )
    },
    text = {
      Divider()
      Column(Modifier.verticalScroll(rememberScrollState())) {
        when (settingsUiState) {
          Loading -> {
            Text(
              text = stringResource(SettingsR.string.loading),
              modifier = Modifier.padding(vertical = 16.dp),
            )
          }
          is Success -> {
            SettingsPanel(
              userPrefs = settingsUiState.userPrefs,
              onChangeThemeConfig = onChangeThemeConfig,
              onChangeCacheStrategy = onChangeCacheStrategy,
            )
          }
        }
      }
    },
    confirmButton = {
      Text(
        text = stringResource(SettingsR.string.confirm),
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier
          .padding(horizontal = 8.dp)
          .clickable { onDismiss() },
      )
    },
  )
}

@Composable
private fun SettingsPanel(
  userPrefs: UserPrefs,
  onChangeThemeConfig: (themeConfig: ThemeConfig) -> Unit,
  onChangeCacheStrategy: (cacheStrategy: CacheStrategy) -> Unit,
) {
  SettingsDialogSectionTitle(text = stringResource(SettingsR.string.theme))
  Column(Modifier.selectableGroup()) {
    SettingsChooserRow(
      text = stringResource(SettingsR.string.theme_system),
      selected = userPrefs.themeConfig == ThemeConfig.FOLLOW_SYSTEM,
      onClick = { onChangeThemeConfig(ThemeConfig.FOLLOW_SYSTEM) },
    )
    SettingsChooserRow(
      text = stringResource(SettingsR.string.theme_light),
      selected = userPrefs.themeConfig == ThemeConfig.LIGHT,
      onClick = { onChangeThemeConfig(ThemeConfig.LIGHT) },
    )
    SettingsChooserRow(
      text = stringResource(SettingsR.string.theme_dark),
      selected = userPrefs.themeConfig == ThemeConfig.DARK,
      onClick = { onChangeThemeConfig(ThemeConfig.DARK) },
    )
  }
//  SettingsDialogSectionTitle(text = stringResource(SettingsR.string.cache_strategy))
//  Column(Modifier.selectableGroup()) {
//    SettingsChooserRow(
//      text = stringResource(SettingsR.string.cache_strategy_never),
//      selected = userPrefs.cacheStrategy == CacheStrategy.NEVER,
//      onClick = { onChangeCacheStrategy(CacheStrategy.NEVER) },
//    )
//    SettingsChooserRow(
//      text = stringResource(SettingsR.string.cache_strategy_always),
//      selected = userPrefs.cacheStrategy == CacheStrategy.ALWAYS,
//      onClick = { onChangeCacheStrategy(CacheStrategy.ALWAYS) },
//    )
//  }
}

@Composable
private fun SettingsDialogSectionTitle(text: String) {
  Text(
    text = text,
    style = MaterialTheme.typography.titleMedium,
    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
  )
}

@Composable
fun SettingsChooserRow(
  text: String,
  selected: Boolean,
  onClick: () -> Unit,
) {
  Row(
    Modifier
      .fillMaxWidth()
      .selectable(
        selected = selected,
        role = Role.RadioButton,
        onClick = onClick,
      )
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    RadioButton(
      selected = selected,
      onClick = null,
    )
    Spacer(Modifier.width(8.dp))
    Text(text)
  }
}

@Preview
@Composable
private fun PreviewSettingsDialog() {
  DiceRollTheme {
    SettingsDialog(
      onDismiss = {},
      settingsUiState = Success(
        UserPrefs(
          themeConfig = ThemeConfig.FOLLOW_SYSTEM,
          cacheStrategy = CacheStrategy.NEVER,
        ),
      ),
      onChangeThemeConfig = {},
      onChangeCacheStrategy = {},
    )
  }
}