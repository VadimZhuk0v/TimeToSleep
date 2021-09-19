package com.vadmax.timetosleep.domain.usercases

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import com.vadmax.timetosleep.utils.AdminReceiver

class IsAdminActive(private val context: Context) {

    operator fun invoke(): Boolean {
        val dm = context.getSystemService(DevicePolicyManager::class.java)
        return dm.isAdminActive(ComponentName(context, AdminReceiver::class.java))
    }
}