package com.vadmax.timetosleep.domain.usercases

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import timber.log.Timber

fun interface CloseApps {
    suspend operator fun invoke()
}

class CloseAppsImpl(
    private val context: Context,
    private val getSelectedApps: GetSelectedApps,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : CloseApps {

    @SuppressLint("MissingPermission")
    @SuppressWarnings("TooGenericExceptionCaught")
    override suspend fun invoke() = withContext(dispatcher) {
        val am = context.getSystemService(ActivityManager::class.java)
        getSelectedApps().first().forEach {
            try {
                am.killBackgroundProcesses(it.packageName)
            } catch (e: Exception) {
                Timber.e(e, "Killing process was failed")
            }
        }
    }
}
