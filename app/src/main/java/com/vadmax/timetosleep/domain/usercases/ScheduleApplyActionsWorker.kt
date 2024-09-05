package com.vadmax.timetosleep.domain.usercases

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.vadmax.timetosleep.domain.workers.ApplyActionsWorker
import org.koin.core.annotation.Factory
import timber.log.Timber
import java.util.Date
import java.util.concurrent.TimeUnit

fun interface ScheduleApplyActionsWorker {

    suspend operator fun invoke()
}

@Factory(binds = [ScheduleApplyActionsWorker::class])
internal class ScheduleApplyActionsWorkerImpl(
    private val context: Context,
    private val getSelectedTime: GetSelectedTime,
) : ScheduleApplyActionsWorker {
    override suspend fun invoke() {
        val currentTimeInMillis = System.currentTimeMillis()
        val selectedTime = getSelectedTime() ?: 0
        val delayInMillis = selectedTime - currentTimeInMillis
        val uploadWorkRequest = OneTimeWorkRequestBuilder<ApplyActionsWorker>()
            .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
            .build()
        WorkManager
            .getInstance(context)
            .enqueueUniqueWork("ApplyActions", ExistingWorkPolicy.REPLACE, uploadWorkRequest)

        Timber.i("ℹ️ ApplyActions worker is scheduled for ${Date(selectedTime)}")
    }
}
