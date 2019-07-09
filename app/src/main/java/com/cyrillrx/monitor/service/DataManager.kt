package com.cyrillrx.monitor.service

import android.content.Context
import android.util.Log
import com.cyrillrx.monitor.R
import com.cyrillrx.monitor.detector.ThresholdDetector
import com.cyrillrx.monitor.detector.impl.AboveThresholdDetector
import com.cyrillrx.monitor.detector.impl.BelowThresholdDetector
import com.cyrillrx.monitor.provider.ValueUpdatedListener
import com.cyrillrx.monitor.provider.impl.BatteryLevelProvider
import com.cyrillrx.monitor.provider.impl.CpuLoadProvider
import com.cyrillrx.monitor.provider.impl.RamUsageProvider
import com.cyrillrx.monitor.utils.UserPref

/**
 * @author Cyril Leroux
 *         Created on 08/07/2019.
 */
object DataManager {

    private val TAG = DataManager::class.java.simpleName

    private var batteryLevelDetector: ThresholdDetector? = null
    private var ramUsageDetector: ThresholdDetector? = null
    private var cpuLoadDetector: ThresholdDetector? = null

    private var batteryLevelProvider: BatteryLevelProvider? = null
    private var ramUsageProvider: RamUsageProvider? = null
    private var cpuLoadProvider: CpuLoadProvider? = null

    fun startMonitoring(context: Context) {
        Log.i(TAG, "startMonitoring")

        val batteryLevelDetector =
            BelowThresholdDetector(
                context,
                context.getString(R.string.stat_label_battery),
                UserPref.getBatteryThreshold(context)
            ).also { this.batteryLevelDetector = it }

        val ramUsageDetector =
            AboveThresholdDetector(
                context,
                context.getString(R.string.stat_label_memory),
                UserPref.getMemoryThreshold(context)
            ).also { this.ramUsageDetector = it }

        val cpuLoadDetector =
            AboveThresholdDetector(
                context,
                context.getString(R.string.stat_label_cpu),
                UserPref.getCpuThreshold(context)
            ).also { this.cpuLoadDetector = it }

        batteryLevelProvider = BatteryLevelProvider(context).apply { addListener(context, batteryLevelDetector) }
        ramUsageProvider = RamUsageProvider(context).apply { addListener(context, ramUsageDetector) }
        cpuLoadProvider = CpuLoadProvider(context).apply { addListener(context, cpuLoadDetector) }
    }

    fun stopMonitoring() {
        Log.i(TAG, "stopMonitoring")

        batteryLevelDetector?.let { batteryLevelProvider?.removeListener(it) }
        ramUsageDetector?.let { ramUsageProvider?.removeListener(it) }
        cpuLoadDetector?.let { cpuLoadProvider?.removeListener(it) }
    }

    fun bindUi(
        context: Context,
        batteryLevel: ValueUpdatedListener,
        ramUsage: ValueUpdatedListener,
        cpuLoad: ValueUpdatedListener) {

        batteryLevelProvider?.addListener(context, batteryLevel)
        ramUsageProvider?.addListener(context, ramUsage)
        cpuLoadProvider?.addListener(context, cpuLoad)
    }

    fun unbindUi(
        batteryLevel: ValueUpdatedListener,
        ramUsage: ValueUpdatedListener,
        cpuLoad: ValueUpdatedListener) {

        batteryLevelProvider?.removeListener(batteryLevel)
        ramUsageProvider?.removeListener(ramUsage)
        cpuLoadProvider?.removeListener(cpuLoad)
    }

    fun broadcastBatteryThreshold(context: Context, threshold: Int) {

        // Save preference
        UserPref.saveBatteryThreshold(context, threshold)

        // Update watchers
        batteryLevelDetector?.updateThreshold(threshold)
    }

    fun broadcastMemoryThreshold(context: Context, threshold: Int) {

        // Save preference
        UserPref.saveMemoryThreshold(context, threshold)

        // Update watchers
        ramUsageDetector?.updateThreshold(threshold)
    }

    fun broadcastCpuThreshold(context: Context, threshold: Int) {

        // Save preference
        UserPref.saveCpuThreshold(context, threshold)

        // Update watchers
        cpuLoadDetector?.updateThreshold(threshold)
    }
}