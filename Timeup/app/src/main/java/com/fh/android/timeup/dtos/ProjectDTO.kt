package com.fh.android.timeup.dtos

import com.fh.android.timeup.enums.UpdateStrings
import com.google.firebase.database.DataSnapshot
import java.lang.Exception
import java.text.DecimalFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ProjectDTO (snapshot: DataSnapshot?)
{
    lateinit var title:String
    var estimatedHours:Long = 0
    var isClosed:Boolean = false
    var projectHash:String = ""

    var timeMeasurements: ArrayList<TimeMeasurementDTO> = ArrayList()

    init {
        if(snapshot != null)
            createProjectFromSnapshot(snapshot)
    }

    private fun createProjectFromSnapshot(snapshot : DataSnapshot){
        try{
            val data : HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            title = data["title"] as String
            estimatedHours = data["estimatedHours"] as Long
            isClosed = data["isClosed"] as Boolean
            projectHash =  data["projectHash"] as String
            timeMeasurements = data["timeMeasurements"] as ArrayList<TimeMeasurementDTO>
        } catch (ex : Exception){
            ex.printStackTrace()
        }
    }

    fun toMap() : HashMap<String, Any>{
        val map:HashMap<String, Any> = HashMap()
        map["title"] = title
        map["estimatedHours"] = estimatedHours
        map["isClosed"] = isClosed
        map["projectHash"] = projectHash
        map["timeMeasurements"] = timeMeasurements

        return map
    }

    /**
     * Adds a new TimeMeasurement to the list of measurements.
     */
    fun addTimeMeasurement(timeMeasurement: TimeMeasurementDTO){
        timeMeasurements.add(timeMeasurement)
    }

    /**
     * Removes the specified TimeMeasurement
     */
    fun removeTimeMeasurement(timeMeasurement: TimeMeasurementDTO){
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
        return timeMeasurements.map{measurement ->
            LocalDate.of(LocalDateTime.ofInstant(Instant.ofEpochMilli(measurement.beginDate), TimeZone.getDefault().toZoneId()).year,
            LocalDateTime.ofInstant(Instant.ofEpochMilli(measurement.beginDate), TimeZone.getDefault().toZoneId()).month,
            LocalDateTime.ofInstant(Instant.ofEpochMilli(measurement.beginDate), TimeZone.getDefault().toZoneId()).dayOfMonth)
        }.max()
    }

    /**
     * Returns the last update string.
     */
    fun getLastUpdateString() : String {
        var lastUpdateTimeStamp = getLastUpdateTimestamp()

        if(lastUpdateTimeStamp == null)
            return UpdateStrings.UNKOWN.description

        var duration = Duration.between(lastUpdateTimeStamp, LocalDateTime.now()).abs().toMinutes()

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
        val beginDate = timeMeasurements.map{ measurement -> measurement.beginDate}.max() ?: return null
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(beginDate), TimeZone.getDefault().toZoneId())
    }

    fun getEstimatedTimeFormat() : String {
        return String.format("Estimated hours %d h", estimatedHours)
    }
}