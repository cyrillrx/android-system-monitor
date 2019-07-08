package com.cyrillrx.monitor.provider.impl

import android.content.Context
import android.os.BatteryManager
import com.cyrillrx.monitor.provider.StatProvider
import com.cyrillrx.monitor.provider.ValueUpdatedListener


/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class BatteryLevelProvider(private val context: Context, listener: ValueUpdatedListener) : StatProvider(listener) {

    override fun fetchData(): Int {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

        // We have to use the consumed level for the threshold to behave like other providers
        val consumedBatteryLevel = 100 - batteryLevel

        listener.onValueUpdated(consumedBatteryLevel)

        return consumedBatteryLevel
    }
}