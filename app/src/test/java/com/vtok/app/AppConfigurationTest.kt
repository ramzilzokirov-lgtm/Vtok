package com.vtok.app

import org.junit.Assert.assertEquals
import org.junit.Test

class AppConfigurationTest {
    @Test
    fun applicationIdAndVersionAreStable() {
        assertEquals("com.vtok.app", BuildConfig.APPLICATION_ID)
        assertEquals("0.1", BuildConfig.VERSION_NAME)
    }
}
