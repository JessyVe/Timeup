package com.fh.android.timeup.services

import com.fh.android.timeup.beans.Project
import com.fh.android.timeup.beans.TimeMeasurement
import com.fh.android.timeup.dtos.ProjectDTO
import com.fh.android.timeup.dtos.TimeMeasurementDTO

object DTOConverter{
 /*
    fun convertToProjectDTO(project: Project) : ProjectDTO{
        val timeMeasurements = project.timeMeasurements.map{ convertToTimeMeasurementDTO(it) }

        return ProjectDTO(project.title, ArrayList(timeMeasurements), project.estimatedHours,
            project.isClosed, project.projectHash)
    }

    private fun convertToTimeMeasurementDTO(timeMeasurement: TimeMeasurement) : TimeMeasurementDTO{
        return TimeMeasurementDTO(timeMeasurement.beginDate, timeMeasurement.endDate)
    }

    fun convertFromProjectDTO(projectDTO: ProjectDTO) : Project{
        val timeMeasurements = projectDTO.timeMeasurements.map{ convertFromTimeMeasurementDTO(it) }
        return Project(projectDTO.title, ArrayList(timeMeasurements), projectDTO.estimatedHours,
            projectDTO.isClosed, projectDTO.projectHash)
    }

    private fun convertFromTimeMeasurementDTO(timeMeasurementDTO: TimeMeasurementDTO) : TimeMeasurement{
        return TimeMeasurement(timeMeasurementDTO.beginDate, timeMeasurementDTO.endDate)
    }*/
}