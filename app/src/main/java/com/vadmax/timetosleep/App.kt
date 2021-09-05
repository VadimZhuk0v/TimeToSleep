package com.vadmax.timetosleep

import android.app.Application
import com.vadmax.timetosleep.di.vmModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(vmModules)
        }
    }
}
