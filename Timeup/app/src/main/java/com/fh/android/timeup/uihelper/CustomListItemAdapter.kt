package com.fh.android.timeup.uihelper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.fh.android.timeup.R
import com.fh.android.timeup.beans.Project

class CustomListItemAdapter(private val context : Context,
                            private val projectList : ArrayList<Project>) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int { return projectList.size }
    override fun getItem(position: Int): Int { return position }
    override fun getItemId(position: Int): Long { return position.toLong() }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var project = projectList[position]

        val rowView = inflater.inflate(R.layout.openprojectrow, parent, false)
        rowView.findViewById<TextView>(R.id.tvProjectTitle).text = project.title
        //rowView.findViewById<TextView>(R.id.tvLastUpdate).text = project.getLastUpdateString()
        //rowView.findViewById<TextView>(R.id.tvEstimatedTime).text = "$project.estimatedHours"
        //rowView.findViewById<TextView>(R.id.tvWorkedTime).text = project.getTotalTimeSpendHourFormat()

        rowView.tag = position
        return rowView
    }
}