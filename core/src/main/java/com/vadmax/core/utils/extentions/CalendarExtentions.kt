package com.vadmax.core.utils.extentions

import java.util.Calendar

var Calendar.hour: Int
    get() = get(Calendar.HOUR_OF_DAY)
    set(value) {
        set(Calendar.HOUR_OF_DAY, value)
    }
var Calendar.minute: Int
    get() = get(Calendar.MINUTE)
    set(value) {
        set(Calendar.MINUTE, value)
    }

var Calendar.second: Int
    get() = get(Calendar.SECOND)
    set(value) {
        set(Calendar.SECOND, value)
    }
