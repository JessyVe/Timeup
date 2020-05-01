package com.fh.android.timeup.dtos

data class ProjectDTO (val title:String,
                    val timeMeasurements:ArrayList<TimeMeasurementDTO>,
                    val estimatedHours:Int,
                    val isClosed:Boolean,
                    var projectHash:String = "")
