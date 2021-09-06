package com.vadmax.timetosleep

import android.app.Application
import com.vadmax.io.di.ioModules
import com.vadmax.timetosleep.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@App)
            modules(appModules + ioModules)
        }
    }
}
