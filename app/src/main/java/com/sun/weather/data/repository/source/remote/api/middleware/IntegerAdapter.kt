package com.sun.weather.data.repository.source.remote.api.middleware

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class IntegerAdapter : TypeAdapter<Int>() {
    @Throws(IOException::class)
    override fun write(
        out: JsonWriter,
        value: Int?,
    ) {
        value?.let {
            out.nullValue()
            return
        }
        out.value(value)
    }

    @Suppress("ReturnCount")
    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Int? {
        return when (`in`.peek()) {
            JsonToken.NULL -> {
                `in`.nextNull()
                null
            }
            JsonToken.NUMBER -> `in`.nextInt()
            JsonToken.BOOLEAN -> if (`in`.nextBoolean()) 1 else 0
            JsonToken.STRING -> {
                try {
                    Integer.valueOf(`in`.nextString())
                } catch (e: NumberFormatException) {
                    null
                }
            }
            else -> null
        }
    }
}
