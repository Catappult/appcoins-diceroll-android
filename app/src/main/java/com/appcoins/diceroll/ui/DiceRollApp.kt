package com.appcoins.diceroll.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.appcoins.diceroll.core.design.DiceRollIcons
import com.appcoins.diceroll.core.design.DiceRollNavigationBarItem
import com.appcoins.diceroll.core.design.DiceRollTopAppBar
import com.appcoins.diceroll.feature.settings.ui.SettingsRoute
import com.appcoins.diceroll.navigation.DiceRollNavHost
import com.appcoins.diceroll.navigation.TopLevelDestination
import com.appcoins.diceroll.core.utils.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceRollApp() {
  val appState: DiceRollAppState = rememberDiceRollAppState()
  if (appState.shouldShowSettingsDialog) {
    SettingsRoute(
      onDismiss = { appState.setShowSettingsDialog(false) },
    )
  }
  Scaffold(
    topBar = {
      DiceRollTopAppBar(
        titleRes = appState.currentTopLevelDestination?.titleTextId ?: R.string.top_bar_title,
        actionIcon = DiceRollIcons.settings,
        onActionClick = { appState.setShowSettingsDialog(true) },
      )
    },
    bottomBar = {
      DiceRollBottomBar(
        destinations = appState.topLevelDestinations,
        onNavigateToDestination = appState::navigateToTopLevelDestination,
        currentDestination = appState.currentDestination,
      )
    },
  ) { scaffoldPadding ->
    DiceRollNavHost(
      appState.navController,
      scaffoldPadding = scaffoldPadding,
    )
  }
}

@Composable
fun DiceRollBottomBar(
  destinations: List<TopLevelDestination>,
  onNavigateToDestination: (TopLevelDestination) -> Unit,
  currentDestination: NavDestination?,
) {
  NavigationBar(
    modifier = Modifier,
    containerColor = MaterialTheme.colorScheme.background,
  ) {
    destinations.forEach { destination ->
      val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
      DiceRollNavigationBarItem(
        selected = selected,
        onClick = { onNavigateToDestination(destination) },
        icon = {
          val icon = if (selected) {
            destination.selectedIcon
          } else {
            destination.unselectedIcon
          }
          Icon(
            imageVector = icon,
            contentDescription = null,
          )
        },
        label = { Text(stringResource(destination.iconTextId)) },
      )
    }
  }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
  this?.hierarchy?.any {
    it.route?.contains(destination.name, true) ?: false
  } ?: false
