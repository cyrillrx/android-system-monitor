package com.cyrillrx.monitor.provider

import com.cyrillrx.monitor.detector.ThresholdDetector

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class ThresholdDetectorCountingAlerts : ThresholdDetector() {

    var alertTriggeredCount = 0
    var alertCanceledCount = 0

    override fun thresholdCrossedUp(value: Int) {
        alertTriggeredCount++
    }

    override fun thresholdCrossedDown(value: Int) {
        alertCanceledCount++
    }

    override fun isThresholdReached(value: Int): Boolean {
        val thresholdPercent = thresholdPercent ?: return false
        return value >= thresholdPercent
    }
}