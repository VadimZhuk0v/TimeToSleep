package com.vadmax.timetosleep.domain.usercases

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.vadmax.io.data.AppInfo
import com.vadmax.timetosleep.utils.extentions.getApplicationLabel
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface GetAppsList {
    suspend operator fun invoke(): List<AppInfo>
}

class GetAppsListImpl internal constructor(
    private val context: Context,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : GetAppsList {

    @Suppress("RedundantSuspendModifier", "QueryPermissionsNeeded")
    override suspend fun invoke(): List<AppInfo> = withContext(dispatcher) {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_APP_MUSIC)
        return@withContext context.packageManager
            .getInstalledApplications(PackageManager.GET_META_DATA)
            .filter {
                context.packageManager
                    .getLaunchIntentForPackage(it.packageName) != null && it.name.isNullOrEmpty()
                    .not()
            }.map {
                AppInfo(it.packageName, it.getApplicationLabel(context.packageManager))
            }
    }
}
