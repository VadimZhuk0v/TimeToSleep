package com.vadmax.timetosleep.domain.usercases

import android.content.Context
import android.net.wifi.WifiManager

fun interface DisableWifi {
    operator fun invoke()
}

class DisableWifiImpl internal constructor(
    private val context: Context,
) : DisableWifi {

    override fun invoke() {
        context.getSystemService(WifiManager::class.java).isWifiEnabled = false
    }
}
