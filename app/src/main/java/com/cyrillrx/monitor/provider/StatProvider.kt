package com.cyrillrx.monitor.provider

/**
 * @author Cyril Leroux
 *          Created on 06/07/2019.
 */
abstract class StatProvider {

    private val listeners = HashSet<ValueUpdatedListener>()
    private val intervalMs = 10_000L

    private var running = false

    protected abstract fun fetchData(): Int

    private fun startFetching() {
        if (running) return

        while (running) {
            val newValue = fetchData()
            listeners.forEach { it.onValueUpdated(newValue) }
            Thread.sleep(intervalMs)
        }
    }

    private fun stopFetching() {
        running = false
    }

    fun addListener(listener: ValueUpdatedListener) {

        // If listener is the first to be added, start fetching data updates
        if (listeners.add(listener) && listeners.size == 1) {
            startFetching()
        }
    }

    fun removeListener(listener: ValueUpdatedListener) {

        // If listener was the last to be removed, stop fetching data updates
        if (listeners.remove(listener) && listeners.isEmpty()) {
            stopFetching()
        }
    }
}