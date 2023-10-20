package com.appcoins.diceroll.payments.appcoins.osp.data.usecases

import com.appcoins.diceroll.payments.appcoins.osp.data.OspCallbackResult
import com.appcoins.diceroll.payments.appcoins.osp.data.repository.OspRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveOspCallbackUseCase @Inject constructor(
  private val ospRepository: OspRepository
) {

  operator fun invoke(): Flow<OspCallbackResult> {
    return ospRepository.observeCallbackResult()
  }
}