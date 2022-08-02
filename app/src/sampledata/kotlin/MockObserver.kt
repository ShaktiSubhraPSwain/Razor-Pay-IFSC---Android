package com.example.razorpayifsc

import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.slot

/**
 * Captured the datum of observer and returned the list of data
 */
inline fun <reified T : Any> Observer<T>.captureObserverChanges(list: ArrayList<T>): List<T> {
    // create slot
    val slot = slot<T>()

    // capture value on every call
    every { onChanged(capture(slot)) } answers {
        // store captured value
        list.add(slot.captured)
    }

    return list
}

