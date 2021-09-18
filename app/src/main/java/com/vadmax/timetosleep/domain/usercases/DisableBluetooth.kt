package com.vadmax.timetosleep.domain.usercases

import android.bluetooth.BluetoothAdapter
import android.content.Context

class DisableBluetooth(private val context: Context) {

    operator fun invoke() {
        BluetoothAdapter.getDefaultAdapter().disable()
    }
}
