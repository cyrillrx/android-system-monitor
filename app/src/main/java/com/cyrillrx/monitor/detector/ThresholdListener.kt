package com.cyrillrx.monitor.detector

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
interface ThresholdListener {

    fun onThresholdExceeded(value: Int?, threshold: Int?)

    fun onValueReturnsToNormal(value: Int?, threshold: Int?)
}