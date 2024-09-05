package com.vadmax.timetosleep.domain.usecases

import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build
import org.koin.core.annotation.Factory

fun interface DisableBluetooth {
    operator fun invoke()
}

@Factory(binds = [DisableBluetooth::class])
class DisableBluetoothImpl(private val context: Context) : DisableBluetooth {

    override fun invoke() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager)
                .adapter
                .disable()
        }
    }
}
