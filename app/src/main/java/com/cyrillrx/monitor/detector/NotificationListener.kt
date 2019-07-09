package com.cyrillrx.monitor.detector

import android.content.Context
import com.cyrillrx.monitor.utils.NotificationUtils

/**
 * @author Cyril Leroux
 *          Created on 08/07/2019.
 */
class NotificationListener(private val context: Context, private val statName: String) : AlertListener {

    override fun onAlertTriggered(percentage: Int?) {
        NotificationUtils.notifyAlert(context, statName, "$statName alert triggered: $percentage%")
    }

    override fun onAlertCanceled(percentage: Int?) {
        NotificationUtils.notifyAlert(context, statName, "$statName alert canceled: $percentage%")
    }
}