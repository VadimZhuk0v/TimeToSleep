package com.vadmax.timetosleep.utils.timber

import timber.log.Timber

class TagTree : Timber.DebugTree() {

    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?,
    ) {
        super.log(priority, "DeveloperLog$tag", message, t)
    }
}
