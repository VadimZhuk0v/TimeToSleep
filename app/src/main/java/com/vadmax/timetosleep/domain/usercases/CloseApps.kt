package com.vadmax.timetosleep.domain.usercases

import android.app.ActivityManager
import android.content.Context
import com.vadmax.io.domain.usercases.GetSelectedApps
import kotlinx.coroutines.flow.first
import timber.log.Timber

class CloseApps(
    private val context: Context,
    private val getSelectedApps: GetSelectedApps,
) {

    suspend operator fun invoke() {
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
