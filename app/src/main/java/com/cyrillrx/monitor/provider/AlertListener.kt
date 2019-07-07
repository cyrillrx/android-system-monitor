package com.cyrillrx.monitor.provider

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
interface AlertListener {

    fun onAlertTriggered(percentage: Int?)

    fun onAlertCanceled(percentage: Int?)
}