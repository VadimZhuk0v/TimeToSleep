package com.vadmax.timetosleep.domain.usercases.local

import org.koin.core.annotation.Factory

fun interface GetPCDownloadLink {

    operator fun invoke(): String
}

@Factory(binds = [GetPCDownloadLink::class])
internal class GetPCDownloadLinkImpl : GetPCDownloadLink {
    override fun invoke() = "https://github.com/VadimZhuk0v/Time-To-Sleep-KMP"
}
