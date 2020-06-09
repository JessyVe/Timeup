package com.fh.android.timeup

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fh.android.timeup.dtos.ProjectDTO
import com.fh.android.timeup.models.ProjectModel
import com.fh.android.timeup.uihelper.CustomTimeEntryListAdapter
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_project_overview.*

class ProjectOverviewActivity : AppCompatActivity() {

    private var listAdapter: CustomTimeEntryListAdapter? = null
    private var projectIndex : Int = -1
    private var currentProject : ProjectDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_overview)

        projectIndex = intent.getSerializableExtra("PROJECT_POSITION") as Int
        val project = ProjectModel.getProjectAt(projectIndex)

        if(project == null){
            Toast.makeText(this, "Project could not be loaded", Toast.LENGTH_LONG).show()
            finish()
        } else {
            currentProject = project
            initializeUI()
        }
    }

    private fun initializeUI(){
        tvProjectName.text = currentProject!!.title
        tvEstimatedTime.text = currentProject!!.getEstimatedTimeFormat()
        tvTotalTime.text = currentProject!!.getTotalTimeSpendHourFormat()

        listAdapter = CustomTimeEntryListAdapter(this, R.layout.time_measurement_row, currentProject!!.timeMeasurements)
        lvTimeEntries.adapter = listAdapter

        btStartWorking.setOnClickListener {
            val intent = Intent(this, StopWatchActivity::class.java)
            intent.putExtra("PROJECT_POSITION", projectIndex)
            startActivity(intent)
        }

        btDeleteProject.setOnClickListener {
           showDialog()
        }
    }

    private fun showDialog() {
        lateinit var dialog:AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete project")
        builder.setMessage("Do you really want to delete this project?")

        val dialogClickListener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> deleteProject()
            }
        }
        builder.setPositiveButton("YES",dialogClickListener)
        builder.setNegativeButton("NO",dialogClickListener)
        builder.setNeutralButton("CANCEL",dialogClickListener)

        dialog = builder.create()

        dialog.show()
    }

    private fun deleteProject(){
        ProjectModel.deleteProject(currentProject!!, OnCompleteListener { task ->
            if(task.isCanceled){
                Toast.makeText(this, "Unable to delete project", Toast.LENGTH_SHORT).show()
            }
            if(task.isSuccessful){
                Toast.makeText(this, "Project was deleted!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, OverviewActivity::class.java)
                startActivity(intent)
            }
        })
    }
}