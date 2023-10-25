package com.appcoins.diceroll.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.appcoins.diceroll.feature.settings.ui.navigation.settingsDialog

internal fun NavGraphBuilder.settingsGraph(navController: NavHostController) {
  settingsDialog { navController.navigateUp() }
}