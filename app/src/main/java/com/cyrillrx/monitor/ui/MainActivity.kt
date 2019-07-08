package com.cyrillrx.monitor.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cyrillrx.monitor.R
import com.cyrillrx.monitor.notification.NotificationUtils
import com.cyrillrx.monitor.provider.AlertListener
import com.cyrillrx.monitor.provider.StatWatcher
import com.cyrillrx.monitor.provider.impl.BatteryLevelProvider
import com.cyrillrx.monitor.provider.impl.GlobalRamUsageProvider
import com.cyrillrx.monitor.provider.impl.SystemCpuLoadProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGo.setOnClickListener { startMonitoring() }
    }

    private fun startMonitoring() {

        val batteryLevelWatcher = StatWatcher(NotificationListener(this, "Battery level"))
        batteryLevelWatcher.updateThreshold(50)

        val globalRamUsageWatcher = StatWatcher(NotificationListener(this, "Global memory usage"))
        globalRamUsageWatcher.updateThreshold(50)

        val systemCpuLoadWatcher = StatWatcher(NotificationListener(this, "System Cpu Load"))
        systemCpuLoadWatcher.updateThreshold(50)

        val batteryLevelProvider = BatteryLevelProvider(this, batteryLevelWatcher)
        val globalRamUsageProvider = GlobalRamUsageProvider(this, globalRamUsageWatcher)
        val systemCpuLoadProvider = SystemCpuLoadProvider(systemCpuLoadWatcher)

        batteryLevelProvider.fetchData()
        globalRamUsageProvider.fetchData()
        systemCpuLoadProvider.fetchData()
    }

//    private fun fetchData(): String {
//
//        val batteryLevel = BatteryLevelProvider(this).fetchData()
//        val globalRamUsage = GlobalRamUsageProvider(this).fetchData()
//        val systemCpuLoad = SystemCpuLoadProvider().fetchData()
//
//        return "Battery level: $batteryLevel%\n" +
//                "Global memory usage: $globalRamUsage%\n" +
//                "System Cpu Load: $systemCpuLoad%"
//
//    }

    private inner class NotificationListener(private val context: Context, private val statName: String) :
        AlertListener {
        override fun onAlertTriggered(percentage: Int?) {
            NotificationUtils.notifyAlert(context, statName, "$statName alert triggered: $percentage%")
        }

        override fun onAlertCanceled(percentage: Int?) {
            NotificationUtils.notifyAlert(context, statName, "$statName alert canceled: $percentage%")
        }

    }

}
