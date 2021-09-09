package com.vadmax.timetosleep.utils.extentions

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.vadmax.io.data.AppInfo

fun AppInfo.systemInfo(context: Context) =
    context.packageManager.getApplicationInfo(packageName, 0)

fun ApplicationInfo.getApplicationLabel(pm: PackageManager) =
    pm.getApplicationLabel(this).toString()
