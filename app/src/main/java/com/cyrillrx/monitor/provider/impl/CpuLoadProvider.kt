package com.cyrillrx.monitor.provider.impl

import android.content.Context
import com.cyrillrx.monitor.R
import com.cyrillrx.monitor.provider.StatProvider

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class CpuLoadProvider(context: Context) : StatProvider(context.getString(R.string.stat_label_cpu)) {

    override fun fetchData(context: Context): Int {

        // TODO
        val fakeData = 0
        return fakeData
    }
}