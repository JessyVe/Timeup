package com.fh.android.timeup

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.fh.android.timeup.beans.Project
import com.fh.android.timeup.beans.TimeMeasurement
import com.fh.android.timeup.uihelper.CustomListItemAdapter
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    var lvProjects: ListView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvProjects = findViewById(R.id.lvProjects);

        SetupProjectsList();
    }

    private fun SetupProjectsList(){
        lvProjects?.adapter = CustomListItemAdapter(this@MainActivity, getTestProjects())
    }

    private fun getTestProjects() : ArrayList<Project>{
        val measurementList1 = mutableListOf<TimeMeasurement>(
            TimeMeasurement(LocalDateTime.of(2020,1,1,13,30,0), LocalDateTime.of(2020,1,1,13,30,0)),
            TimeMeasurement(LocalDateTime.of(2020,1,1,13,30,0), LocalDateTime.of(2020,1,1,13,30,0))
        )
        val measurementList2 = mutableListOf<TimeMeasurement>(
            TimeMeasurement(LocalDateTime.of(2020,1,1,13,30,0), LocalDateTime.of(2020,1,1,13,30,0))
        )
        val projects =  mutableListOf<Project>(
            Project("My Test Project1", ArrayList(measurementList1), 500, false),
            Project("My Test Project2", ArrayList(measurementList2), 120, false),
            Project("My Test Project1", ArrayList(measurementList1), 500, false),
            Project("My Test Project2", ArrayList(measurementList2), 120, false)
        )
        return ArrayList(projects)
    }
}
