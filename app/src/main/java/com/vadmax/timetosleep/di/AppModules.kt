package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.domain.di.domainModules
import org.koin.ksp.generated.module

val appModules = listOf(
    ViewModelModule().module,
    CoroutinesModule().module,
    UseCasesModule().module,
    RepositoriesModule().module,
    AnalyticsModule().module,
    FirebaseModule().module,
) + domainModules
