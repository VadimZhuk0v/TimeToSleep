package com.vadmax.timetosleep.domain.workers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.domain.usecases.ApplyActions
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

private const val CHANNEL_ID = "APPLY_ACTIONS_SERVICE"
private const val NOTIFICATION_ID = 0x000032

class ApplyActionsWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params),
    KoinComponent {

    private val applyActions: ApplyActions by inject()

    override suspend fun doWork(): Result {
        setForeground(createForegroundInfo())
        Timber.i("✅ Apply actions worker active")
        applyActions()
        return Result.success()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Apply Actions Service",
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val manager = getSystemService(applicationContext, NotificationManager::class.java)
        manager?.createNotificationChannel(channel)

        val notification = createNotification()

        return ForegroundInfo(NOTIFICATION_ID, notification)
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(applicationContext.getString(R.string.notification_apply_actions_title))
            .setContentText(
                applicationContext.getString(R.string.notification_apply_actions_body),
            )
            .setSmallIcon(R.drawable.ic_info)
            .setForegroundServiceBehavior(Notification.FOREGROUND_SERVICE_IMMEDIATE)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()
    }
}
