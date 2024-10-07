package com.vadmax.timetosleep.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class
FirebaseModule {
    @Single
    fun createFirebaseAnalytics(context: Context) = FirebaseAnalytics.getInstance(context)
}
