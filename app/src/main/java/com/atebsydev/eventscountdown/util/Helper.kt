package com.atebsydev.eventscountdown.util

import java.time.OffsetDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt

object Helper {

    fun formatDateToCountDownTimer(eventDateTime: OffsetDateTime): String {
        var formattedCountDown = "-- -- -- ---"

        if (eventDateTime.isAfter(OffsetDateTime.now())) {
            var remainingDays = 0L
            var remainingHours = 0L
            val dueDateInSeconds = ChronoUnit.SECONDS.between(OffsetDateTime.now(), eventDateTime)
            if (dueDateInSeconds < 3600) {
                //adding 1 because i am not displaying seconds in the app
                formattedCountDown = if (dueDateInSeconds <= 60) {
                    "in ${floor((dueDateInSeconds / 60).toDouble()).roundToInt() + 1} Minute"
                } else {
                    "in ${round(floor((dueDateInSeconds / 60).toDouble())).roundToInt() + 1} Minutes"
                }
            } else {
                var secondsToHours = dueDateInSeconds / 60 / 60
                if (secondsToHours > 24) {
                    remainingHours %= 24
                    remainingDays = (secondsToHours - remainingHours) / 24
                } else {
                    remainingHours = secondsToHours
                }
                //adding 1 because i am not displaying seconds in the app
                var remainingMinutes = (dueDateInSeconds / 60 - secondsToHours * 60) + 1

                if (remainingMinutes == 60L && remainingHours == 0L) {
                    remainingMinutes = 0
                    remainingHours = 1
                } else if (remainingMinutes == 60L && remainingHours > 0) {
                    remainingMinutes = 0
                    remainingHours += 1
                }
                var formattedHrs = ""
                formattedHrs = if (remainingHours == 1L) {
                    "$remainingHours hr"
                } else if (remainingHours > 1) {
                    "$remainingHours hrs"
                } else {
                    ""
                }

                var formattedMinutes = ""
                formattedMinutes = if (remainingMinutes == 1L) {
                    "$remainingMinutes min"
                } else if (remainingMinutes > 0) {
                    "$remainingMinutes mins"
                } else {
                    ""
                }
                var formattedDay = ""
                if (remainingDays == 1L) {
                    formattedDay = "$remainingDays Day"
                } else if (remainingDays > 1L) {
                    formattedDay = "$remainingDays Days"
                }
                formattedCountDown = "in $formattedDay $formattedHrs $formattedMinutes"
            }
        }

        return formattedCountDown
    }

    fun formatOffsetDateTime(eventDateTime: OffsetDateTime): String {

        return if (Period.between(
                OffsetDateTime.now().toLocalDate(),
                eventDateTime.toLocalDate()
            ).days == -1
        ) {
            "Yesterday at ${eventDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss a"))}"
        } else if (Period.between(
                OffsetDateTime.now().toLocalDate(),
                eventDateTime.toLocalDate()
            ).days == 0
        ) {
            "Today at ${eventDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss a"))}"
        } else if (Period.between(
                OffsetDateTime.now().toLocalDate(),
                eventDateTime.toLocalDate()
            ).days == 1
        ) {
            "Tomorrow at ${eventDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss a"))}"
        } else {
            eventDateTime.format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a"))
        }
    }
}