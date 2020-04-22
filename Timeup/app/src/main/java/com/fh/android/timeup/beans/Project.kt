package com.fh.android.timeup.beans

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Project(var title:String,
                   var timeMeasurements:ArrayList<TimeMeasurement>,
                   var estimatedHours:Int,
                   var isClosed:Boolean)
{
    val formatter : DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    /**
     * Adds a new TimeMeasurement to the list of measurements.
     */
    fun addTimeMeasurement(timeMeasurement: TimeMeasurement){
        timeMeasurements.add(timeMeasurement)
    }

    /**
     * Removes the specified TimeMeasurement
     */
    fun removeTimeMeasurement(timeMeasurement: TimeMeasurement){
        timeMeasurements.remove(timeMeasurement)
    }

    /**
     * Returns the absolute duration spend as a formatted string in hours.
     */
    fun getTotalTimeSpendHourFormat() : String{
        var hourValue = getTotalTimeSpend()/3600.0
        return String.format("%.1f h", hourValue)
    }

    /**
     * Returns the absolute time spend on the project.
     */
    private fun getTotalTimeSpend() : Long {
        return timeMeasurements.map { measurement -> measurement.getWorkingDuration() }.sum()
    }

    /**
     * Returns the last date an entry was made.
     */
    fun getLastUpdateDate() : LocalDate? {
        return timeMeasurements.map{measurement -> measurement.getWorkDate()}.max()
    }

    /**
     * Returns the last update string.
     */
    fun getLastUpdateString() : String {
        var duration = Duration.between(getLastUpdateDate(), LocalDateTime.now()).abs().toMinutes()

        when {
            duration < 10 -> {
                return "Just now"
            }
            duration < 60 -> {
                return "This hour"
            }
            duration < 60*24 -> {
                return "Today"
            }
            duration < 60*24*7 -> {
                return "This week"
            }
            duration < 60*24*30 -> {
                return "This month"
            }
        }
        return getLastUpdateDate()?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "Unknown"
    }
}