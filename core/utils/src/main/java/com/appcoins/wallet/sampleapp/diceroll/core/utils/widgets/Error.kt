package com.appcoins.wallet.sampleapp.diceroll.core.utils.widgets

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
import com.appcoins.wallet.sampleapp.diceroll.core.design.DiceRollIcons

@Composable
fun ShowError(stringResource: Int) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(vertical = 64.dp, horizontal = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    Icon(
      imageVector = DiceRollIcons.wifiOff, contentDescription = null,
      modifier = Modifier
        .size(150.dp)
    )
    Text(
      text = stringResource(id = stringResource),
      style = MaterialTheme.typography.headlineSmall,
    )
  }
}