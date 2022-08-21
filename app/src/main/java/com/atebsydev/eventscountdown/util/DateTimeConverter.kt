package com.atebsydev.eventscountdown.util

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object DateTimeConverter {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME //Date Time with Offset	2011-12-03T10:15:30+01:00'

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}