package com.fh.android.timeup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.fh.android.timeup.dtos.ProjectDTO
import com.fh.android.timeup.dtos.TimeMeasurementDTO
import com.fh.android.timeup.models.ProjectModel
import kotlinx.android.synthetic.main.activity_stopwatch.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class StopWatchActivity : AppCompatActivity() {
    private var isWatchRunning : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        val projectIndex = intent.getSerializableExtra("PROJECT_POSITION") as Int
        val project = ProjectModel.getProjectAt(projectIndex)

        initializeUI(project)
    }

    private fun initializeUI(project: ProjectDTO){
        tvHeading.text = project.title

        btStartTimer.setOnClickListener {
            if(isWatchRunning)
                return@setOnClickListener

            isWatchRunning = true
            btStopTimer.isEnabled = true
            btStartTimer.isEnabled = false

            val currentTimeMeasurement = TimeMeasurementDTO (LocalDateTime.now(), LocalDateTime.now())
            GlobalScope.launch {
                while (isWatchRunning) {
                    //Handler().postDelayed( {
                    val pastTime = Duration.between(currentTimeMeasurement.beginDate, LocalDateTime.now()).abs()
                    tvTime.text = LocalTime.ofSecondOfDay(pastTime.seconds).toString()
                    //}, 1000)
                    Thread.sleep(1_000)
                }
                project.timeMeasurements.add(currentTimeMeasurement)
                isWatchRunning = false;
            }
        }

        btStopTimer.setOnClickListener {
            isWatchRunning = false
            btStopTimer.isEnabled = false
            btStartTimer.isEnabled = true
        }
    }
}