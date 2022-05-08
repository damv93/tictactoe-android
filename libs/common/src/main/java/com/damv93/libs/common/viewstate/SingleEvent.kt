package com.damv93.libs.common.viewstate

/**
 * An event that can be consumed only once.
 * Usually used as a field of an observable state (e.g., an state wrapped by a LiveData).
 *
 */
class SingleEvent<out T>(private val content: T) {

    var consumed = false
        private set // Allow external read but not write

    /**
     * Consumes the content if it has not been consumed yet.
     * @return The unconsumed content or `null` if it was consumed already.
     */
    fun consume(): T? {
        return if (consumed) {
            null
        } else {
            consumed = true
            content
        }
    }

    /**
     * @return The content regardless of whether it has been consumed or not.
     */
    fun peek(): T = content
}