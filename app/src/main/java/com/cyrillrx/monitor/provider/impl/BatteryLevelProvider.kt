package com.cyrillrx.monitor.provider.impl

import android.content.Context
import android.os.BatteryManager
import com.cyrillrx.monitor.provider.StatProvider


/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class BatteryLevelProvider(private val context: Context) : StatProvider() {

    override fun fetchData(): Int {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }
}