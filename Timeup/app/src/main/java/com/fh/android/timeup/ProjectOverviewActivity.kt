package com.fh.android.timeup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fh.android.timeup.dtos.ProjectDTO
import com.fh.android.timeup.models.ProjectModel
import com.fh.android.timeup.uihelper.CustomTimeEntryListAdapter
import kotlinx.android.synthetic.main.activity_project_overview.*

class ProjectOverviewActivity : AppCompatActivity() {

    private var listAdapter: CustomTimeEntryListAdapter? = null
    private var projectIndex : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_overview)

        projectIndex = intent.getSerializableExtra("PROJECT_POSITION") as Int
        val project = ProjectModel.getProjectAt(projectIndex)
        initializeUI(project)
    }

    private fun initializeUI(project : ProjectDTO){
        tvProjectName.text = project.title
        tvEstimatedTime.text = project.getEstimatedTimeFormat()
        tvTotalTime.text = project.getTotalTimeSpendHourFormat()

        listAdapter = CustomTimeEntryListAdapter(this, R.layout.time_measurement_row, project.timeMeasurements)
        lvTimeEntries.adapter = listAdapter

        btStartWorking.setOnClickListener {
            val intent = Intent(this, StopWatchActivity::class.java)
            intent.putExtra("PROJECT_POSITION", projectIndex)
            startActivity(intent)
        }
    }
}