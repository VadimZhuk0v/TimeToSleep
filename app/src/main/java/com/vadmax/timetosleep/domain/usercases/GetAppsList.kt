package com.vadmax.timetosleep.domain.usercases

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.vadmax.io.data.AppInfo
import com.vadmax.timetosleep.utils.extentions.getApplicationLabel

class GetAppsList(private val context: Context) {

    @Suppress("RedundantSuspendModifier", "QueryPermissionsNeeded")
    suspend operator fun invoke(): List<AppInfo> {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_APP_MUSIC)
        return context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter {
                context.packageManager
                    .getLaunchIntentForPackage(it.packageName) != null && it.name.isNullOrEmpty()
                    .not()
            }.map {
                AppInfo(it.packageName, it.getApplicationLabel(context.packageManager))
            }
    }
}
