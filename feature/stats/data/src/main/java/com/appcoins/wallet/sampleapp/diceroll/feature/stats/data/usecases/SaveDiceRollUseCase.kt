package com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.usecases

import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.repository.DiceRollRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveDiceRollUseCase @Inject constructor(private val diceRollRepository: DiceRollRepository) {

  suspend operator fun invoke(diceRoll: DiceRoll) = diceRollRepository.saveDiceRoll(diceRoll)
}