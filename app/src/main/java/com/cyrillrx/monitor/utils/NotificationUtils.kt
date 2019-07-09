package com.cyrillrx.monitor.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.cyrillrx.monitor.R

/**
 * @author Cyril Leroux
 *          Created on 06/07/2019.
 */
object NotificationUtils {

    private const val CHANNEL_ID_MONITORING_ALERT = "1.monitoring.alert"
    private const val NOTIFICATION_ID_ALERT = 6000

    fun createChannels(context: Context) {

        // Channels are only available from API 26 (O)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        // Create the alert channel
        val alertChannel = NotificationChannel(
            CHANNEL_ID_MONITORING_ALERT,
            context.getString(R.string.notification_channel_alerts_title),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        // Customize the channel
        alertChannel.description = context.getString(R.string.notification_channel_alerts_desc)
        alertChannel.enableLights(true)
        alertChannel.lightColor = Color.RED
        alertChannel.enableVibration(true)
        alertChannel.setShowBadge(true)

        // Register the channel
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(alertChannel)
    }

    fun notifyAlert(context: Context, title: String, content: String) {

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID_MONITORING_ALERT)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(content)
        notificationBuilder.setSmallIcon(R.drawable.ic_baseline_warning_24)
        notificationBuilder.setAutoCancel(true)

        val notification = notificationBuilder.build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID_ALERT, notification)
    }
}