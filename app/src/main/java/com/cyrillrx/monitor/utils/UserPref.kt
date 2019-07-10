package com.cyrillrx.monitor.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/**
 * @author Cyril Leroux
 *         Created on 08/07/2019.
 */
object UserPref {

    private const val KEY_BATTERY_THRESHOLD = "battery_threshold"
    private const val KEY_MEMORY_THRESHOLD = "memory_threshold"
    private const val KEY_CPU_THRESHOLD = "cpu_threshold"
    private const val KEY_ALERT_RECOVERED = "alert_recovered"

    private const val DEFAULT_BATTERY_THRESHOLD = 20
    private const val DEFAULT_MEMORY_THRESHOLD = 60
    private const val DEFAULT_CPU_THRESHOLD = 60

    fun getBatteryThreshold(context: Context) = context.getPrefInt(
        KEY_BATTERY_THRESHOLD,
        DEFAULT_BATTERY_THRESHOLD
    )

    fun saveBatteryThreshold(context: Context, threshold: Int) {
        context.savePrefInt(KEY_BATTERY_THRESHOLD, threshold)
    }

    fun getMemoryThreshold(context: Context) = context.getPrefInt(
        KEY_MEMORY_THRESHOLD,
        DEFAULT_MEMORY_THRESHOLD
    )

    fun saveMemoryThreshold(context: Context, threshold: Int) {
        context.savePrefInt(KEY_MEMORY_THRESHOLD, threshold)
    }

    fun getCpuThreshold(context: Context) = context.getPrefInt(
        KEY_CPU_THRESHOLD,
        DEFAULT_CPU_THRESHOLD
    )

    fun saveCpuThreshold(context: Context, threshold: Int) {
        context.savePrefInt(KEY_CPU_THRESHOLD, threshold)
    }

    fun getAlertRecovered(context: Context): Boolean = context.getPrefBool(KEY_ALERT_RECOVERED, false)

    fun saveAlertRecovered(context: Context, value: Boolean) {
        context.savePrefBool(KEY_ALERT_RECOVERED, value)
    }

    private fun Context.getSharedPrefs(): SharedPreferences? =
        PreferenceManager.getDefaultSharedPreferences(applicationContext)

    /**
     * Saves a boolean value into the shared preferences.
     *
     * @param key The name of the preference to retrieve.
     */
    private fun Context.savePrefBool(key: String, value: Boolean) {
        getSharedPrefs()?.edit()?.putBoolean(key, value)?.apply()
    }

    /**
     * Saves an integer value into the shared preferences.
     *
     * @param key The name of the preference to retrieve.
     */
    private fun Context.savePrefInt(key: String, value: Int) {
        getSharedPrefs()?.edit()?.putInt(key, value)?.apply()
    }

    /**
     * Retrieves a boolean value from the shared preferences.
     * Clears the stored field if an error occurs.
     *
     * @param key The name of the preference to retrieve.
     * @return The preference value if it exists, or defaultValue.
     */
    private fun Context.getPrefBool(key: String, defaultValue: Boolean = false): Boolean {

        try {
            return getSharedPrefs()?.getBoolean(key, defaultValue) ?: defaultValue
        } catch (e: Exception) {
            // If a problem occurred while parsing, better clear the stored field.
            clearPrefKey(key)
        }

        return defaultValue
    }

    /**
     * Retrieves an integer value from the shared preferences.
     * Clears the stored field if an error occurs.
     *
     * @param key The name of the preference to retrieve.
     * @return The preference value if it exists, or defaultValue.
     */
    private fun Context.getPrefInt(key: String, defaultValue: Int = Int.MIN_VALUE): Int {

        try {
            return getSharedPrefs()?.getInt(key, defaultValue) ?: defaultValue
        } catch (e: Exception) {
            // If a problem occurred while parsing, better clear the stored field.
            clearPrefKey(key)
        }

        return defaultValue
    }

    private fun Context.clearPrefKey(key: String) = getSharedPrefs()?.edit()?.remove(key)?.apply()
}