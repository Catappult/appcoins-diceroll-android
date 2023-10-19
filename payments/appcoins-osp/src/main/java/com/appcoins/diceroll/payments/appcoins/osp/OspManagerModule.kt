package com.appcoins.diceroll.payments.appcoins.osp

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This module provides a workaround to access Hilt entry points from Compose.
 *
 * Hilt entry points like OspManagerEntryPoint are normally accessed via the
 * EntryPoints API which requires an Android Context. However, in Compose you don't
 * have access to a Context in @Composable functions.
 *
 * This module provides a way to lazily get the OspManagerEntryPoint by using the
 * LocalContext available in Compose.
 *
 * The approach is based on this Stack Overflow answer:
 * https://stackoverflow.com/a/73007239
 */

private lateinit var ospManagerEntryPoint: OspManagerEntryPoint

@Composable
fun requireOspManagerEntryPoint(): OspManagerEntryPoint {
  if (!::ospManagerEntryPoint.isInitialized) {
    ospManagerEntryPoint =
      EntryPoints.get(
        LocalContext.current.applicationContext,
        OspManagerEntryPoint::class.java,
      )
  }
  return ospManagerEntryPoint
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface OspManagerEntryPoint {
  val ospManager: OspManager
}
