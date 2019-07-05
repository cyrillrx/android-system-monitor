package com.cyrillrx.monitor.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cyrillrx.monitor.R
import com.cyrillrx.monitor.notification.NotificationUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Notification test
        NotificationUtils.notifyAlert(this, "Alert", "Toto")
    }
}
