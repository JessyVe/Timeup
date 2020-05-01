package com.fh.android.timeup.beans

import com.fh.android.timeup.enums.UpdateStrings
import com.fh.android.timeup.services.HashingEngine
import java.text.DecimalFormat
import java.time.*
import java.time.format.DateTimeFormatter

data class Project (val title:String,
                    val timeMeasurements:ArrayList<TimeMeasurement>,
                    val estimatedHours:Int,
                    val isClosed:Boolean,
                    var projectHash:String = "") : IHashable {

    init {
        HashingEngine.setHashOfObject(this)
    }

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
        val dec = DecimalFormat("##.#")
        var hourValue = getTotalTimeSpend()/3600.0

        return dec.format(hourValue) + " h"
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
        var duration = Duration.between(getLastUpdateTimestamp(), LocalDateTime.now()).abs().toMinutes()

        when {
            duration < 10 -> {
                return UpdateStrings.JUST_NOW.description
            }
            duration < 60*24 -> {
                return UpdateStrings.TODAY.description
            }
            duration < 60*24*7 -> {
                return UpdateStrings.THIS_WEEK.description
            }
            duration < 60*24*30 -> {
                return UpdateStrings.THIS_MONTH.description
            }
        }
        return getLastUpdateDate()?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "Unknown"
    }

    /**
     * Returns the last end-timestamp an entry was made.
     */
    private fun getLastUpdateTimestamp() : LocalDateTime? {
        return timeMeasurements.map{measurement -> measurement.beginDate }.max()
    }

    override fun getHashingString(): String {
       return String.format("%s%s", title, LocalDateTime.now().toString())
    }

    override fun setHash(projectHash: String) {
        if(projectHash.isNullOrEmpty())
            this.projectHash = projectHash
    }

    override fun getHash() : String {
        return projectHash
    }
}