package com.vadmax.io.utils.extentions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.toObject(json: String): T {
    val type = object : TypeToken<T>() {}.type
    return fromJson(json, type)
}
