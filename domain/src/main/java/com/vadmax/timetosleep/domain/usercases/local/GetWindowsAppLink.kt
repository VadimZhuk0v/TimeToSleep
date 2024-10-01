package com.vadmax.timetosleep.domain.usercases.local

import org.koin.core.annotation.Factory

fun interface GetWindowsAppLink {

    operator fun invoke(): String
}

@Factory(binds = [GetWindowsAppLink::class])
internal class GetWindowsAppLinkImpl : GetWindowsAppLink {

    override fun invoke(): String = "Link"
}
