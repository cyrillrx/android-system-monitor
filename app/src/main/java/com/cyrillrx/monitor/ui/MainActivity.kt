package com.cyrillrx.monitor.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.cyrillrx.monitor.R
import com.cyrillrx.monitor.provider.ValueUpdatedListener
import com.cyrillrx.monitor.service.DataManager
import com.cyrillrx.monitor.service.MonitoringService
import com.cyrillrx.monitor.utils.UserPref
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_stat_battery.*
import kotlinx.android.synthetic.main.layout_stat_cpu.*
import kotlinx.android.synthetic.main.layout_stat_memory.*

class MainActivity : AppCompatActivity() {

    private val batteryLevel = object : ValueUpdatedListener {
        override fun onValueUpdated(newValue: Int) {
            tvBatteryValue?.text = "$newValue%"
        }
    }

    private val ramUsage = object : ValueUpdatedListener {
        override fun onValueUpdated(newValue: Int) {
            tvMemoryValue?.text = "$newValue%"
        }
    }

    private val cpuLoad = object : ValueUpdatedListener {
        override fun onValueUpdated(newValue: Int) {
            tvCpuValue?.text = "$newValue%"
        }
    }

    private val serviceConnector = object : ServiceConnection {

        var service: MonitoringService? = null

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            Log.v(TAG, "onServiceConnected")

            service = (binder as MonitoringService.Binder).getService()
            DataManager.bindUi(this@MainActivity, batteryLevel, ramUsage, cpuLoad)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.v(TAG, "onServiceDisconnected")
        }

        fun bindService() {
            Log.v(TAG, "bindService")

            val intent = Intent(this@MainActivity, MonitoringService::class.java)
            bindService(intent, this, Context.BIND_AUTO_CREATE)
        }

        fun unbindService() {
            Log.v(TAG, "unbindService")

            DataManager.unbindUi(batteryLevel, ramUsage, cpuLoad)
            unbindService(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()

        btnGo.setOnClickListener { startMonitoringService() }

        serviceConnector.bindService()
    }

    override fun onDestroy() {
        serviceConnector.unbindService()
        super.onDestroy()
    }

    private fun setupViews() {

        sbBattery.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvBatteryThreshold.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val threshold: Int = seekBar?.progress ?: 0
                DataManager.broadcastBatteryThreshold(this@MainActivity, threshold)

            }
        })
        sbBattery.progress = UserPref.getBatteryThreshold(this@MainActivity)

        sbMemory.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvMemoryThreshold.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val threshold: Int = seekBar?.progress ?: 0
                DataManager.broadcastMemoryThreshold(this@MainActivity, threshold)

            }
        })
        sbMemory.progress = UserPref.getMemoryThreshold(this@MainActivity)

        sbCpu.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvCpuThreshold.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val threshold: Int = seekBar?.progress ?: 0
                DataManager.broadcastCpuThreshold(this@MainActivity, threshold)

            }
        })
        sbCpu.progress = UserPref.getCpuThreshold(this@MainActivity)
    }

    private fun startMonitoringService() {

    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
