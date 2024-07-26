package com.vadmax.timetosleep.domain.usercases

import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build

fun interface DisableBluetooth {
    operator fun invoke()
}

class DisableBluetoothImpl(private val context: Context) : DisableBluetooth {

    override fun invoke() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager)
                .adapter
                .disable()
        }
    }
}
