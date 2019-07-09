package com.cyrillrx.monitor.provider.impl

import android.app.ActivityManager
import android.content.Context
import com.cyrillrx.monitor.R
import com.cyrillrx.monitor.provider.StatProvider

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class RamUsageProvider(context: Context) : StatProvider(context.getString(R.string.stat_label_ram_usage)) {

    override fun fetchData(context: Context): Int {

        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val availableMemBytes: Long = memoryInfo.availMem
        val totalMemBytes: Long = memoryInfo.totalMem
        val usedMemBytes: Long = totalMemBytes - availableMemBytes

        val memoryUsagePercentage = usedMemBytes.toDouble() / totalMemBytes.toDouble() * 100.0

        return memoryUsagePercentage.toInt()
    }
}