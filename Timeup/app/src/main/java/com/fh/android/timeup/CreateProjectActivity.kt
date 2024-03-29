package com.fh.android.timeup

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fh.android.timeup.dtos.ProjectDTO
import com.fh.android.timeup.models.ProjectModel
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_create_project.*

class CreateProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_project)

        initializeUI()
    }

    private fun initializeUI(){
        btCreateNewProject.setOnClickListener{
            createProject()
        }
    }

    private fun createProject(){
        val projectName = txProjectname.text.toString()
        var estimatedHourString = txEstimatedHours.text.toString()

        if(projectName.equals("")){
            Toast.makeText(this, "No project name was given!", Toast.LENGTH_SHORT).show()
            return
        }

        if(estimatedHourString.equals(""))
            estimatedHourString = "0"

        val newProject = ProjectDTO(null)
        newProject.title = projectName
        newProject.estimatedHours = estimatedHourString.toLong()

        ProjectModel.saveProject(newProject, OnCompleteListener { task ->
            if(task.isCanceled){
                Toast.makeText(this, "Unable to create project", Toast.LENGTH_SHORT).show()
            }
            if(task.isSuccessful){
                Toast.makeText(this, "New Project was saved successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }
}