package com.fh.android.timeup

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fh.android.timeup.database_access.FirebaseAccess
import com.fh.android.timeup.dtos.ProjectDTO
import com.fh.android.timeup.models.ProjectModel
import com.fh.android.timeup.uihelper.CustomListItemAdapter
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.coroutines.NonCancellable.cancel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), Observer {

    private var lvProjects: ListView? = null
    private var btAddProject: Button? = null
    private var listAdapter: CustomListItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ProjectModel
        ProjectModel.addObserver(this)

        lvProjects = findViewById(R.id.lvProjects)
        val data: ArrayList<ProjectDTO> = ArrayList()
        listAdapter = CustomListItemAdapter(this, R.layout.openprojectrow, data)
        lvProjects?.adapter = listAdapter

        btAddProject = findViewById(R.id.btAddProject)
        btAddProject?.setOnClickListener {
            /*
            val newProject = ProjectDTO(null)
            newProject.title = "Project 1"
            newProject.estimatedHours = 500

            ProjectModel.saveProject(newProject, OnCompleteListener {
              task -> if(task.isComplete){
              Toast.makeText(applicationContext, "New Project was saved!", Toast.LENGTH_SHORT)
               }
              if(task.isCanceled){
                  Toast.makeText(applicationContext, "Unable to create project", Toast.LENGTH_SHORT)
              }
              if(task.isSuccessful){
                  Toast.makeText(applicationContext, "New Project was saved successfully!", Toast.LENGTH_SHORT)
              }
            })
            */

            /*
            val intent = Intent(this, ProjectOverviewActivity::class.java).apply {
             putExtra("EXTRA_MESSAGE", "message")
            }
            startActivity(intent)
            */
        }
    }

    override fun update(p0: Observable?, p1: Any?) {
        listAdapter?.clear()

        val data = ProjectModel.getData()
        if (data != null) {
            listAdapter?.addAll(data)
            listAdapter?.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        ProjectModel.addObserver(this)
    }

    override fun onPause() {
        super.onPause()
        ProjectModel.deleteObserver(this)
    }

    override fun onStop() {
        super.onStop()
        ProjectModel.deleteObserver(this)
    }
}