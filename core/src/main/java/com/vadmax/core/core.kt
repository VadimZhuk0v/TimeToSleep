package com.vadmax.core // ktlint-disable filename

import timber.log.Timber

val log: Logger = object : Logger {
    override fun d(message: String) {
        Timber.d(message)
    }

    override fun e(message: String) {
        Timber.e(message)
    }

    override fun e(exception: Exception, message: String) {
        Timber.e(exception, message)
    }

    override fun i(message: String) {
        Timber.i(message)
    }

    override fun w(message: String) {
        Timber.w(message)
    }
}
