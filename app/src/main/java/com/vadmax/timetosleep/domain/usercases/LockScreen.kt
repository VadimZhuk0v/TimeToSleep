package com.vadmax.timetosleep.domain.usercases

import android.app.admin.DevicePolicyManager
import android.content.Context
import com.vadmax.timetosleep.utils.extentions.isAdminActive

class LockScreen(private val context: Context) {

    operator fun invoke() {
        if (context.isAdminActive.not()) {
            return
        }
        context.getSystemService(DevicePolicyManager::class.java).lockNow()
    }
}
