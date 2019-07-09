package com.cyrillrx.monitor.detector

import com.cyrillrx.monitor.provider.ValueUpdatedListener

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
abstract class ThresholdDetector : ValueUpdatedListener {

    private var lastKnownValue: Int? = null
    private var threshold: Int? = null

    private var thresholdReached: Boolean = false

    /** @param newThreshold The threshold in percent [0-100] */
    fun updateThreshold(newThreshold: Int) {

        if (newThreshold < MIN_VALUE || newThreshold > MAX_VALUE) {
            throw IllegalArgumentException("thresholdPercentage should be between $MIN_VALUE and $MAX_VALUE but was $newThreshold")
        }

        // Save the old state to be able to detect threshold crossing
        val wasThresholdReached = thresholdReached

        // Update inner attributes
        threshold = newThreshold
        thresholdReached = isThresholdReached(lastKnownValue, threshold)

        // Detect threshold crossing
        if (wasThresholdReached && !thresholdReached) {
            thresholdCrossedDown(lastKnownValue, threshold)

        } else if (!wasThresholdReached && thresholdReached) {
            thresholdCrossedUp(lastKnownValue, threshold)
        }
    }

    fun disableThreshold() {
        threshold = null

        if (thresholdReached) {
            thresholdCrossedDown(lastKnownValue, threshold)
        }
    }

    override fun onValueUpdated(newValue: Int) {

        // Save the old state to be able to detect threshold crossing
        val wasThresholdReached = thresholdReached

        // Update inner attributes
        lastKnownValue = newValue
        thresholdReached = isThresholdReached(lastKnownValue, threshold)

        // Detect threshold crossing
        if (wasThresholdReached && !thresholdReached) {
            thresholdCrossedDown(lastKnownValue, threshold)

        } else if (!wasThresholdReached && thresholdReached) {
            thresholdCrossedUp(lastKnownValue, threshold)
        }
    }

    protected abstract fun thresholdCrossedUp(value: Int?, threshold: Int?)

    protected abstract fun thresholdCrossedDown(value: Int?, threshold: Int?)

    protected abstract fun isThresholdReached(value: Int?, threshold: Int?): Boolean

    companion object {
        private const val MIN_VALUE = 0
        private const val MAX_VALUE = 100
    }
}