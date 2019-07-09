package com.cyrillrx.monitor

import android.app.Application
import com.cyrillrx.monitor.utils.NotificationUtils

/**
 * @author Cyril Leroux
 *          Created on 05/07/2019.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        NotificationUtils.createChannels(this)
    }
}