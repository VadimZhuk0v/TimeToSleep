package com.vadmax.timetosleep.domain.usecases

import android.content.Context
import android.net.wifi.WifiManager
import org.koin.core.annotation.Factory

fun interface DisableWifi {
    operator fun invoke()
}

@Factory(binds = [DisableWifi::class])
class DisableWifiImpl(private val context: Context) : DisableWifi {

    override fun invoke() {
        context.getSystemService(WifiManager::class.java).isWifiEnabled = false
    }
}
