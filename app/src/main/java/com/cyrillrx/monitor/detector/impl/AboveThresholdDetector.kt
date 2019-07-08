package com.cyrillrx.monitor.detector.impl

import android.content.Context
import com.cyrillrx.monitor.detector.NotificationListener
import com.cyrillrx.monitor.detector.ThresholdDetector

/**
 * @author Cyril Leroux
 *         Created on 09/07/2019.
 */
open class AboveThresholdDetector(context: Context, statName: String, threshold: Int) : ThresholdDetector() {

    private val notificationListener = NotificationListener(context, statName)

    init {
        updateThreshold(threshold)
    }

    override fun thresholdCrossedUp(value: Int) {
        notificationListener.onAlertTriggered(value)
    }

    override fun thresholdCrossedDown(value: Int) {
        notificationListener.onAlertCanceled(value)
    }

    override fun isThresholdReached(value: Int): Boolean {
        val thresholdPercent = thresholdPercent ?: return false
        // >= because we consider that an equality triggers the threshold
        return value >= thresholdPercent
    }
}