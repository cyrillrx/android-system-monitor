package com.cyrillrx.monitor.provider

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
open class StatWatcher(protected var alertListener: AlertListener? = null) : ValueUpdatedListener {

    private var lastKnownValue: Int = 0
    private var thresholdPercent: Int? = null

    private var thresholdReached: Boolean = false

    /** @param newThreshold The threshold in percent [0-100] */
    fun updateThreshold(newThreshold: Int) {

        if (newThreshold < MIN_VALUE || newThreshold > MAX_VALUE) {
            throw IllegalArgumentException("thresholdPercentage should be between $MIN_VALUE and $MAX_VALUE but was $newThreshold")
        }

        // Save the old state to be able to detect threshold crossing
        val wasThresholdReached = thresholdReached

        // Update inner attributes
        thresholdPercent = newThreshold
        thresholdReached = isThresholdReached(lastKnownValue)

        // Detect threshold crossing
        if (wasThresholdReached && !thresholdReached) {
            alertListener?.onAlertCanceled(lastKnownValue)

        } else if (!wasThresholdReached && thresholdReached) {
            alertListener?.onAlertTriggered(lastKnownValue)
        }
    }

    fun disableThreshold() {
        thresholdPercent = null

        if (thresholdReached) {
            alertListener?.onAlertCanceled(lastKnownValue)
        }
    }

    override fun onValueUpdated(newValue: Int) {

        // Save the old state to be able to detect threshold crossing
        val wasThresholdReached = thresholdReached

        // Update inner attributes
        lastKnownValue = newValue
        thresholdReached = isThresholdReached(newValue)

        // Detect threshold crossing
        if (wasThresholdReached && !thresholdReached) {
            alertListener?.onAlertCanceled(lastKnownValue)

        } else if (!wasThresholdReached && thresholdReached) {
            alertListener?.onAlertTriggered(lastKnownValue)
        }
    }

    private fun isThresholdReached(value: Int?): Boolean {
        val thresholdPercent = thresholdPercent ?: return false
        // >= because we consider that an equality triggers the threshold
        return value != null && value >= thresholdPercent
    }

    companion object {
        private const val MIN_VALUE = 0
        private const val MAX_VALUE = 100
    }
}