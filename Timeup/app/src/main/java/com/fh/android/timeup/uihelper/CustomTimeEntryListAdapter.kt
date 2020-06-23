package com.fh.android.timeup.uihelper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.fh.android.timeup.R
import com.fh.android.timeup.dtos.TimeMeasurementDTO
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CustomTimeEntryListAdapter(context : Context,
                            resource :Int,
                            timeMeasurementList : ArrayList<TimeMeasurementDTO>)
    : ArrayAdapter<TimeMeasurementDTO>(context, resource, timeMeasurementList) {

    private var mResource : Int = 0
    private var timeMeasurements : ArrayList<TimeMeasurementDTO>

    private val mLayoutInflater: LayoutInflater

    init{
        this.mResource = resource
        this.timeMeasurements = timeMeasurementList
        this.mLayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val returnView: View?

        if(convertView == null){
            returnView = try {
                mLayoutInflater.inflate(mResource, null)
            } catch (ex :Exception){
                ex.printStackTrace()
                View(context)
            }
            setUI(returnView, position)
            return returnView
        }
        setUI(convertView, position)

        return convertView
    }

    private fun setUI(view : View, position : Int){
        val timeMeasurement : TimeMeasurementDTO? = if (count > position) getItem(position) else null

        if(timeMeasurement != null) {
            val workDate = LocalDate.of(LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMeasurement.beginDate), TimeZone.getDefault().toZoneId()).year,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMeasurement.beginDate), TimeZone.getDefault().toZoneId()).month,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMeasurement.beginDate), TimeZone.getDefault().toZoneId()).dayOfMonth)

            view.findViewById<TextView>(R.id.txWorkDate).text =
                workDate.format(DateTimeFormatter.ISO_DATE)
            view.findViewById<TextView>(R.id.txTotalTime).text =
                timeMeasurement.getWorkingDurationHourFormat()
        }
    }
}