package com.appcoins.diceroll.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.appcoins.diceroll.core.navigation.destinations.Destinations
import com.appcoins.diceroll.core.navigation.destinations.toNavigationType
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

/**
 * Navigates to the specified destination using the given route and navigation options.
 *
 * @param destination The destination to navigate to, providing the type of screen.
 * @param destinationArgs The list of optional destination arguments.
 * @param navOptions The navigation options for this navigation.
 */
fun NavController.navigateToDestination(
  destination: Destinations,
  destinationArgs: List<String> = emptyList(),
  navOptions: NavOptions? = null,
) {
  this.navigate(finalRoute(destination.route, destinationArgs, shouldNavigate = true), navOptions)
}

/**
 * Builds a screen for the specified destination, taking into consideration the type of screen that
 * should be created, either a [Destinations.Screen], a [Destinations.Dialog] or
 * a [Destinations.BottomSheet], by passing the destination object, with optional arguments and
 * deeplink, and with the provided Composable content.
 *
 * @param destination The destination to build the screen for, providing the type of screen.
 * @param destinationArgs The list of optional destination arguments.
 * @param destinationDeeplinks The list of optional destination DeepLinks.
 * @param destinationComposable The Composable content to be displayed on the screen.
 */
fun NavGraphBuilder.buildScreen(
  destination: Destinations,
  destinationArgs: List<String> = emptyList(),
  destinationDeeplinks: List<String> = emptyList(),
  destinationComposable: @Composable (Map<String, String>) -> Unit,
) {
  val finalRoute = finalRoute(destination.route, destinationArgs, shouldNavigate = false)

  val destinationArgsList = destinationArgs.map {
    navArgument(it) {
      type = NavType.StringType
    }
  }

  val destinationDeeplinksList = destinationDeeplinks.map {
    navDeepLink {
      uriPattern = it
    }
  }

  when (destination.toNavigationType()) {
    NavigationType.Composable -> {
      composableHandler(
        finalRoute,
        destinationComposable,
        destinationArgsList,
        destinationDeeplinksList
      )
    }

    NavigationType.Dialog -> {
      dialogHandler(
        finalRoute,
        destinationComposable,
        destinationArgsList,
        destinationDeeplinksList
      )
    }

    NavigationType.BottomSheet -> {
      bottomSheetHandler(
        finalRoute,
        destinationComposable,
        destinationArgsList,
        destinationDeeplinksList
      )
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
  destinationArgs: List<NamedNavArgument>,
  destinationDeeplinks: List<NavDeepLink>,
) {
  composable(
    route = route,
    arguments = destinationArgs,
    deepLinks = destinationDeeplinks,
  ) { backStackEntry ->
    val argValues =
      destinationArgs.associate { it.name to backStackEntry.arguments?.getString(it.name) }
    content(argValues.filterValues { it != null }.mapValues { it.value!! })
  }
}

private fun NavGraphBuilder.dialogHandler(
  route: String,
  content: @Composable (Map<String, String>) -> Unit,
  destinationArgs: List<NamedNavArgument>,
  destinationDeeplinks: List<NavDeepLink>,
) {
  dialog(
    route = route,
    arguments = destinationArgs,
    deepLinks = destinationDeeplinks,
  ) { backStackEntry ->
    val argValues =
      destinationArgs.associate { it.name to backStackEntry.arguments?.getString(it.name) }
    content(argValues.filterValues { it != null }.mapValues { it.value!! })
  }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
private fun NavGraphBuilder.bottomSheetHandler(
  route: String,
  content: @Composable (Map<String, String>) -> Unit,
  destinationArgs: List<NamedNavArgument>,
  destinationDeeplinks: List<NavDeepLink>,
) {
  bottomSheet(
    route = route,
    arguments = destinationArgs,
    deepLinks = destinationDeeplinks,
  ) { backStackEntry ->
    val argValues =
      destinationArgs.associate { it.name to backStackEntry.arguments?.getString(it.name) }
    content(argValues.filterValues { it != null }.mapValues { it.value!! })
  }
}
