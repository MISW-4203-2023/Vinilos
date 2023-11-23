package com.team3.vinilos.model.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.team3.proto.FavoritePreferences
import java.io.InputStream
import java.io.OutputStream

object FavoriteSeralizer : Serializer<FavoritePreferences> {
    override val defaultValue: FavoritePreferences = FavoritePreferences.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): FavoritePreferences {
        try {
            return FavoritePreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: FavoritePreferences, output: OutputStream) = t.writeTo(output)
}

