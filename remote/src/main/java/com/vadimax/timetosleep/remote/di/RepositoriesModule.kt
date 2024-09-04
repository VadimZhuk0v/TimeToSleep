package com.vadimax.timetosleep.remote.di

import com.vadimax.timetosleep.remote.repositories.CommandApiRepository
import com.vadimax.timetosleep.remote.repositories.CommandApiRepositoryImpl
import org.koin.dsl.module

internal val repositoriesModule = module {
    single<CommandApiRepository> { CommandApiRepositoryImpl() }
}
