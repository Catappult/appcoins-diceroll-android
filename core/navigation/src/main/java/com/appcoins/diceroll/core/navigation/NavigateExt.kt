package com.appcoins.diceroll.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.appcoins.diceroll.core.navigation.destinations.Destinations
import com.appcoins.diceroll.core.navigation.destinations.toNavigationType
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet


fun NavController.navigateToDestination(
  destination: Destinations,
  destinationArgs: List<String> = emptyList(),
  navOptions: NavOptions? = null,
) {
  this.navigate(finalRoute(destination.route, destinationArgs, shouldNavigate = true), navOptions)
}

fun NavGraphBuilder.buildScreen(
  destination: Destinations,
  destinationArgs: List<String> = emptyList(),
  destinationComposable: @Composable (Map<String, String>) -> Unit,
) {
  val finalRoute = finalRoute(destination.route, destinationArgs, shouldNavigate = false)

  val destinationArgsList = destinationArgs.map {
    navArgument(it) {
      type = NavType.StringType
    }
  }

  when (destination.toNavigationType()) {
    NavigationType.Composable -> {
      composableHandler(finalRoute, destinationComposable, destinationArgsList)
    }

    NavigationType.Dialog -> {
      dialogHandler(finalRoute, destinationComposable, destinationArgsList)
    }

    NavigationType.BottomSheet -> {
      bottomSheetHandler(finalRoute, destinationComposable, destinationArgsList)
    }
  }
}


private fun finalRoute(
  destination: String,
  destinationArgs: List<String> = emptyList(),
  shouldNavigate: Boolean = false,
): String {
  val routeArgs = destinationArgs.joinToString("/") {
    if (shouldNavigate) it else "{$it}"
  }

  return if (routeArgs.isNotEmpty()) {
    "$destination/$routeArgs"
  } else {
    destination
  }
}

private fun NavGraphBuilder.composableHandler(
  route: String,
  content: @Composable (Map<String, String>) -> Unit,
  destinationArgsList: List<NamedNavArgument>,
) {
  composable(
    route = route,
    arguments = destinationArgsList,
  ) { backStackEntry ->
    val argValues =
      destinationArgsList.associate { it.name to backStackEntry.arguments?.getString(it.name) }
    content(argValues.filterValues { it != null }.mapValues { it.value!! })
  }
}

private fun NavGraphBuilder.dialogHandler(
  route: String,
  content: @Composable (Map<String, String>) -> Unit,
  destinationArgsList: List<NamedNavArgument>,
) {
  dialog(
    route = route,
    arguments = destinationArgsList,
  ) { backStackEntry ->
    val argValues =
      destinationArgsList.associate { it.name to backStackEntry.arguments?.getString(it.name) }
    content(argValues.filterValues { it != null }.mapValues { it.value!! })
  }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
private fun NavGraphBuilder.bottomSheetHandler(
  route: String,
  content: @Composable (Map<String, String>) -> Unit,
  destinationArgsList: List<NamedNavArgument>,
) {
  bottomSheet(
    route = route,
    arguments = destinationArgsList,
  ) { backStackEntry ->
    val argValues =
      destinationArgsList.associate { it.name to backStackEntry.arguments?.getString(it.name) }
    content(argValues.filterValues { it != null }.mapValues { it.value!! })
  }
}
