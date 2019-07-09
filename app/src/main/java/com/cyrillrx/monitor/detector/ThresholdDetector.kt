package com.cyrillrx.monitor.detector

import com.cyrillrx.monitor.provider.ValueUpdatedListener

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
abstract class ThresholdDetector : ValueUpdatedListener, ThresholdListener {

    private val listeners = HashSet<ThresholdListener>()

    private var lastKnownValue: Int? = null
    private var threshold: Int? = null

    private var thresholdExceeded: Boolean = false

    fun isThresholdExceeded(): Boolean = thresholdExceeded

    fun addListener(listener: ThresholdListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: ThresholdListener) {
        listeners.remove(listener)
    }

    /** @param newThreshold The threshold in percent [0-100] */
    fun updateThreshold(newThreshold: Int) {

        if (newThreshold < MIN_VALUE || newThreshold > MAX_VALUE) {
            throw IllegalArgumentException("thresholdPercentage should be between $MIN_VALUE and $MAX_VALUE but was $newThreshold")
        }

        // Save the old state to be able to detect threshold crossing
        val wasThresholdExceeded = thresholdExceeded

        // Update inner attributes
        threshold = newThreshold
        thresholdExceeded = isThresholdExceeded(lastKnownValue, threshold)

        // Detect threshold crossing
        if (wasThresholdExceeded && !thresholdExceeded) {
            onValueReturnsToNormal(lastKnownValue, threshold)

        } else if (!wasThresholdExceeded && thresholdExceeded) {
            onThresholdExceeded(lastKnownValue, threshold)
        }
    }

    fun disableThreshold() {
        threshold = null

        if (thresholdExceeded) {
            onValueReturnsToNormal(lastKnownValue, threshold)
        }
    }

    override fun onValueUpdated(newValue: Int?) {

        // Save the old state to be able to detect threshold crossing
        val wasThresholdExceeded = thresholdExceeded

        // Update inner attributes
        lastKnownValue = newValue
        thresholdExceeded = isThresholdExceeded(lastKnownValue, threshold)

        // Detect threshold crossing
        if (wasThresholdExceeded && !thresholdExceeded) {
            onValueReturnsToNormal(lastKnownValue, threshold)

        } else if (!wasThresholdExceeded && thresholdExceeded) {
            onThresholdExceeded(lastKnownValue, threshold)
        }
    }

    override fun onThresholdExceeded(value: Int?, threshold: Int?) {
        listeners.forEach { it.onThresholdExceeded(value, threshold) }
    }

    override fun onValueReturnsToNormal(value: Int?, threshold: Int?) {
        listeners.forEach { it.onValueReturnsToNormal(value, threshold) }
    }

    protected abstract fun isThresholdExceeded(value: Int?, threshold: Int?): Boolean

    companion object {
        private const val MIN_VALUE = 0
        private const val MAX_VALUE = 100
    }
}