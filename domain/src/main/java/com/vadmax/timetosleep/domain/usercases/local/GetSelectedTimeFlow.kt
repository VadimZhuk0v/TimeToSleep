package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

fun interface GetSelectedTimeFlow {

    operator fun invoke(): Flow<Long?>

}

@Factory(binds = [GetSelectedTimeFlow::class])
internal class GetSelectedTimeFlowImpl(private val settingsProvider: SettingsProvider) :
    GetSelectedTimeFlow {
    override operator fun invoke(): Flow<Long?> = settingsProvider.selectedTimeFlow
}
