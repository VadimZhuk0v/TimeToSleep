package com.vadmax.timetosleep.domain.di

import com.vadimax.timetosleep.remote.di.remoteModules
import com.vadmax.timetosleep.local.di.localModules

val domainModules = listOf(useCasesModule) + localModules + remoteModules
