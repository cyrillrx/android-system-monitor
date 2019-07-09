package com.cyrillrx.monitor.detector.impl

import com.cyrillrx.monitor.detector.ThresholdDetector

/**
 * @author Cyril Leroux
 *         Created on 09/07/2019.
 */
class AboveThresholdDetector(threshold: Int) : ThresholdDetector() {

    init {
        updateThreshold(threshold)
    }

    override fun isThresholdExceeded(value: Int?, threshold: Int?): Boolean {
        // We use '>' instead of '>=' because we consider that
        // an equality do not triggers the threshold
        return value != null && threshold != null && value > threshold
    }
}