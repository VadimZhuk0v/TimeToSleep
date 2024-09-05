package com.vadmax.timetosleep.domain.usecases

import android.app.admin.DevicePolicyManager
import android.content.Context
import com.vadmax.timetosleep.utils.extentions.isAdminActive
import org.koin.core.annotation.Factory

fun interface LockScreen {
    operator fun invoke()
}

@Factory(binds = [LockScreen::class])
class LockScreenImpl(private val context: Context) : LockScreen {

    override fun invoke() {
        if (context.isAdminActive.not()) {
            return
        }
        context.getSystemService(DevicePolicyManager::class.java).lockNow()
    }
}
