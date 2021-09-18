package com.vadmax.timetosleep.domain.usercases

import android.content.Context
import android.net.wifi.WifiManager

class DisableWifi(private val context: Context) {

    operator fun invoke() {
        context.getSystemService(WifiManager::class.java).isWifiEnabled = false
    }
}
