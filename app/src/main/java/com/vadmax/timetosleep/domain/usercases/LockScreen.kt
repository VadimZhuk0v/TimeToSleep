package com.vadmax.timetosleep.domain.usercases

import android.app.admin.DevicePolicyManager
import android.content.Context
import com.vadmax.timetosleep.utils.extentions.isAdminActive

fun interface LockScreen {
    operator fun invoke()
}

class LockScreenImpl internal constructor(
    private val context: Context,
) : LockScreen {

    override fun invoke() {
        if (context.isAdminActive.not()) {
            return
        }
        context.getSystemService(DevicePolicyManager::class.java).lockNow()
    }
}
