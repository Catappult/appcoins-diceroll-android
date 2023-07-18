package com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.repository

import com.appcoins.wallet.sampleapp.diceroll.core.db.model.DiceRollEntity
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll

fun DiceRollEntity.mapToDiceRoll(): DiceRoll {
  return DiceRoll(
    id = this.id,
    rollWin = this.rollWin,
    betNumber = this.betNumber,
    resultNumber = this.resultNumber
  )
}

fun DiceRoll.mapToDiceRollEntity(): DiceRollEntity {
  return DiceRollEntity(
    id = this.id,
    rollWin = this.rollWin,
    betNumber = this.betNumber,
    resultNumber = this.resultNumber
  )
}

fun List<DiceRollEntity>.mapToDiceRollList(): List<DiceRoll> {
  return this.map { diceRollEntity ->
    DiceRoll(
      id = diceRollEntity.id,
      rollWin = diceRollEntity.rollWin,
      betNumber = diceRollEntity.betNumber,
      resultNumber = diceRollEntity.resultNumber
    )
  }
}


