package com.cyrillrx.monitor.service

import android.content.Context
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

    var batteryLevelDetector: ThresholdDetector? = null
    var ramUsageDetector: ThresholdDetector? = null
    var cpuLoadDetector: ThresholdDetector? = null

    var batteryLevelProvider: BatteryLevelProvider? = null
    var ramUsageProvider: RamUsageProvider? = null
    var cpuLoadProvider: CpuLoadProvider? = null

    fun startMonitoring(context: Context) {

        val batteryLevelDetector =
            BelowThresholdDetector(
                context,
                context.getString(R.string.stat_label_battery),
                UserPref.getBatteryThreshold(context)
            ).also { this.batteryLevelDetector = it }
        batteryLevelProvider = BatteryLevelProvider(context).apply { addListener(batteryLevelDetector) }

        val ramUsageDetector =
            AboveThresholdDetector(
                context,
                context.getString(R.string.stat_label_memory),
                UserPref.getMemoryThreshold(context)
            ).also { this.ramUsageDetector = it }
        ramUsageProvider = RamUsageProvider(context).apply { addListener(ramUsageDetector) }

        val cpuLoadDetector =
            AboveThresholdDetector(
                context,
                context.getString(R.string.stat_label_cpu),
                UserPref.getCpuThreshold(context)
            ).also { this.cpuLoadDetector = it }
        cpuLoadProvider = CpuLoadProvider().apply { addListener(cpuLoadDetector) }
    }

    fun stopMonitoring() {
        batteryLevelDetector?.let { batteryLevelProvider?.removeListener(it) }
        ramUsageDetector?.let { ramUsageProvider?.removeListener(it) }
        cpuLoadDetector?.let { cpuLoadProvider?.removeListener(it) }
    }

    fun bindUi(batteryLevel: ValueUpdatedListener, ramUsage: ValueUpdatedListener, cpuLoad: ValueUpdatedListener) {
        batteryLevelProvider?.addListener(batteryLevel)
        ramUsageProvider?.addListener(ramUsage)
        cpuLoadProvider?.addListener(cpuLoad)
    }

    fun unbindUi(batteryLevel: ValueUpdatedListener, ramUsage: ValueUpdatedListener, cpuLoad: ValueUpdatedListener) {
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

    fun broadcastBatteryValue() {

    }

    fun broadcastMemoryThreshold(context: Context, threshold: Int) {

        // Save preference
        UserPref.saveMemoryThreshold(context, threshold)

        // Update watchers
        ramUsageDetector?.updateThreshold(threshold)
    }

    fun broadcastMemoryValue() {

    }

    fun broadcastCpuThreshold(context: Context, threshold: Int) {

        // Save preference
        UserPref.saveCpuThreshold(context, threshold)

        // Update watchers
        cpuLoadDetector?.updateThreshold(threshold)
    }

    fun broadcastCpuValue() {

    }
}