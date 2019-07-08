package com.cyrillrx.monitor.provider.impl

import com.cyrillrx.monitor.provider.StatProvider

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class CpuLoadProvider : StatProvider() {

    override fun fetchData(): Int {

        // TODO
        val fakeData = 0
        return fakeData
    }
}