package com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.repository

import com.appcoins.wallet.sampleapp.diceroll.core.db.model.DiceRollDao
import com.appcoins.wallet.sampleapp.diceroll.core.db.model.DiceRollEntity
import com.appcoins.wallet.sampleapp.diceroll.core.utils.Dispatchers
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiceRollRepository @Inject constructor(
  private val dao: DiceRollDao,
  private val dispatchers: Dispatchers
) {
  fun getBdDiceRolls(): Flow<List<DiceRoll>> =
    dao.getDiceRollEntityListFlow().map { entityList ->
      entityList.map { rollEntity ->
        rollEntity.mapToDiceRoll()
      }
    }.flowOn(dispatchers.io)

  suspend fun saveDiceRoll(diceRoll: DiceRoll) {
    dao.saveDiceRollEntity(diceRoll.mapToDiceRollEntity())
  }

  fun getAttemptsLeft() : Flow<Int?> {
    return dao.getAttemptsLeft()
  }
}