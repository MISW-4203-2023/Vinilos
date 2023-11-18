package com.team3.vinilos.model.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Serializer(forClass = Date::class)
class DateSerializer : KSerializer<Date?> {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    override fun serialize(encoder: Encoder, value: Date?) {
        encoder.encodeString(simpleDateFormat.format(value))
    }

    override fun deserialize(decoder: Decoder): Date? {
        return simpleDateFormat.parse(decoder.decodeString())
    }

}