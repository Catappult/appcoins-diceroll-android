package com.appcoins.diceroll.core.navigation

sealed interface NavigationType {
  data object Composable : NavigationType
  data object Dialog : NavigationType
  data object BottomSheet : NavigationType
}