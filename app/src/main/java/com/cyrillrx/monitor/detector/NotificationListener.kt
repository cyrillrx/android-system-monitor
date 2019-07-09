package com.cyrillrx.monitor.detector

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.cyrillrx.monitor.utils.NotificationUtils

/**
 * @author Cyril Leroux
 *          Created on 08/07/2019.
 */
class NotificationListener(
    private val context: Context,
    private val notificationId: Int,
    private val statName: String) : AlertListener {

    override fun onAlertTriggered(value: Int?, threshold: Int?) {
        val message = "$statName alert triggered: $value% (threshold: $threshold)"

        Log.i(TAG, message)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        NotificationUtils.notifyAlert(context, notificationId, statName, message)
    }

    override fun onAlertCanceled(value: Int?, threshold: Int?) {
        val message = "$statName alert canceled: $value% (threshold: $threshold)"

        Log.i(TAG, message)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        NotificationUtils.notifyAlert(context, notificationId, statName, message)
    }

    companion object {
        private val TAG = NotificationListener::class.java.simpleName
    }
}