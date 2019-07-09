package com.cyrillrx.monitor.ui

import android.graphics.Color
import android.widget.TextView
import com.cyrillrx.monitor.detector.ThresholdListener
import com.cyrillrx.monitor.provider.ValueUpdatedListener

/**
 * @author Cyril Leroux
 *         Created on 09/07/2019.
 */
class UiUpdater(private val textView: TextView) : ValueUpdatedListener, ThresholdListener {

    override fun onValueUpdated(newValue: Int?) {

        textView.text = if (newValue == null) {
            "??"
        } else {
            "$newValue%"
        }
    }

    override fun onThresholdExceeded(value: Int?, threshold: Int?) {
        textView.setTextColor(Color.RED)
    }

    override fun onValueReturnsToNormal(value: Int?, threshold: Int?) {
        textView.setTextColor(Color.GREEN)
    }

    fun setThresholdExceeded(thresholdExceeded: Boolean?) {
        if (thresholdExceeded == true) {
            textView.setTextColor(Color.RED)
        } else {
            textView.setTextColor(Color.GREEN)
        }
    }
}