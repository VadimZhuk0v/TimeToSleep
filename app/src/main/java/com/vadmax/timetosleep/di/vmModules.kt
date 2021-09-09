package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.ui.screens.applications.ApplicationsViewModel
import com.vadmax.timetosleep.ui.screens.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModules = module {
    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { ApplicationsViewModel(get(), get(), get(), get()) }
}
