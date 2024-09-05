package com.vadmax.timetosleep.domain.di

import com.vadimax.timetosleep.remote.di.remoteModules
import com.vadmax.timetosleep.local.di.localModules
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

@Module
@ComponentScan("com.vadmax.timetosleep.domain.usercases")
internal class UseCases

val domainModules = listOf(UseCases().module) + localModules + remoteModules
