package com.vadmax.timetosleep

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.vadmax.io.di.ioModules
import com.vadmax.timetosleep.di.appModules
import com.vadmax.timetosleep.ui.widgets.ad.interstitial.loadInterstitialAd
import com.vadmax.timetosleep.utils.FirebaseCrashlyticsTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKoin()
        initAdmob()
        initCrashlytics()
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
        val conf = RequestConfiguration.Builder()
            .setTagForChildDirectedTreatment(TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
            .build()
        MobileAds.setRequestConfiguration(conf)
        MobileAds.initialize(this)
    }

    private fun initCrashlytics() {
        if (BuildConfig.ENABLE_CRASHLYTICS) {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
            Timber.plant(
                FirebaseCrashlyticsTree(
                    FirebaseCrashlytics.getInstance().apply {
                        setCrashlyticsCollectionEnabled(true)
                    }
                )
            )
        }
    }
}
