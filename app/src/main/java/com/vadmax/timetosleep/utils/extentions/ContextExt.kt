package com.vadmax.timetosleep.utils.extentions

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

fun Context.vibrate() {
    val vibrator = getSystemService(Vibrator::class.java)
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            vibrator.vibrate(
                VibrationEffect.createPredefined(
                    VibrationEffect.EFFECT_TICK
                )
            )
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(10, 10, 10), -1))
        }
    }
}
