package com.cyrillrx.monitor.provider

import com.cyrillrx.monitor.detector.ThresholdDetector

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class ThresholdDetectorCountingAlerts : ThresholdDetector() {

    var alertTriggeredCount = 0
    var alertCanceledCount = 0

    override fun onThresholdExceeded(value: Int?, threshold: Int?) {
        super.onThresholdExceeded(value, threshold)
        alertTriggeredCount++
    }

    override fun onValueReturnsToNormal(value: Int?, threshold: Int?) {
        super.onValueReturnsToNormal(value, threshold)
        alertCanceledCount++
    }

    override fun isThresholdExceeded(value: Int?, threshold: Int?): Boolean {
        // We use '>' instead of '>=' because we consider that
        // an equality do not triggers the threshold
        return value != null && threshold != null && value > threshold
    }
}