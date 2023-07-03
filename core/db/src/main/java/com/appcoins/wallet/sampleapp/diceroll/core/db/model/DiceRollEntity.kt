package com.appcoins.wallet.sampleapp.diceroll.core.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DiceRollEntity")
data class DiceRollEntity(
  @PrimaryKey
  val id: String,
  val rollResult: Boolean,
  val betNumber: Int,
  val resultNumber: Int,
)