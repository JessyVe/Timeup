package com.fh.android.timeup

import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fh.android.timeup.dtos.ProjectDTO
import com.fh.android.timeup.dtos.TimeMeasurementDTO
import com.fh.android.timeup.models.ProjectModel
import kotlinx.android.synthetic.main.activity_stopwatch.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

class StopWatchActivity : AppCompatActivity() {
    private var isWatchRunning : Boolean = false
    private var index : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        index = intent.getSerializableExtra("PROJECT_POSITION") as? Int
        val project = ProjectModel.getProjectAt(index ?: -1)
        initializeUI(project ?: return)
    }

    private fun initializeUI(project: ProjectDTO){
        tvHeading.text = project.title

        btStartTimer.setOnClickListener {
            if(isWatchRunning)
                return@setOnClickListener

            isWatchRunning = true
            btStopTimer.visibility = android.view.View.VISIBLE
            btStopTimer.bringToFront()
            btStartTimer.visibility = android.view.View.GONE

            val currentTimeMeasurement = TimeMeasurementDTO (LocalDateTime.now().toMillis(), LocalDateTime.now().toMillis())
            GlobalScope.launch {
                while (isWatchRunning) {

                    val pastSeconds = ChronoUnit.SECONDS.between(
                        LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(currentTimeMeasurement.beginDate),
                            TimeZone.getDefault().toZoneId()
                        ), LocalDateTime.now()
                    )

                    tvTime.text = String.format(
                        "%02d:%02d:%02d",
                        pastSeconds / 3600,
                        (pastSeconds % 3600) / 60,
                        pastSeconds % 60
                    )
                    Thread.sleep(1_000)
                }
                currentTimeMeasurement.setNewEndDate(LocalDateTime.now().toMillis())
                project.addTimeMeasurement(currentTimeMeasurement)
                updateDatabaseEntry(project)
            }
        }

        btStopTimer.setOnClickListener {
            isWatchRunning = false
            btStopTimer.visibility = android.view.View.GONE
            btStartTimer.visibility = android.view.View.VISIBLE
            btStartTimer.bringToFront()
        }
    }

    private fun LocalDateTime.toMillis(zone: ZoneId = ZoneId.systemDefault()) = atZone(zone).toInstant().toEpochMilli()

    private fun updateDatabaseEntry(project: ProjectDTO){
        try {
            val map:HashMap<String, Any> = HashMap()
            val key = ProjectModel.getProjectAt(index!!)!!.GetSnapshot()!!.key!!
            map[key] = project.toMap()
            ProjectModel.updateProject(
                map
            )
        } catch (ex : Exception){
            Toast.makeText(
                applicationContext,
                "Time measurement was not added. ${ex.message}",
                Toast.LENGTH_SHORT
            ).show()
        } finally {
            reOpenOverview()
        }
    }

    private fun reOpenOverview(){
        val intent = Intent(this, ProjectOverviewActivity::class.java)
        intent.putExtra("PROJECT_POSITION", index)
        startActivity(intent)
    }
}