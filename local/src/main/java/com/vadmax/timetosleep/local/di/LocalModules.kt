package com.vadmax.timetosleep.local.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

@Module
@ComponentScan("com.vadmax.timetosleep.local")
internal class PreferencesModule

val localModules = listOf(PreferencesModule().module)
