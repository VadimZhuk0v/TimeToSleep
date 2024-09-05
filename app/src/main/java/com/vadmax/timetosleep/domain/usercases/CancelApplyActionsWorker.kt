package com.vadmax.timetosleep.domain.usercases

import android.content.Context
import androidx.work.WorkManager
import org.koin.core.annotation.Factory
import timber.log.Timber

fun interface CancelApplyActionsWorker {

    operator fun invoke()
}

@Factory(binds = [CancelApplyActionsWorker::class])
internal class CancelApplyActionsWorkerImpl(private val context: Context) :
    CancelApplyActionsWorker {

    override operator fun invoke() {
        WorkManager.getInstance(context).cancelUniqueWork("ApplyActions")
        Timber.d("⚠️ ApplyActions worker is canceled")
    }
}
