package com.vadmax.timetosleep.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.vadmax.timetosleep.local.data.ServerDataModel
import java.io.InputStream
import java.io.OutputStream

object ServerDataModelSerializer : Serializer<ServerDataModel> {
    override val defaultValue = ServerDataModel.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ServerDataModel {
        try {
            return ServerDataModel.parseFrom(input)
        } catch (exception: Exception) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(
        t: ServerDataModel,
        output: OutputStream,
    ) = t.writeTo(output)
}
