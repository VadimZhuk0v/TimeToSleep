package com.vadmax.timetosleep.utils.extentions

import android.app.NotificationManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
import com.vadmax.timetosleep.utils.AdminReceiver

@SuppressWarnings("MagicNumber")
fun Context.vibrate() {
    val vibrator = getSystemService(Vibrator::class.java)
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            vibrator.vibrate(
                VibrationEffect.createPredefined(
                    VibrationEffect.EFFECT_TICK,
                ),
            )
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(10, 10, 10), -1))
        }
    }
}

fun Context.navigateToLockScreenAdminPermission() {
    val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
        putExtra(
            DevicePolicyManager.EXTRA_DEVICE_ADMIN,
            ComponentName(this@navigateToLockScreenAdminPermission, AdminReceiver::class.java),
        )
    }
    startActivity(intent)
}

fun Context.navigateToNotificationAccessSettings() {
    val intent = Intent(
        Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS,
    )
    startActivity(intent)
}

val Context.isNotificationAccessGranted: Boolean
    get() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        return notificationManager.isNotificationPolicyAccessGranted
    }

val Context.isAdminActive: Boolean
    get() {
        return getSystemService(DevicePolicyManager::class.java).isAdminActive(
            ComponentName(
                this,
                AdminReceiver::class.java,
            ),
        )
    }

fun Context.requestIgnoreBatteryOptimizations(): Boolean {
    val intent = Intent(
        ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
        Uri.parse("package:$packageName"),
    )
    val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
    if (pm.isIgnoringBatteryOptimizations(packageName).not()) {
        startActivity(intent)
        return true
    } else {
        return false
    }
}
