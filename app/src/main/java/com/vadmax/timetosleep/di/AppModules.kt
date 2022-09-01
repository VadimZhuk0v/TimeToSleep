package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.domain.di.domainModules

val appModules = listOf(useCasesModule, vmModules) + domainModules
