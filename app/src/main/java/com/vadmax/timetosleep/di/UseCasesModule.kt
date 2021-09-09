package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.domain.usercases.ApplyActions
import com.vadmax.timetosleep.domain.usercases.CloseApps
import com.vadmax.timetosleep.domain.usercases.GetAppsList
import com.vadmax.timetosleep.domain.usercases.SetAlarmActivation
import com.vadmax.timetosleep.domain.usercases.StopMusic
import org.koin.dsl.module

val useCasesModule = module {
    single { SetAlarmActivation(get(), get(), get()) }
    single { GetAppsList(get()) }
    single { CloseApps(get(), get()) }
    single { StopMusic(get()) }
    single { ApplyActions(get(), get(), get()) }
}
