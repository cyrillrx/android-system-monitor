package com.cyrillrx.monitor.provider

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
open class StatProvider(private val maxValue: Long) {

    private var lastKnownValue: Long = 0L
    private var lastKnownPercent: Float = 0f

    private var thresholdValue: Long? = null
    private var thresholdPercent: Float? = null

    private var thresholdReached: Boolean = false

    var alertListener: AlertListener? = null

    fun updateThreshold(newThresholdPercent: Float) {

        if (newThresholdPercent < 0f || newThresholdPercent > 1f) {
            throw IllegalArgumentException("thresholdPercentage should be between 0 and 1 but was $newThresholdPercent")
        }

        // Save the old state to be able to detect threshold crossing
        val wasThresholdReached = thresholdReached

        // Update inner attributes
        thresholdPercent = newThresholdPercent
        thresholdValue = (maxValue.toFloat() * newThresholdPercent).toLong()
        thresholdReached = isThresholdReached(lastKnownValue)

        // Detect threshold crossing
        if (wasThresholdReached && !thresholdReached) {
            onAlertCanceled(lastKnownPercent)

        } else if (!wasThresholdReached && thresholdReached) {
            onAlertTriggered(lastKnownPercent)
        }
    }

    fun disableThreshold() {
        thresholdValue = null
        thresholdPercent = null

        if (thresholdReached) {
            onAlertCanceled(lastKnownPercent)
        }
    }

    fun updateValue(newValue: Long) {

        // Save the old state to be able to detect threshold crossing
        val wasThresholdReached = thresholdReached

        // Update inner attributes
        lastKnownValue = newValue
        lastKnownPercent = newValue.toFloat() / maxValue.toFloat()
        thresholdReached = isThresholdReached(newValue)

        // Detect threshold crossing
        if (wasThresholdReached && !thresholdReached) {
            onAlertCanceled(lastKnownPercent)

        } else if (!wasThresholdReached && thresholdReached) {
            onAlertTriggered(lastKnownPercent)
        }
    }

    protected open fun onAlertTriggered(crossingPercent: Float) {
        alertListener?.onAlertTriggered(crossingPercent)
    }

    protected open fun onAlertCanceled(crossingPercent: Float?) {
        alertListener?.onAlertCanceled(crossingPercent)
    }

    private fun isThresholdReached(value: Long?): Boolean {
        val thresholdValue = thresholdValue ?: return false
        // >= because we consider that an equality triggers the threshold
        return value != null && value >= thresholdValue
    }
}