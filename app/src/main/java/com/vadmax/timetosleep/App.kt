package com.vadmax.timetosleep

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.vadmax.io.di.ioModules
import com.vadmax.timetosleep.di.appModules
import com.vadmax.timetosleep.ui.widgets.ad.interstitial.loadInterstitialAd
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKoin()
        initAdmob()
        loadInterstitialAd(this)
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(appModules + ioModules)
        }
    }

    private fun initAdmob() {
        MobileAds.initialize(this)
    }
}
