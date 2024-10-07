package com.vadmax.timetosleep.analytics

import android.util.StatsLog.logEvent
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ParametersBuilder
import com.google.firebase.analytics.logEvent
import com.vadmax.core.analytics.AnalyticsRingerMode
import com.vadmax.core.analytics.AppAnalytics
import org.koin.core.annotation.Single
import timber.log.Timber

@Single(binds = [AppAnalytics::class])
class AppAnalyticsFirebaseImpl(private val analytics: FirebaseAnalytics) : AppAnalytics {

    override fun userEnablePhoneTimer(value: Boolean) {
        logEvent(AnalyticsEvents.USER_ENABLE_PHONE_TIMER) {
            param("enable", value.toString())
        }
    }

    override fun userEnablePCTimer(value: Boolean) {
        logEvent(AnalyticsEvents.USER_ENABLE_PC_TIMER) {
            param("enable", value.toString())
        }
    }

    override fun enableVibration(value: Boolean) {
        logEvent(AnalyticsEvents.ENABLE_VIBRATION) {
            param("enable", value.toString())
        }
    }

    override fun enableSoundEffect(value: Boolean) {
        logEvent(AnalyticsEvents.ENABLE_SOUND_EFFECT) {
            param("enable", value.toString())
        }
    }

    override fun enableLockScreen(value: Boolean) {
        logEvent(AnalyticsEvents.ENABLE_LOCK_SCREEN) {
            param("enable", value.toString())
        }
    }

    override fun setRingerMode(mode: AnalyticsRingerMode) {
        logEvent(AnalyticsEvents.SET_RINGER_MODE) {
            param("mode", mode.name)
        }
    }

    override fun openOpenSourceLink() {
        logEvent(AnalyticsEvents.OPEN_OPEN_SOURCE_LINK)
    }

    override fun pairPC() {
        logEvent(AnalyticsEvents.PAIR_PC)
    }

    override fun unpairPC() {
        logEvent(AnalyticsEvents.UNPAIR_PC)
    }

    override fun turnOffPC() {
        logEvent(AnalyticsEvents.TURN_OFF_PC)
    }

    override fun scanUnsupportedQR() {
        logEvent(AnalyticsEvents.SCAN_UNSUPPORTED_QR)
    }

    override fun turnOffConfirm() {
        logEvent(AnalyticsEvents.TURN_OFF_PC_CONFIRM)
    }

    override fun settingsPhone() {
        logEvent(AnalyticsEvents.SETTINGS_PHONE)
    }

    override fun settingsPC() {
        logEvent(AnalyticsEvents.SETTINGS_PC)
    }

    override fun infoPC() {
        logEvent(AnalyticsEvents.INFO_PC)
    }

    override fun downloadPCApp() {
        logEvent(AnalyticsEvents.DOWNLOAD_PC_APP)
    }

    private fun logEvent(
        name: String,
        params: ParametersBuilder.() -> Unit = {},
    ) {
        Timber.i("📊 Log analytics event: $name with params: $params")
        analytics.logEvent(name) {
            params()
        }
    }
}
