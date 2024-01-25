package com.appcoins.diceroll.feature.settings.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appcoins.diceroll.core.ui.design.theme.DiceRollTheme
import com.appcoins.diceroll.core.utils.R
import com.appcoins.diceroll.core.utils.diceRollPackage
import com.appcoins.diceroll.core.utils.extensions.getInstallerInfo
import com.appcoins.diceroll.feature.settings.data.model.CacheStrategy
import com.appcoins.diceroll.feature.settings.data.model.ThemeConfig
import com.appcoins.diceroll.feature.settings.data.model.UserPrefs
import com.appcoins.diceroll.feature.settings.ui.SettingsUiState.Loading
import com.appcoins.diceroll.feature.settings.ui.SettingsUiState.Success

@Composable
fun SettingsRoute(
  onDismiss: () -> Unit,
  viewModel: SettingsViewModel = hiltViewModel(),
) {
  val settingsUiState by viewModel.uiState.collectAsStateWithLifecycle()
  val deeplinkUiState by viewModel.storeDeeplinkState.collectAsStateWithLifecycle()
  SettingsDialog(
    onDismiss = onDismiss,
    settingsUiState = settingsUiState,
    deepLinkUiState = deeplinkUiState,
    viewModel = viewModel,
  )
}

@Composable
fun SettingsDialog(
  settingsUiState: SettingsUiState,
  deepLinkUiState: StoreDeeplinkUiState,
  onDismiss: () -> Unit,
  viewModel: SettingsViewModel,
) {
  AlertDialog(
    properties = DialogProperties(usePlatformDefaultWidth = false),
    modifier = Modifier.widthIn(max = LocalConfiguration.current.screenWidthDp.dp - 80.dp),
    onDismissRequest = { onDismiss() },
    title = {
      Text(
        text = stringResource(R.string.settings_title),
        style = MaterialTheme.typography.titleLarge,
      )
    },
    text = {
      HorizontalDivider()
      checkForInstaller(
        context = LocalContext.current,
        getUpdateDeeplink = viewModel::getUpdateDeeplink,
        viewModel.shouldLaunchDeeplink
      )
      Column(Modifier.verticalScroll(rememberScrollState())) {
        when (deepLinkUiState) {
          StoreDeeplinkUiState.Loading -> {}
          StoreDeeplinkUiState.Error -> {}
          is StoreDeeplinkUiState.Success -> {
            ShowUpdateInformation(
              deeplink = deepLinkUiState.storeDeeplinkUrl,
              shouldLaunchDeeplink = viewModel.shouldLaunchDeeplink,
            )
          }
        }
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
              onChangeThemeConfig = viewModel::updateThemeConfig,
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

private fun checkForInstaller(
  context: Context,
  getUpdateDeeplink: (appPackage: String, storePackage: String?) -> Unit,
  shouldLaunchDeeplink: MutableState<Boolean>
) {
  shouldLaunchDeeplink.value = true
  getUpdateDeeplink(diceRollPackage, context.packageManager.getInstallerInfo())
}

@Composable
fun ShowUpdateInformation(deeplink: String, shouldLaunchDeeplink: MutableState<Boolean>) {
  val context = LocalContext.current
  Text(
    text = stringResource(id = com.appcoins.diceroll.core.ui.design.R.string.check_for_updates_title),
    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
  )
  Button(onClick = {
    launchDeeplink(
      context = context,
      deeplink = deeplink,
      shouldLaunchDeeplink = shouldLaunchDeeplink
    )
  }) {
    Text(text = stringResource(id = com.appcoins.diceroll.core.ui.design.R.string.check_for_updates_button))
  }
}

fun launchDeeplink(
  deeplink: String,
  shouldLaunchDeeplink: MutableState<Boolean>,
  context: Context
) {
  if (shouldLaunchDeeplink.value) {
    shouldLaunchDeeplink.value = false
    val deeplinkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplink)).apply {
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
      context.packageManager.getInstallerInfo()?.let {
        `package` = it
      }
    }

    try {
      context.startActivity(deeplinkIntent)
    } catch (e: ActivityNotFoundException) {
      e.printStackTrace()
    }
  }
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
      settingsUiState = Success(
        UserPrefs(
          themeConfig = ThemeConfig.FOLLOW_SYSTEM,
          cacheStrategy = CacheStrategy.NEVER,
        ),
      ),
      deepLinkUiState = StoreDeeplinkUiState.Success("market://details=id={}"),
      onDismiss = {},
      viewModel = hiltViewModel(),
    )
  }
}