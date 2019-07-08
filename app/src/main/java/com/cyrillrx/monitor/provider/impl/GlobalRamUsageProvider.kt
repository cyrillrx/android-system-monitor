package com.cyrillrx.monitor.provider.impl

import android.app.ActivityManager
import android.content.Context
import com.cyrillrx.monitor.provider.StatProvider
import com.cyrillrx.monitor.provider.ValueUpdatedListener

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class GlobalRamUsageProvider(private val context: Context, listener: ValueUpdatedListener) : StatProvider(listener) {

    override fun fetchData(): Int {

        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val availableMemBytes: Long = memoryInfo.availMem
        val totalMemBytes: Long = memoryInfo.totalMem
        val usedMemBytes: Long = totalMemBytes - availableMemBytes

        val memoryUsagePercentage = usedMemBytes.toDouble() / totalMemBytes.toDouble() * 100.0
        val result = memoryUsagePercentage.toInt()

        listener.onValueUpdated(result)
        return result
    }
}