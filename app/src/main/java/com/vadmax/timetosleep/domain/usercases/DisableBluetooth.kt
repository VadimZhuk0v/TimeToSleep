package com.vadmax.timetosleep.domain.usercases

import android.bluetooth.BluetoothAdapter

fun interface DisableBluetooth {
    operator fun invoke()
}

class DisableBluetoothImpl : DisableBluetooth {

    override fun invoke() {
        BluetoothAdapter.getDefaultAdapter().disable()
    }
}
