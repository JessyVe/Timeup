package com.fh.android.timeup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fh.android.timeup.dtos.TimeMeasurementDTO
import com.fh.android.timeup.uihelper.CustomTimeEntryListAdapter
import kotlinx.android.synthetic.main.activity_project_overview.*
import java.time.LocalDateTime

class ProjectOverviewActivity : AppCompatActivity() {

    private var listAdapter: CustomTimeEntryListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_overview)

        val data: ArrayList<TimeMeasurementDTO> = arrayListOf(
            TimeMeasurementDTO(LocalDateTime.now(), LocalDateTime.now())
        )
        listAdapter = CustomTimeEntryListAdapter(this, R.layout.time_measurement_row, data)
        lvTimeEntries.adapter = listAdapter
    }
}