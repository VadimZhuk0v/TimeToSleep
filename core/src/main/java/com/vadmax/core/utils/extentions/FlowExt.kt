package com.vadmax.core.utils.extentions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

fun <T, M> StateFlow<T>.map(
    coroutineScope: CoroutineScope,
    starter: SharingStarted = SharingStarted.WhileSubscribed(@Suppress("MagicNumber") 500),
    mapper: (value: T) -> M,
): StateFlow<M> = map { mapper(it) }.stateIn(
    coroutineScope,
    starter,
    mapper(value),
)
