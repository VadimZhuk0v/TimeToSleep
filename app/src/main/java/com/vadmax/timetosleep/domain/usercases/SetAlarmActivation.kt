package com.vadmax.timetosleep.domain.usercases

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.vadmax.io.domain.usercases.GetSelectedTime
import com.vadmax.io.domain.usercases.IsTimerEnable
import com.vadmax.timetosleep.broadcastreceivers.StopMusicReceiver
import kotlinx.coroutines.flow.first
import java.util.Date

class SetAlarmActivation(
    private val context: Context,
    private val getSelectedTime: GetSelectedTime,
    private val isTimerEnable: IsTimerEnable,
) {
    suspend operator fun invoke() {
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val alarmIntent = Intent(context, StopMusicReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_ONE_SHOT)
        if (isTimerEnable().first()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC,
                getSelectedTime() ?: Date().time,
                pendingIntent,
            )
        } else {
            alarmManager.cancel(pendingIntent)
        }
    }
}
