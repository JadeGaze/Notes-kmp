package com.example.shared.core.utils.resource

import kotlinx.datetime.Clock.System
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object TimeUtil {
    fun currentTimeUtc(): Long = System.now().toEpochMilliseconds()

    fun formatTimeWithDefaultPattern(utcTime: Long): String {
        val timeZone = TimeZone.currentSystemDefault()
        val instant = Instant.fromEpochMilliseconds(utcTime)
        val localDateTime = instant.toLocalDateTime(timeZone)

        val format =
            LocalDateTime.Format {
                monthName(MonthNames.ENGLISH_ABBREVIATED)
                dayOfMonth()
                chars(", ")
                year()
                chars(" ")
                hour()
                char(':')
                minute()
            }

        return localDateTime.format(format)
    }

    fun parseTimeToUTCSystemDefaults(input: String): Long {
        val format =
            LocalDateTime.Format {
                monthName(MonthNames.ENGLISH_ABBREVIATED)
                dayOfMonth()
                chars(", ")
                year()
                chars(" ")
                hour()
                char(':')
                minute()
            }

        val timeZone = TimeZone.currentSystemDefault()
        return try {
            val localDateTime = LocalDateTime.parse(input, format)
            localDateTime.toInstant(timeZone).toEpochMilliseconds()
        } catch (e: Exception) {
            currentTimeUtc()
        }
    }
}