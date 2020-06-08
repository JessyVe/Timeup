package com.fh.android.timeup.uihelper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.fh.android.timeup.R
import com.fh.android.timeup.dtos.TimeMeasurementDTO
import java.time.format.DateTimeFormatter

class CustomTimeEntryListAdapter(context : Context,
                            resource :Int,
                            timeMeasurementList : ArrayList<TimeMeasurementDTO>)
    : ArrayAdapter<TimeMeasurementDTO>(context, resource, timeMeasurementList) {

    private var mResource : Int = 0
    private var mList : ArrayList<TimeMeasurementDTO>

    private val mLayoutInflater: LayoutInflater

    init{
        this.mResource = resource
        this.mList = timeMeasurementList
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

        view.findViewById<TextView>(R.id.txWorkDate).text = timeMeasurement?.getWorkDate()?.format(DateTimeFormatter.ISO_DATE)
        view.findViewById<TextView>(R.id.txTotalTime).text = timeMeasurement?.getWorkingDurationHourFormat()
    }
}