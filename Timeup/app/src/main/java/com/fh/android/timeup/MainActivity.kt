package com.fh.android.timeup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fh.android.timeup.beans.Project
import com.fh.android.timeup.beans.TimeMeasurement
import com.fh.android.timeup.database_access.FirebaseAccess
import com.fh.android.timeup.dtos.ProjectDTO
import com.fh.android.timeup.services.DTOConverter
import com.fh.android.timeup.uihelper.CustomListItemAdapter
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var lvProjects: ListView? = null
    var btAddProject : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvProjects = findViewById(R.id.lvProjects);
        btAddProject = findViewById(R.id.btAddProject)

        setupProjectsList()

        btAddProject?.setOnClickListener{
           val intent = Intent(this, ProjectOverviewActivity::class.java).apply {
               putExtra("EXTRA_MESSAGE", "message")
           }
           startActivity(intent)
        }
    }

    private fun setupProjectsList(){
        lvProjects?.adapter = CustomListItemAdapter(this@MainActivity, getLoadProjects())
    }

    private fun  getLoadProjects() : ArrayList<Project>{
        try {
            val projectDTOs = FirebaseAccess.loadFromDatabase<List<ProjectDTO>>("projects")
            return ArrayList(projectDTOs.map{ DTOConverter.convertFromProjectDTO(it)})
        } catch (ex: Exception){
            Toast.makeText(applicationContext, ex.toString(), Toast.LENGTH_SHORT)
        }
        return ArrayList()
    }
}