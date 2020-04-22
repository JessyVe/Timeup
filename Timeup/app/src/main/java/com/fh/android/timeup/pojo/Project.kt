package com.fh.android.timeup.pojo

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

data class Project(var title:String,
                   var timeMeasurements : ArrayList<TimeMeasurement>,
                   var isClosed : Boolean)
{
    /**
     * Sets the title property to the given value.
     */
    fun setProjectTitle(title : String) { this.title = title }

    /**
     * Sets the isClosed property to the given value.
     */
    fun setProjectStatus(isClosed : Boolean) { this.isClosed = isClosed }

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
        var hourValue = BigDecimal(getTotalTimeSpend()/360).setScale(1, RoundingMode.HALF_EVEN)
        return "$hourValue h"
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
}