package com.vadmax.timetosleep.local.di

import com.vadmax.timetosleep.local.SettingsProvider
import com.vadmax.timetosleep.local.SettingsProviderImpl
import org.koin.dsl.module

val preferencesModule = module {
    single<SettingsProvider> { SettingsProviderImpl(get(), get()) }
}
