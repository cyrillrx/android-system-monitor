package com.cyrillrx.monitor.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.cyrillrx.monitor.R
import com.cyrillrx.monitor.ui.MainActivity

/**
 * @author Cyril Leroux
 *          Created on 06/07/2019.
 */
object NotificationUtils {

    private const val CHANNEL_ID_MONITORING_ALERT = "1.monitoring.alert"

    const val NOTIFICATION_ID_BATTERY_LEVEL = 6000
    const val NOTIFICATION_ID_RAM_USAGE = 6001
    const val NOTIFICATION_ID_CPU_LOAD = 6002

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

    fun notifyAlert(context: Context, notificationId: Int, title: String, content: String) {

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID_MONITORING_ALERT)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(content)
        notificationBuilder.setSmallIcon(R.drawable.ic_baseline_warning_24)

        val intent = Intent(context, MainActivity::class.java)
            .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        notificationBuilder.setContentIntent(pendingIntent)
        notificationBuilder.setAutoCancel(true)

        val notification = notificationBuilder.build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    fun cancelNotification(context: Context, notificationId: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }
}