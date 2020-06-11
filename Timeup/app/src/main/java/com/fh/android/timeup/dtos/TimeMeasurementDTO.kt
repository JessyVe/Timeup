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
        val hourValue = getWorkingDuration() / 3600
        val minuteValue = getWorkingDuration() % 60

        if(hourValue <= 0)
            return String.format("%d min", minuteValue)

        return String.format("%d h %d min", hourValue, minuteValue)
    }

    /**
     * Returns the absolute duration spent in seconds.
     */
    fun getWorkingDuration(): Long {
        var duration = Duration.between(LocalDateTime.ofInstant(Instant.ofEpochMilli(beginDate), TimeZone.getDefault().toZoneId()),
            LocalDateTime.ofInstant(Instant.ofEpochMilli(endDate), TimeZone.getDefault().toZoneId())).abs()
        return duration.seconds
    }

    fun setNewEndDate(endDate: Long) {
        this.endDate = endDate
    }
}