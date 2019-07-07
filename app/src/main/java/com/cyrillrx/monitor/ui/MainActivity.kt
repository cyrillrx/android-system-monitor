package com.cyrillrx.monitor.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cyrillrx.monitor.R
import com.cyrillrx.monitor.notification.NotificationUtils
import com.cyrillrx.monitor.provider.impl.BatteryLevelProvider
import com.cyrillrx.monitor.provider.impl.GlobalRamUsageProvider
import com.cyrillrx.monitor.provider.impl.SystemCpuLoadProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Notification test
        NotificationUtils.notifyAlert(this, "Alert", "Toto")

        val batteryLevel = BatteryLevelProvider(this).fetchData()
        val globalRamUsage = GlobalRamUsageProvider(this).fetchData()
        val systemCpuLoad = SystemCpuLoadProvider().fetchData()

        val message = "Battery level: $batteryLevel%\n" +
                "Global memory usage: $globalRamUsage%\n" +
                "System Cpu Load: $systemCpuLoad%"

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
