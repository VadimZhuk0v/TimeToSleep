package com.vadmax.timetosleep.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class FirebaseCrashlyticsTree(private val crashlytics: FirebaseCrashlytics) : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        throwable?.let {
            // Report only critical exceptions
            if (priority == Log.ERROR) {
                crashlytics.log("$tag: $message")
                crashlytics.recordException(throwable)
            } else {
                crashlytics.log("$tag: $message. Error: ${throwable.message}")
            }
        } ?: run {
            crashlytics.log("$tag: $message")
        }
    }
}
