package com.appcoins.diceroll.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.appcoins.diceroll.feature.payments.ui.navigation.navigateToPaymentsDialog
import com.appcoins.diceroll.feature.payments.ui.navigation.paymentsDialog
import com.appcoins.diceroll.feature.payments.ui.toSku
import com.appcoins.diceroll.feature.roll_game.ui.navigation.rollGameNavigationRoute
import com.appcoins.diceroll.feature.roll_game.ui.navigation.rollGameScreen
import com.appcoins.diceroll.feature.stats.ui.navigation.navigateToRollDetailsStatsScreen
import com.appcoins.diceroll.feature.stats.ui.navigation.rollDetailsStatsScreen
import com.appcoins.diceroll.feature.stats.ui.navigation.statsScreen
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun DiceRollNavHost(
  navController: NavHostController,
  bottomSheetNavigator: BottomSheetNavigator,
  startDestination: String = rollGameNavigationRoute,
  scaffoldPadding: PaddingValues
) {
  ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
    NavHost(
      navController = navController,
      startDestination = startDestination,
      modifier = Modifier.padding(scaffoldPadding),
      enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
      popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
      exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
      popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
    ) {
      rollGameScreen(onBuyClick = { item ->
        navController.navigateToPaymentsDialog(item.toSku()) {
          launchSingleTop = true
        }
      })
      paymentsDialog(onDismiss = {
        navController.popBackStack()
      })
      statsScreen(onDetailsClick = {
        navController.navigateToRollDetailsStatsScreen()
      })
      rollDetailsStatsScreen()
    }
  }
}
