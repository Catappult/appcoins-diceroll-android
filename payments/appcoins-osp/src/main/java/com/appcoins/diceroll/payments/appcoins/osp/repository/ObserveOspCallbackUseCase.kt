package com.appcoins.diceroll.payments.appcoins.osp.repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveOspCallbackUseCase @Inject constructor(
  private val ospRepository: OspRepository
) {

  operator fun invoke(): Flow<OspCallbackResult> {
    return ospRepository.observeCallbackResult()
  }
}