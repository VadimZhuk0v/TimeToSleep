package com.vadmax.io.utils.extentions

import android.content.SharedPreferences

fun SharedPreferences.putString(name: String, value: String?) {
    if (value == null) {
        edit().remove(name).apply()

        return
    }

    this.edit().putString(name, value).apply()
}

fun SharedPreferences.putInt(name: String, value: Int?) {
    if (value == null) {
        edit().remove(name).apply()

        return
    }

    this.edit().putInt(name, value).apply()
}

fun SharedPreferences.putLong(name: String, value: Long?) {
    if (value == null) {
        edit().remove(name).apply()

        return
    }

    this.edit().putLong(name, value).apply()
}

fun SharedPreferences.putBoolean(name: String, value: Boolean) {
    this.edit().putBoolean(name, value).apply()
}

fun SharedPreferences.getLongOrNull(name: String): Long? {
    if (contains(name).not()) {
        return null
    }

    return getLong(name, 0L)
}
