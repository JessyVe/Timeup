package com.fh.android.timeup.dtos

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Represents on working session on a project.
 */
data class TimeMeasurementDTO(val beginDate : LocalDateTime,
                              val endDate : LocalDateTime
) {

    /**
     * Returns the absolute duration spend on the project as a formatted string in hours.
     */
    fun getWorkingDurationHourFormat(): String {
        var hourValue = getWorkingDuration() / 3600.0
        return String.format("%.1f h", hourValue)
    }

    /**
     * Returns the absolute duration spend in seconds.
     */
    fun getWorkingDuration(): Long {
        var duration = Duration.between(beginDate, endDate).abs()
        return duration.seconds
    }

    /**
     * Returns the date of the begin timestamp.
     */
    fun getWorkDate(): LocalDate {
        return LocalDate.of(beginDate.year, beginDate.month, beginDate.dayOfMonth)
    }
}