package com.vadmax.timetosleep

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.vadmax.timetosleep.di.appModules
import com.vadmax.timetosleep.utils.FirebaseCrashlyticsTree
import com.vadmax.timetosleep.utils.timber.TagTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKoin()
        initCrashlytics()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
        Timber.plant(TagTree())
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(appModules)
        }
    }

    private fun initCrashlytics() {
        if (BuildConfig.ENABLE_CRASHLYTICS) {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
            Timber.plant(
                FirebaseCrashlyticsTree(
                    FirebaseCrashlytics.getInstance().apply {
                        setCrashlyticsCollectionEnabled(true)
                    },
                ),
            )
        }
    }
}
