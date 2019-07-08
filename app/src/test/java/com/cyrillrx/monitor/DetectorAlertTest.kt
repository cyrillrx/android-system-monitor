package com.cyrillrx.monitor

import com.cyrillrx.monitor.provider.ThresholdDetectorCountingAlerts
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DetectorAlertTest {

    @Test
    fun triggerAlert_changingValue() {

        val provider = ThresholdDetectorCountingAlerts()
        provider.updateThreshold(50)

        Assert.assertEquals(provider.alertTriggeredCount, 0)
        Assert.assertEquals(provider.alertCanceledCount, 0)

        // Trigger alert
        provider.onValueUpdated(70)

        Assert.assertEquals(provider.alertTriggeredCount, 1)
        Assert.assertEquals(provider.alertCanceledCount, 0)
    }

    @Test
    fun triggerAlert_changingThreshold() {

        val provider = ThresholdDetectorCountingAlerts()
        provider.onValueUpdated(70)

        Assert.assertEquals(provider.alertTriggeredCount, 0)
        Assert.assertEquals(provider.alertCanceledCount, 0)

        // Trigger alert
        provider.updateThreshold(50)

        Assert.assertEquals(provider.alertTriggeredCount, 1)
        Assert.assertEquals(provider.alertCanceledCount, 0)
    }

    @Test
    fun triggerAlert_changingValueTwice() {

        val provider = ThresholdDetectorCountingAlerts()
        provider.updateThreshold(50)

        Assert.assertEquals(provider.alertTriggeredCount, 0)
        Assert.assertEquals(provider.alertCanceledCount, 0)

        // Trigger alert
        provider.onValueUpdated(70)
        // This should not trigger another alert
        provider.onValueUpdated(90)

        Assert.assertEquals(provider.alertTriggeredCount, 1)
        Assert.assertEquals(provider.alertCanceledCount, 0)
    }

    @Test
    fun cancelAlert_changingValue() {

        val provider = ThresholdDetectorCountingAlerts()
        provider.updateThreshold(50)

        Assert.assertEquals(provider.alertTriggeredCount, 0)
        Assert.assertEquals(provider.alertCanceledCount, 0)

        // Trigger alert
        provider.onValueUpdated(70)
        // Cancel alert
        provider.onValueUpdated(40)

        Assert.assertEquals(provider.alertTriggeredCount, 1)
        Assert.assertEquals(provider.alertCanceledCount, 1)
    }

    @Test
    fun cancelAlert_changingThreshold() {

        val provider = ThresholdDetectorCountingAlerts()
        provider.updateThreshold(50)

        Assert.assertEquals(provider.alertTriggeredCount, 0)
        Assert.assertEquals(provider.alertCanceledCount, 0)

        // Trigger alert
        provider.onValueUpdated(70)
        // Cancel alert
        provider.updateThreshold(90)

        Assert.assertEquals(provider.alertTriggeredCount, 1)
        Assert.assertEquals(provider.alertCanceledCount, 1)
    }

    @Test
    fun cancelAlert_disablingThreshold() {

        val provider = ThresholdDetectorCountingAlerts()
        provider.updateThreshold(50)

        Assert.assertEquals(provider.alertTriggeredCount, 0)
        Assert.assertEquals(provider.alertCanceledCount, 0)

        // Trigger alert
        provider.onValueUpdated(70)
        // Cancel alert
        provider.disableThreshold()

        Assert.assertEquals(provider.alertTriggeredCount, 1)
        Assert.assertEquals(provider.alertCanceledCount, 1)
    }
}
