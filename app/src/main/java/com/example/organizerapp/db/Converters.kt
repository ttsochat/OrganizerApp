package com.example.organizerapp.db

import androidx.room.TypeConverter
import java.util.*

/**
 * Mandatory convertor for the Room Database entries to change
 * the date format from Unix timestamp to Kotlin Date object.
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}