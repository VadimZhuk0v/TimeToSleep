package com.vadmax.timetosleep.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class CoroutinesModule {

    @Single
    fun scope() = CoroutineScope(Dispatchers.Main)
}
