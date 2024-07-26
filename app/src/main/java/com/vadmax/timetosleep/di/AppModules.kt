package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.domain.di.domainModules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModules = listOf(
    useCasesModule,
    vmModules,
    module { single { CoroutineScope(Dispatchers.Main) } },
) + domainModules
