package com.example.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {

    val gson = Gson()

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): Long? {
        return value?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
    }

    @TypeConverter
    fun toLocalDateTime(value: Long?): LocalDateTime?{
        return value?.let {
            Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDateTime()
        }
    }

    @TypeConverter
    fun fromListString(value: List<String>?): String?{
        return gson.toJson(value)
    }

    @TypeConverter
    fun toListString(value: String?): List<String>?{
        if (value == null) return null
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}