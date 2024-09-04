package com.vadimax.timetosleep.remote.repositories

interface CommandApiRepository {

    suspend fun shutdown()
}

internal class CommandApiRepositoryImpl : CommandApiRepository {

    override suspend fun shutdown() {
//        client.post("/shutdown")
    }
}
