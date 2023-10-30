package com.appcoins.diceroll.core.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance

/**
 * Simple event bus using Kotlin coroutines and channels to publish and listen to events.
 */
object EventBus {

  val eventChannel = Channel<Any>()

  /**
   * Publishes an event to the event bus, so that all listeners can receive it.
   *
   * @param event The event to be published.
   */
  suspend fun publish(event: Any) {
    eventChannel.send(event)
  }

  /**
   * Listens for events of a specific type and provides a Flow to collect the events.
   *
   * @param T The type of event to listen for.
   * @return A Flow of events of the specified type [T].
   */
  inline fun <reified T> listen(): Flow<T> {
    return eventChannel.consumeAsFlow().filterIsInstance()
  }
}
