package com.cyrillrx.monitor.detector

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
interface AlertListener {

    fun onAlertTriggered(value: Int?, threshold: Int?)

    fun onAlertCanceled(value: Int?, threshold: Int?)
}