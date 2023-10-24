package com.appcoins.diceroll.feature.payments.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.appcoins.diceroll.feature.payments.ui.PaymentsDialogRoute
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import java.net.URLEncoder

const val paymentsNavigationRoute = "payments_dialog_route"
internal const val itemIdArg = "itemId"

fun NavController.navigateToPaymentsDialog(
  itemId: String,
  navOptions: NavOptionsBuilder.() -> Unit
) {
  val encodedId = URLEncoder.encode(itemId, Charsets.UTF_8.name())
  this.navigate("$paymentsNavigationRoute/$encodedId", navOptions)
}

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.paymentsDialog(onDismiss: () -> Unit) {
  bottomSheet(
    route = "$paymentsNavigationRoute/{$itemIdArg}",
    arguments = listOf(
      navArgument(itemIdArg) { type = NavType.StringType },
    ),
  ) { backStackEntry ->
    backStackEntry.arguments?.getString(itemIdArg)?.let {
      PaymentsDialogRoute(onDismiss, it)
    }
  }
}