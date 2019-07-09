package com.cyrillrx.monitor.detector.impl

import android.content.Context
import com.cyrillrx.monitor.detector.NotificationListener
import com.cyrillrx.monitor.detector.ThresholdDetector

/**
 * @author Cyril Leroux
 *         Created on 09/07/2019.
 */
class BelowThresholdDetector(context: Context, statName: String, threshold: Int) : ThresholdDetector() {

    private val notificationListener = NotificationListener(context, statName)

    init {
        updateThreshold(threshold)
    }

    override fun thresholdCrossedUp(value: Int?, threshold: Int?) {
        notificationListener.onAlertCanceled(value, threshold)
    }

    override fun thresholdCrossedDown(value: Int?, threshold: Int?) {
        notificationListener.onAlertTriggered(value, threshold)
    }

    override fun isThresholdReached(value: Int?, threshold: Int?): Boolean {
        // <= because we consider that an equality triggers the threshold
        return value != null && threshold != null && value <= threshold
    }
}