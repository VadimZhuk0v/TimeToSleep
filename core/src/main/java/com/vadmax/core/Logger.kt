package com.vadmax.core

interface Logger {

    fun d(message: String)

    fun e(message: String)

    fun e(exception: Exception, message: String)

    fun i(message: String)

    fun w(message: String)
}
