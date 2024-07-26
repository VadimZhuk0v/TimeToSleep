package com.vadmax.timetosleep.domain.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vadmax.timetosleep.domain.usercases.ApplyActions
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class ApplyActionsWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params),
    KoinComponent {

    private val applyActions: ApplyActions by inject()

    override suspend fun doWork(): Result {
        Timber.i("✅ Apply actions worker active")
        applyActions()
        return Result.success()
    }
}
