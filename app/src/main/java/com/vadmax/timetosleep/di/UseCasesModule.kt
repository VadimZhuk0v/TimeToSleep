package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.domain.usercases.SetTimerEnable
import org.koin.dsl.module

val useCasesModule = module {
    single { SetTimerEnable(get(), get()) }
}
