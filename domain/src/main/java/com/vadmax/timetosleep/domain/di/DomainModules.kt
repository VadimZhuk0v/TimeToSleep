package com.vadmax.timetosleep.domain.di

import com.vadmax.timetosleep.local.di.localModules

val domainModules = listOf(useCasesModule) + localModules
