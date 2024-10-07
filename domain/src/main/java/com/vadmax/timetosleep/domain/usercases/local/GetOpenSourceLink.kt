package com.vadmax.timetosleep.domain.usercases.local

import org.koin.core.annotation.Factory

fun interface GetOpenSourceLink {

    operator fun invoke(): String
}

@Factory(binds = [GetOpenSourceLink::class])
internal class GetOpenSourceLinkImpl : GetOpenSourceLink {
    override fun invoke() = "https://github.com/VadimZhuk0v/TimeToSleep"
}
