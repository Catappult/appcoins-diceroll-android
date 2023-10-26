package com.appcoins.diceroll.core.utils.extensions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.singleOrNull
import kotlin.time.Duration


fun <T> Flow<T>.emitEvery(interval: Duration): Flow<T> = flow {
  while (true) {
    collect { value ->
      emit(value)
      delay(interval)
    }
  }
}

fun <T> Flow<T>.repeatUntil(predicate: suspend (value: T, attempt: Long) -> Boolean): Flow<T> =
  flow {
    var attempt = 0L
    var shallRepeat: Boolean
    do {
      shallRepeat = false
      val value = this@repeatUntil.singleOrNull()
      if (value != null) {
        if (!predicate(value, attempt)) {
          shallRepeat = true
          attempt++
        } else {
          emit(value)
        }
      }
    } while (shallRepeat)
  }


fun <T, R> Flow<T>.repeatUntil(
  map: suspend (T) -> R,
  predicate: suspend FlowCollector<R>.(item: T, result: R, attempt: Long) -> Boolean
): Flow<R> {

  tailrec suspend fun FlowCollector<R>.retryCollect(item: T, attempt: Long) {
    val result = map(item)
    if (predicate(item, result, attempt)) {
      retryCollect(item, attempt = attempt + 1)
    } else {
      emit(result)
    }
  }

  return flow {
    collect { item ->
      retryCollect(item, attempt = 1L)
    }
  }
}