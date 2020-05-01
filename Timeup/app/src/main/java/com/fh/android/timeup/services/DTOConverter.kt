package com.fh.android.timeup.services

import com.fh.android.timeup.beans.Project
import com.fh.android.timeup.beans.TimeMeasurement
import com.fh.android.timeup.dtos.ProjectDTO
import com.fh.android.timeup.dtos.TimeMeasurementDTO
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

object DTOConverter{
    fun convertToProjectDTO(project: Project) : ProjectDTO{
        val timeMeasurements = project.timeMeasurements.map{ convertToTimeMeasurementDTO(it) }
        return ProjectDTO(project.title, ArrayList(timeMeasurements), project.estimatedHours,
            project.isClosed, project.projectHash)
    }

    private fun convertToTimeMeasurementDTO(timeMeasurement: TimeMeasurement) : TimeMeasurementDTO{
        return TimeMeasurementDTO(timeMeasurement.beginDate.atZone(ZoneOffset.UTC).toEpochSecond(),
                                  timeMeasurement.endDate.atZone(ZoneOffset.UTC).toEpochSecond())
    }

    fun convertFromProjectDTO(projectDTO: ProjectDTO) : Project{
        val timeMeasurements = projectDTO.timeMeasurements.map{ convertFromTimeMeasurementDTO(it) }
        return Project(projectDTO.title, ArrayList(timeMeasurements), projectDTO.estimatedHours,
            projectDTO.isClosed, projectDTO.projectHash)
    }

    private fun convertFromTimeMeasurementDTO(timeMeasurementDTO: TimeMeasurementDTO) : TimeMeasurement{
        return TimeMeasurement(
            LocalDateTime.ofInstant(Instant.ofEpochSecond(timeMeasurementDTO.beginTimestamp), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(timeMeasurementDTO.endTimestamp), ZoneId.systemDefault()))
    }
}