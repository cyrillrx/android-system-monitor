package com.cyrillrx.monitor.service

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.cyrillrx.monitor.R
import com.cyrillrx.monitor.detector.NotificationListener
import com.cyrillrx.monitor.detector.ThresholdDetector
import com.cyrillrx.monitor.detector.impl.AboveThresholdDetector
import com.cyrillrx.monitor.detector.impl.BelowThresholdDetector
import com.cyrillrx.monitor.provider.impl.BatteryLevelProvider
import com.cyrillrx.monitor.provider.impl.CpuLoadProvider
import com.cyrillrx.monitor.provider.impl.RamUsageProvider
import com.cyrillrx.monitor.ui.UiUpdater
import com.cyrillrx.monitor.utils.NotificationUtils
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

        val batteryLevelDetector = BelowThresholdDetector(UserPref.getBatteryThreshold(context))
            .apply {
                val notificationListener = NotificationListener(
                    context,
                    NotificationUtils.NOTIFICATION_ID_BATTERY_LEVEL,
                    context.getString(R.string.stat_label_battery))
                addListener(notificationListener)
            }
            .also { this.batteryLevelDetector = it }

        val ramUsageDetector = AboveThresholdDetector(UserPref.getMemoryThreshold(context))
            .apply {
                val notificationListener = NotificationListener(
                    context,
                    NotificationUtils.NOTIFICATION_ID_RAM_USAGE,
                    context.getString(R.string.stat_label_ram_usage))
                addListener(notificationListener)
            }
            .also { this.ramUsageDetector = it }

        val cpuLoadDetector = AboveThresholdDetector(UserPref.getCpuThreshold(context))
            .apply {
                val notificationListener = NotificationListener(
                    context,
                    NotificationUtils.NOTIFICATION_ID_CPU_LOAD,
                    context.getString(R.string.stat_label_cpu_load))
                addListener(notificationListener)
            }
            .also { this.cpuLoadDetector = it }

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
        batteryLevel: UiUpdater,
        ramUsage: UiUpdater,
        cpuLoad: UiUpdater,
        tvHistory: TextView) {

        // Update the last known value of every stat
        batteryLevel.onValueUpdated(batteryLevelProvider?.getLastKnownValue())
        ramUsage.onValueUpdated(ramUsageProvider?.getLastKnownValue())
        cpuLoad.onValueUpdated(cpuLoadProvider?.getLastKnownValue())

        // Listen for value updates
        batteryLevelProvider?.addListener(context, batteryLevel)
        ramUsageProvider?.addListener(context, ramUsage)
        cpuLoadProvider?.addListener(context, cpuLoad)

        // Update the last state
        batteryLevel.setThresholdExceeded(batteryLevelDetector?.isThresholdExceeded())
        ramUsage.setThresholdExceeded(ramUsageDetector?.isThresholdExceeded())
        cpuLoad.setThresholdExceeded(cpuLoadDetector?.isThresholdExceeded())

        // Listen for alerts
        batteryLevelDetector?.addListener(batteryLevel)
        ramUsageDetector?.addListener(ramUsage)
        cpuLoadDetector?.addListener(cpuLoad)

        // Bind history
        tvHistory.text = AlertHistory.getHistory()
        AlertHistory.onHistoryUpdated = { tvHistory.text = it }
    }

    fun unbindUi(
        batteryLevel: UiUpdater,
        ramUsage: UiUpdater,
        cpuLoad: UiUpdater) {

        batteryLevelProvider?.removeListener(batteryLevel)
        ramUsageProvider?.removeListener(ramUsage)
        cpuLoadProvider?.removeListener(cpuLoad)

        batteryLevelDetector?.removeListener(batteryLevel)
        ramUsageDetector?.removeListener(ramUsage)
        cpuLoadDetector?.removeListener(cpuLoad)

        AlertHistory.onHistoryUpdated = null
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