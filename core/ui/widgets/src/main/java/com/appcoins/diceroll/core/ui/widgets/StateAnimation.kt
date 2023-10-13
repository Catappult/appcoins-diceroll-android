package com.appcoins.diceroll.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadingAnimation(
  titleStringResource: Int,
  bodyStringResource: Int? = null,
) {
  StateLottieAnimation(
    content = {
      Text(
        text = stringResource(id = titleStringResource),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
      )
      if (bodyStringResource != null) {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
          text = stringResource(id = bodyStringResource),
          style = MaterialTheme.typography.bodyMedium,
          textAlign = TextAlign.Center,
        )
      }
    }
  )
}

@Composable
fun SuccessAnimation(
  titleStringResource: Int,
  bodyStringResource: Int? = null,
) {
  StateLottieAnimation(
    isSuccess = true,
    content = {
      Text(
        text = stringResource(id = titleStringResource),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
      )
      if (bodyStringResource != null) {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
          text = stringResource(id = bodyStringResource),
          style = MaterialTheme.typography.bodyMedium,
          textAlign = TextAlign.Center,
        )
      }
    }
  )
}

@Composable
fun ErrorAnimation(
  titleStringResource: Int,
  bodyStringResource: Int? = null,
) {
  StateLottieAnimation(
    isFailed = true,
    content = {
      Text(
        text = stringResource(id = titleStringResource),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
      )
      if (bodyStringResource != null) {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
          text = stringResource(id = bodyStringResource),
          style = MaterialTheme.typography.bodyMedium,
          textAlign = TextAlign.Center,
        )
      }
    }
  )
}

@Composable
fun StateLottieAnimation(
  modifier: Modifier = Modifier,
  isSuccess: Boolean = false,
  isFailed: Boolean = false,
  content: @Composable ColumnScope.() -> Unit
) {
  Column(
    modifier = Modifier
      .verticalScroll(rememberScrollState())
      .padding(vertical = 32.dp, horizontal = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly,
  ) {
    val clipSpecs = LottieClipSpec.Progress(
      min = if (isFailed) 0.499f else 0.0f,
      max = if (isSuccess) 0.44f else if (isFailed) 0.95f else 0.282f
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_loading_success_failed))

    LottieAnimation(
      modifier = modifier,
      composition = composition,
      iterations = if (isSuccess || isFailed) 1 else LottieConstants.IterateForever,
      clipSpec = clipSpecs,
    )
    Spacer(modifier = Modifier.padding(8.dp))
    content()
  }
}