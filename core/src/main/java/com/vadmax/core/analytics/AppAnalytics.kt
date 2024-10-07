package com.vadmax.core.analytics

interface AppAnalytics {

    fun userEnablePhoneTimer(value: Boolean)

    fun userEnablePCTimer(value: Boolean)

    fun enableVibration(value: Boolean)

    fun enableSoundEffect(value: Boolean)

    fun enableLockScreen(value: Boolean)

    fun setRingerMode(mode: AnalyticsRingerMode)

    fun openOpenSourceLink()

    fun scanUnsupportedQR()

    fun pairPC()

    fun unpairPC()

    fun turnOffPC()
}
