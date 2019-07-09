package com.cyrillrx.monitor.provider

import android.content.Context
import android.os.Handler
import android.util.Log

/**
 * @author Cyril Leroux
 *          Created on 06/07/2019.
 */
abstract class StatProvider(private val name: String) {

    private val listeners = HashSet<ValueUpdatedListener>()

    private val handler = Handler()

    private var runnable: Runnable? = null
    private var lastKnownValue: Int? = null

    protected abstract fun fetchData(context: Context): Int

    fun getLastKnownValue() = lastKnownValue

    fun addListener(context: Context, listener: ValueUpdatedListener) {

        // If listener is the first to be added, start fetching data updates
        if (listeners.add(listener) && listeners.size == 1) {
            startFetching(context)
        }
    }

    fun removeListener(listener: ValueUpdatedListener) {

        // If listener was the last to be removed, stop fetching data updates
        if (listeners.remove(listener) && listeners.isEmpty()) {
            stopFetching()
        }
    }

    private fun startFetching(context: Context) {
        Log.i(TAG, "startFetching")

        runnable = Runnable {
            val newValue = fetchData(context)

            Log.v(TAG, "data fetched - $name: $newValue")
            if (newValue != lastKnownValue) {
                updateLastKnownValue(newValue)
            }

            runnable?.let { handler.postDelayed(it, INTERVAL_MS) }

        }.also { handler.post(it) }
    }

    private fun stopFetching() {
        runnable?.let { handler.removeCallbacks(it) }
        runnable = null

    }

    private fun updateLastKnownValue(newValue: Int) {
        Log.i(TAG, "updateLastKnownValue() - $name: $newValue")

        listeners.forEach { it.onValueUpdated(newValue) }
        lastKnownValue = newValue
    }

    companion object {
        private val TAG = StatProvider::class.java.simpleName
        private const val INTERVAL_MS = 10_000L
    }
}