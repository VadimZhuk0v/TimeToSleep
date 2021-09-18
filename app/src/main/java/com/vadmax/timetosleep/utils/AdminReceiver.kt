package com.vadmax.timetosleep.utils

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import com.vadmax.io.domain.usercases.SetLockScreenEnable
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AdminReceiver : DeviceAdminReceiver(), KoinComponent {

    private val setLockScreenEnable: SetLockScreenEnable by inject()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)

        GlobalScope.launch(Dispatchers.IO) {
            setLockScreenEnable(false)
        }
    }
}
