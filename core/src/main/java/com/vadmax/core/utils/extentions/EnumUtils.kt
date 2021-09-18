package com.vadmax.core.utils.extentions

@Suppress("TooGenericExceptionCaught")
inline fun <reified T : Enum<T>> safeValueOf(type: String?): T? {
    type ?: return null
    return try {
        java.lang.Enum.valueOf(T::class.java, type)
    } catch (e: Exception) {
        return null
    }
}
