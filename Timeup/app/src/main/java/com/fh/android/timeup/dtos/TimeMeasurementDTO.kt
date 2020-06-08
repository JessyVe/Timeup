package com.fh.android.timeup.dtos

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

/**
 * Represents on working session on a project.
 */
data class TimeMeasurementDTO(val beginDate : Long,
                              var endDate : Long) {
    /**
     * Returns the absolute duration spend on the project as a formatted string in hours.
     */
    fun getWorkingDurationHourFormat(): String {
        var hourValue = getWorkingDuration() / 3600.0
        return String.format("%.4f h", hourValue)
    }

    /**
     * Returns the absolute duration spend in seconds.
     */
    fun getWorkingDuration(): Long {
        var duration = Duration.between(LocalDateTime.ofInstant(Instant.ofEpochMilli(beginDate), TimeZone.getDefault().toZoneId()),
            LocalDateTime.ofInstant(Instant.ofEpochMilli(endDate), TimeZone.getDefault().toZoneId())).abs()
        return duration.seconds
    }

    /**
     * Returns the date of the begin timestamp.

    fun getWorkDate(): LocalDate {
        return LocalDate.of(getBeginDate().year, getBeginDate().month, getBeginDate().dayOfMonth)
    }

    fun getBeginDate() : LocalDateTime{
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(beginDate), TimeZone.getDefault().toZoneId())
    }

    fun getEndDate() : LocalDateTime{
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(endDate), TimeZone.getDefault().toZoneId())
    }
     */

    fun setNewEndDate(endDate: Long) {
        this.endDate = endDate
    }
}