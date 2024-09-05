package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.domain.di.domainModules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.dsl.module
import org.koin.ksp.generated.module

@Module
@ComponentScan("com.vadmax.timetosleep.ui")
class ViewModelModule

@Module
@ComponentScan("com.vadmax.timetosleep.domain.usercases")
class UseCases

@Module
class CoroutinesModule {

    @Single
    fun scope() = CoroutineScope(Dispatchers.Main)
}

val appModules = listOf(
    ViewModelModule().module,
    CoroutinesModule().module,
    UseCases().module,
) + domainModules
