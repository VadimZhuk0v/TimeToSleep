package com.vadimax.timetosleep.remote.di

import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module

val remoteModules = listOf(
    HttpModule().module,
    RepositoriesModule().module,
    defaultModule,
)
