package com.cyrillrx.monitor.provider.impl

import android.content.Context
import android.os.Build
import com.cyrillrx.monitor.R
import com.cyrillrx.monitor.provider.StatProvider
import java.io.BufferedReader
import java.io.FileReader
import kotlin.math.roundToInt

/**
 * Inspired by https://github.com/AntonioRedondo/AnotherMonitor
 * org.anothermonitor.ServiceReader
 *
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class CpuLoadProvider(context: Context) : StatProvider(context.getString(R.string.stat_label_cpu_load)) {

    override val intervalMs: Long = 1_000L

    private var lastTotalCpu: Float = 0f
    private var lastUsedCpu: Float = 0f

    override fun fetchData(context: Context): Int? {

        if (Build.VERSION.SDK_INT >= 26) {
            return null
        }

        var totalCpu = 0f
        var usedCpu = 0f
        var sa: Array<String>?

        BufferedReader(FileReader("/proc/stat"))
            .use { reader ->
                sa = reader.readLine()?.split("[ ]+".toRegex(), 9)?.toTypedArray()
                usedCpu = sa.getAsFloat(1) + sa.getAsFloat(2) + sa.getAsFloat(3)
                totalCpu = usedCpu + sa.getAsFloat(4) + sa.getAsFloat(5) + sa.getAsFloat(6) + sa.getAsFloat(7)
            }

        var cpuTotal = 0f
        if (lastTotalCpu != 0f) {
            val totalT = totalCpu - lastTotalCpu
            val workT = usedCpu - lastUsedCpu
            cpuTotal = workT / totalT * 100f
        }

        lastTotalCpu = totalCpu
        lastUsedCpu = usedCpu

        return cpuTotal.roundToInt()
    }

    private fun Array<String>?.getAsFloat(index: Int): Float = this?.get(index)?.toFloat() ?: 0f
}