package com.fh.android.timeup.dtos

import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class ProjectDTO (snapshot: DataSnapshot?)
{
    lateinit var title:String
    var estimatedHours:Int = 0
    var isClosed:Boolean = false
    var projectHash:String = ""

    var timeMeasurements:List<TimeMeasurementDTO> = mutableListOf()

    init {
        if(snapshot != null)
            createProjectFromSnapshot(snapshot)
    }

    private fun createProjectFromSnapshot(snapshot : DataSnapshot){
        try{
            val data : HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            title = data["title"] as String
            estimatedHours = data["estimatedHours"] as Int
            isClosed = data["isClosed"] as Boolean
            projectHash =  data["projectHash"] as String
            timeMeasurements = data["timeMeasurements"] as List<TimeMeasurementDTO>
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
}