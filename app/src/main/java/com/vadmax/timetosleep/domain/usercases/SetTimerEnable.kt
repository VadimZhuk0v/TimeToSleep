package com.vadmax.timetosleep.domain.usercases

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.vadmax.io.domain.usercases.SetTimerEnable
import com.vadmax.timetosleep.broadcastreceivers.StopMusicReceiver
import java.util.Date

class SetTimerEnable(
    private val context: Context,
    private val setTimerEnable: SetTimerEnable,
) {

    suspend operator fun invoke(value: Boolean, time: Date) {
        setTimerEnable(value)
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val alarmIntent = Intent(context, StopMusicReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_ONE_SHOT)
        if (value) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC,
                time.time,
                pendingIntent,
            )
        } else {
            alarmManager.cancel(pendingIntent)
        }
    }
}
