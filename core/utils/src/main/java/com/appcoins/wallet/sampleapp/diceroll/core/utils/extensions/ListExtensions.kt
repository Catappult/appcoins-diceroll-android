package com.appcoins.wallet.sampleapp.diceroll.core.utils.extensions

fun List<Float>.toPercent(): List<Float> {
    return this.map { item ->
        item * 100 / this.sum()
    }
}