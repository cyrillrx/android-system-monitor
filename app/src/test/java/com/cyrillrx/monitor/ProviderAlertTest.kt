package com.cyrillrx.monitor

import com.cyrillrx.monitor.provider.TestProvider
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ProviderAlertTest {

    @Test
    fun triggerAlert_changingValue() {

        val provider = TestProvider(100L)
        provider.updateThreshold(0.5f)

        Assert.assertEquals(provider.alertTriggeredCount, 0)
        Assert.assertEquals(provider.alertCanceledCount, 0)

        // Trigger alert
        provider.updateValue(70L)

        Assert.assertEquals(provider.alertTriggeredCount, 1)
        Assert.assertEquals(provider.alertCanceledCount, 0)
    }

    @Test
    fun triggerAlert_changingThreshold() {

        val provider = TestProvider(100L)
        provider.updateValue(70L)

        Assert.assertEquals(provider.alertTriggeredCount, 0)
        Assert.assertEquals(provider.alertCanceledCount, 0)

        // Trigger alert
        provider.updateThreshold(0.5f)

        Assert.assertEquals(provider.alertTriggeredCount, 1)
        Assert.assertEquals(provider.alertCanceledCount, 0)
    }

    @Test
    fun cancelAlert_changingValue() {

        val provider = TestProvider(100L)
        provider.updateThreshold(0.5f)

        Assert.assertEquals(provider.alertTriggeredCount, 0)
        Assert.assertEquals(provider.alertCanceledCount, 0)

        // Trigger alert
        provider.updateValue(70L)
        // Cancel alert
        provider.updateValue(40L)

        Assert.assertEquals(provider.alertTriggeredCount, 1)
        Assert.assertEquals(provider.alertCanceledCount, 1)
    }

    @Test
    fun cancelAlert_changingThreshold() {

        val provider = TestProvider(100L)
        provider.updateThreshold(0.5f)

        Assert.assertEquals(provider.alertTriggeredCount, 0)
        Assert.assertEquals(provider.alertCanceledCount, 0)

        // Trigger alert
        provider.updateValue(70L)
        // Cancel alert
        provider.updateThreshold(0.9f)

        Assert.assertEquals(provider.alertTriggeredCount, 1)
        Assert.assertEquals(provider.alertCanceledCount, 1)
    }

    @Test
    fun cancelAlert_disablingThreshold() {

        val provider = TestProvider(100L)
        provider.updateThreshold(0.5f)

        Assert.assertEquals(provider.alertTriggeredCount, 0)
        Assert.assertEquals(provider.alertCanceledCount, 0)

        // Trigger alert
        provider.updateValue(70L)
        // Cancel alert
        provider.disableThreshold()

        Assert.assertEquals(provider.alertTriggeredCount, 1)
        Assert.assertEquals(provider.alertCanceledCount, 1)
    }
}
