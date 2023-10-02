package com.appcoins.diceroll.core.utils.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.appcoins.diceroll.core.design.DiceRollIcons

/**
 * Ideally should be a lottie animation or a shimmer for the list,
 * but for the sake of simplicity I'm just showing a text and icon while it loads
 */
@Composable
fun Loading(stringResource: Int) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(vertical = 64.dp, horizontal = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Icon(
      imageVector = DiceRollIcons.loadIcon, contentDescription = null,
      modifier = Modifier
        .size(100.dp)
        .padding(bottom = 16.dp)
    )
    Text(
      text = stringResource(id = stringResource),
      style = MaterialTheme.typography.headlineSmall,
    )
  }
}