package com.vadmax.io.di

import com.vadmax.io.domain.usercases.GetSelectedApps
import com.vadmax.io.domain.usercases.GetSelectedTime
import com.vadmax.io.domain.usercases.IsTimerEnable
import com.vadmax.io.domain.usercases.SelectApp
import com.vadmax.io.domain.usercases.SetSelectedTime
import com.vadmax.io.domain.usercases.SetTimerEnable
import com.vadmax.io.domain.usercases.UnselectApp
import org.koin.dsl.module

val useCasesModule = module {
    single { IsTimerEnable(get()) }
    single { SetTimerEnable(get()) }
    single { SetSelectedTime(get()) }
    single { GetSelectedTime(get()) }
    single { SelectApp(get()) }
    single { UnselectApp(get()) }
    single { GetSelectedApps(get()) }
}
