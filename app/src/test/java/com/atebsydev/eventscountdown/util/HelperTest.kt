package com.atebsydev.eventscountdown.util

import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.Period
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class HelperTest {


    @Test
    fun dateTimeHelper_formatDateToCountDownTimer_shouldReturnEmptyCountDown() {
        val yesterday = OffsetDateTime.now().minusDays(1)
        val expected = "-- -- -- ---"

        val actual = Helper.formatDateToCountDownTimer(yesterday)

        assertEquals(expected,actual)
    }

    @Test
    fun dateTimeHelper_formatOffsetDateTime_shouldReturnYesterday() {
        val eventDateAndTime = OffsetDateTime.now().minusDays(1)
        val expected = "Yesterday at ${eventDateAndTime.format(DateTimeFormatter.ofPattern("HH:mm:ss a"))}"

        val actual = Helper.formatOffsetDateTime(eventDateAndTime)

        assertEquals(expected,actual)
    }

    @Test
    fun dateTimeHelper_formatOffsetDateTime_shouldReturnToday() {
        val eventDateAndTime = OffsetDateTime.now()
        val expected = "Today at ${eventDateAndTime.format(DateTimeFormatter.ofPattern("HH:mm:ss a"))}"

        val actual = Helper.formatOffsetDateTime(eventDateAndTime)

        assertEquals(expected,actual)
    }

    @Test
    fun dateTimeHelper_formatOffsetDateTime_shouldReturnTomorrow() {
        val today = OffsetDateTime.now()
        val eventDateAndTime = today.plusDays(1)
        val expected = "Tomorrow at ${eventDateAndTime.format(DateTimeFormatter.ofPattern("HH:mm:ss a"))}"

        val actual = Helper.formatOffsetDateTime(eventDateAndTime)

        assertEquals(expected,actual)
    }
}
