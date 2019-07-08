package com.cyrillrx.monitor.provider.impl

import com.cyrillrx.monitor.provider.StatProvider
import com.cyrillrx.monitor.provider.ValueUpdatedListener

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class SystemCpuLoadProvider(listener: ValueUpdatedListener) : StatProvider(listener) {

    override fun fetchData(): Int {

        // TODO
        val fakeData = 0

        listener.onValueUpdated(fakeData)
        return fakeData
    }
}