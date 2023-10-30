package com.appcoins.diceroll.core.utils

import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.onCompletion

/**
 * Simple event bus using Kotlin coroutines and channels to publish and listen to events.
 */
object EventBus {

  /**
   * The channel used for publishing and listening to events. It is recreated upon completion.
   */
  var eventChannel = Channel<Any>()

  /**
   * Creates a new event channel with a CONFLATED buffer type.
   *
   * @return A new channel for handling events with a CONFLATED buffer type.
   */
  fun createEventChannel(): Channel<Any> {
    return Channel(Channel.CONFLATED)
  }

  /**
   * Publishes an event to the event bus, so that all listeners can receive it.
   *
   * @param event The event to be published.
   */
  suspend fun publish(event: Any) {
    Log.d(CUSTOM_TAG, "EventBus: publish: event $event")
    eventChannel.send(event)
  }

  /**
   * Listens for events of a specific type and provides a Flow to collect the events.
   *
   * @param T The type of event to listen for.
   * @return A Flow of events of the specified type [T].
   */
  inline fun <reified T> listen(): Flow<T> {
    Log.d(CUSTOM_TAG, "EventBus: listen: T ${T::class.java}")
    return eventChannel.consumeAsFlow().filterIsInstance<T>()
      .onCompletion {
        eventChannel = createEventChannel()
      }
  }
}
