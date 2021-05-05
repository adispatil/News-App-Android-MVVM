package com.example.newsapp.utils

import androidx.lifecycle.Observer

open class Event(vararg content1: Any) {
    var content: Array<out Any> = content1

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        internal set
}

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
class EventObserverNew(private val onEventUnhandledContent: (Any?) -> Unit) :
    Observer<Event> {
    override fun onChanged(event: Event) {
        if (!event.hasBeenHandled) {
            onEventUnhandledContent(
                event.content
            )
            event.hasBeenHandled = true
        }
    }
}
