package com.cyrillrx.monitor.provider

import com.cyrillrx.monitor.detector.ThresholdDetector

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class ThresholdDetectorCountingAlerts : ThresholdDetector() {

    var alertTriggeredCount = 0
    var alertCanceledCount = 0

    override fun thresholdCrossedUp(value: Int?, threshold: Int?) {
        alertTriggeredCount++
    }

    override fun thresholdCrossedDown(value: Int?, threshold: Int?) {
        alertCanceledCount++
    }

    override fun isThresholdReached(value: Int?, threshold: Int?): Boolean {
        // >= because we consider that an equality triggers the threshold
        return value != null && threshold != null && value >= threshold
    }
}