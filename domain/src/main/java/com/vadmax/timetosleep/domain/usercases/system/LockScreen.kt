package com.vadmax.timetosleep.domain.usercases.system

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import com.vadmax.timetosleep.domain.receiver.AdminReceiver
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


private val Context.isAdminActive: Boolean
    get() {
        return getSystemService(DevicePolicyManager::class.java).isAdminActive(
            ComponentName(
                this,
                AdminReceiver::class.java,
            ),
        )
    }
