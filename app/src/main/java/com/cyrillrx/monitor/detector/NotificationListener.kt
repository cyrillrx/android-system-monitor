package com.cyrillrx.monitor.detector

import android.content.Context
import android.util.Log
import com.cyrillrx.monitor.service.AlertHistory
import com.cyrillrx.monitor.utils.NotificationUtils

/**
 * @author Cyril Leroux
 *          Created on 08/07/2019.
 */
class NotificationListener(
    private val context: Context,
    private val notificationId: Int,
    private val statName: String) : ThresholdListener {

    override fun onThresholdExceeded(value: Int?, threshold: Int?) {
        val message = "$statName alert triggered - value: $value% threshold: $threshold%"

        Log.i(TAG, message)
        NotificationUtils.notifyAlert(context, notificationId, statName, message)
        AlertHistory.addEntry(message)
    }

    override fun onValueReturnsToNormal(value: Int?, threshold: Int?) {

        val message = "$statName back to normal - value: $value% threshold: $threshold%"
        Log.i(TAG, message)

        if (notifyAlertRecovered) {
            NotificationUtils.notifyAlert(context, notificationId, statName, message)
            AlertHistory.addEntry(message)

        } else {
            NotificationUtils.cancelNotification(context, notificationId)
        }
    }

    companion object {
        private val TAG = NotificationListener::class.java.simpleName

        var notifyAlertRecovered = false
    }
}