package com.vadmax.io.di

import com.vadmax.io.domain.usercases.IsTimerEnable
import com.vadmax.io.domain.usercases.SetTimerEnable
import org.koin.dsl.module

val useCasesModule = module {
    single { IsTimerEnable(get()) }
    single { SetTimerEnable(get()) }
}
