package com.appcoins.diceroll.feature.settings.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.appcoins.diceroll.core.navigation.buildScreen
import com.appcoins.diceroll.core.navigation.destinations.Destinations
import com.appcoins.diceroll.core.navigation.navigateToDestination
import com.appcoins.diceroll.feature.settings.ui.SettingsRoute

fun NavController.navigateToSettings() {
  this.navigateToDestination(
    destination = Destinations.Dialog.Settings
  )
}

fun NavGraphBuilder.settingsDialog(onDismiss: () -> Unit) {
  this.buildScreen(
    destination = Destinations.Dialog.Settings,
  ) {
    SettingsRoute(onDismiss)
  }
}
