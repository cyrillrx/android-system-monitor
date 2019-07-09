package com.cyrillrx.monitor.service

/**
 * @author Cyril Leroux
 *         Created on 10/07/2019.
 */
object AlertHistory {

    private var history = ""

    var onHistoryUpdated: ((String) -> Unit)? = null

    fun getHistory() = history

    fun addEntry(message: String) {

        history += "$message\n"
        onHistoryUpdated?.invoke(history)
    }
}