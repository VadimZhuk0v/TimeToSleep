package com.vadmax.timetosleep.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.domain.usecases.ApplyActions
import com.vadmax.timetosleep.domain.usercases.local.GetSelectedTime
import com.vadmax.timetosleep.domain.usercases.local.SetTimerEnable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val CHANNEL_ID = "APPLY_ACTIONS_SERVICE"
private const val NOTIFICATION_ID = 0x000032

class ApplyActionsService : Service() {

    private val applyActions: ApplyActions by inject()
    private val getSelectedTime: GetSelectedTime by inject()
    private val setTimerActive: SetTimerEnable by inject()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private var job: Job? = null

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Apply Actions Service",
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
        val notification = createNotification("")

        startForeground(
            NOTIFICATION_ID,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROCESSING,
        )
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            COMMAND_NEW_TIME -> {
                serviceLogic()
            }

            COMMAND_CANCEL -> {
                coroutineScope.launch {
                    setTimerActive(false)
                    stopSelf()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun serviceLogic() {
        job?.cancel()
        job = coroutineScope.launch {
            val currentTimeInMillis = System.currentTimeMillis()
            val selectedTime = getSelectedTime() ?: 0
            val delayInMillis = selectedTime - currentTimeInMillis
            updateNotification(selectedTime)
            delay(delayInMillis)
            applyActions()
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun stopService(name: Intent?): Boolean {
        Timber.d("Actions service stopped")

        return super.stopService(name)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

    private fun calculateTime(selectedTime: Long): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return Instant.ofEpochMilli(selectedTime)
            .atZone(ZoneId.systemDefault())
            .toLocalTime()
            .format(formatter)
    }

    private fun updateNotification(selectedTime: Long) {
        val time = calculateTime(selectedTime)
        Timber.i("⏲️ Apply actions scheduled: $time")
        val updatedNotification = createNotification(time)
        val nm = getSystemService(NotificationManager::class.java)
        nm?.notify(NOTIFICATION_ID, updatedNotification)
    }

    private fun createNotification(time: String): Notification {
        val stopIntent = Intent(this, ApplyActionsService::class.java).apply {
            action = COMMAND_CANCEL
        }
        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_apply_actions_title))
            .setContentText(getString(R.string.notification_apply_actions_body, time))
            .setSmallIcon(R.drawable.ic_info)
            .setForegroundServiceBehavior(Notification.FOREGROUND_SERVICE_IMMEDIATE)
            .setOngoing(true)
            .setAutoCancel(false)
            .addAction(
                0,
                getString(R.string.notification_apply_actions_cancel),
                stopPendingIntent,
            )
            .build()
    }

    companion object {
        const val COMMAND_NEW_TIME = "NEW_TIME"
        const val COMMAND_CANCEL = "CANCEL"
    }
}
