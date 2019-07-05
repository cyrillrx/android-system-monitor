package com.cyrillrx.monitor.provider

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class TestProvider(maxValue: Long) : StatProvider(maxValue) {

    var alertTriggeredCount = 0
    var alertCanceledCount = 0

    init {
        alertListener = object : AlertListener {
            override fun onAlertTriggered(percentage: Float?) {
                alertTriggeredCount++
            }

            override fun onAlertCanceled(percentage: Float?) {
                alertCanceledCount++
            }
        }
    }
}