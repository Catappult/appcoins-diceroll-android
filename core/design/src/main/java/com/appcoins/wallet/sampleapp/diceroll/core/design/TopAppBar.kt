package com.appcoins.wallet.sampleapp.diceroll.core.design

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

/**
 * Top app bar with action, displayed on the right
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceRollTopAppBar(
  @StringRes titleRes: Int,
  actionIcon: ImageVector,
  actionIconContentDescription: String? = null,
  onNavigationClick: () -> Unit = {},
  onActionClick: () -> Unit = {},
) {
  CenterAlignedTopAppBar(
    title = {
      Text(
        text = stringResource(id = titleRes),
        color = MaterialTheme.colorScheme.primaryContainer
      )
    },
    navigationIcon = {
      IconButton(onClick = onNavigationClick) {
        Image(
          painter = painterResource(id = R.drawable.wallet_icon ),
          contentDescription = null,
        )
      }
    },
    actions = {
      IconButton(onClick = onActionClick) {
        Icon(
          imageVector = actionIcon,
          contentDescription = actionIconContentDescription,
          tint = MaterialTheme.colorScheme.primaryContainer,
        )
      }
    },
    colors = centerAlignedTopAppBarColors(
      containerColor = MaterialTheme.colorScheme.background,
    ),
  )
}