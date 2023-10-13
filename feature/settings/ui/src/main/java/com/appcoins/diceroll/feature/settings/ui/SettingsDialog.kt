package com.appcoins.diceroll.feature.settings.ui

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
import com.appcoins.diceroll.core.ui.design.theme.DiceRollTheme
import com.appcoins.diceroll.feature.settings.data.CacheStrategy
import com.appcoins.diceroll.feature.settings.data.ThemeConfig
import com.appcoins.diceroll.feature.settings.data.UserPrefs
import com.appcoins.diceroll.feature.settings.ui.SettingsUiState.Loading
import com.appcoins.diceroll.feature.settings.ui.SettingsUiState.Success
import com.appcoins.diceroll.core.utils.R

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
  )
}

@Composable
fun SettingsDialog(
  settingsUiState: SettingsUiState,
  onDismiss: () -> Unit,
  onChangeThemeConfig: (themeConfig: ThemeConfig) -> Unit,
) {
  val configuration = LocalConfiguration.current
  AlertDialog(
    properties = DialogProperties(usePlatformDefaultWidth = false),
    modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
    onDismissRequest = { onDismiss() },
    title = {
      Text(
        text = stringResource(R.string.settings_title),
        style = MaterialTheme.typography.titleLarge,
      )
    },
    text = {
      HorizontalDivider()
      Column(Modifier.verticalScroll(rememberScrollState())) {
        when (settingsUiState) {
          Loading -> {
            Text(
              text = stringResource(R.string.loading),
              modifier = Modifier.padding(vertical = 16.dp),
            )
          }
          is Success -> {
            SettingsPanel(
              userPrefs = settingsUiState.userPrefs,
              onChangeThemeConfig = onChangeThemeConfig,
            )
          }
        }
      }
    },
    confirmButton = {
      Text(
        text = stringResource(R.string.confirm),
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
) {
  SettingsDialogSectionTitle(text = stringResource(R.string.settings_theme))
  Column(Modifier.selectableGroup()) {
    SettingsChooserRow(
      text = stringResource(R.string.settings_theme_system),
      selected = userPrefs.themeConfig == ThemeConfig.FOLLOW_SYSTEM,
      onClick = { onChangeThemeConfig(ThemeConfig.FOLLOW_SYSTEM) },
    )
    SettingsChooserRow(
      text = stringResource(R.string.settings_theme_light),
      selected = userPrefs.themeConfig == ThemeConfig.LIGHT,
      onClick = { onChangeThemeConfig(ThemeConfig.LIGHT) },
    )
    SettingsChooserRow(
      text = stringResource(R.string.settings_theme_dark),
      selected = userPrefs.themeConfig == ThemeConfig.DARK,
      onClick = { onChangeThemeConfig(ThemeConfig.DARK) },
    )
  }
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
    )
  }
}