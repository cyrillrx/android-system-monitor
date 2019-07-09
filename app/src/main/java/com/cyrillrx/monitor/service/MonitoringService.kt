package com.cyrillrx.monitor.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * @author Cyril Leroux
 *          Created on 08/07/2019.
 */
class MonitoringService : Service() {

    private val binder = Binder()

    override fun onBind(intent: Intent?): IBinder? = binder

    override fun onCreate() {
        super.onCreate()
        DataManager.startMonitoring(this)
    }

    override fun onDestroy() {
        DataManager.stopMonitoring()
        super.onDestroy()
    }

    inner class Binder : android.os.Binder() {
        fun getService(): MonitoringService {
            return this@MonitoringService
        }
    }
}