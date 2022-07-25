package com.vadmax.timetosleep.domain.usercases

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import com.vadmax.timetosleep.utils.AdminReceiver

fun interface IsAdminActive {
    operator fun invoke(): Boolean
}

class IsAdminActiveImpl internal constructor(
    private val context: Context,
) : IsAdminActive {

    override fun invoke(): Boolean {
        val dm = context.getSystemService(DevicePolicyManager::class.java)
        return dm.isAdminActive(ComponentName(context, AdminReceiver::class.java))
    }
}
