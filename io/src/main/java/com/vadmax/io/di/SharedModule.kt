package com.vadmax.io.di

import com.vadmax.io.data.SettingsProvider
import com.vadmax.io.data.SettingsProviderImpl
import org.koin.dsl.module

val sharedModule = module {
    single<SettingsProvider> { SettingsProviderImpl(get()) }
}
