package com.fh.android.timeup.uihelper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.fh.android.timeup.R
import com.fh.android.timeup.beans.Project
import com.fh.android.timeup.dtos.ProjectDTO
import com.fh.android.timeup.enums.UpdateStrings

class CustomListItemAdapter(context : Context,
                            resource :Int,
                            projectList : ArrayList<ProjectDTO>)
    : ArrayAdapter<ProjectDTO>(context, resource, projectList) {

    private var mResource : Int = 0
    private var mList : ArrayList<ProjectDTO>

    private val mLayoutInflater: LayoutInflater

    init{
        this.mResource = resource
        this.mList = projectList
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
        val projectDTO : ProjectDTO? = if (count > position) getItem(position) else null

        view.findViewById<TextView>(R.id.tvProjectTitle).text =
            projectDTO?.title ?: ""
        view.findViewById<TextView>(R.id.tvLastUpdate).text = projectDTO?.getLastUpdateString() ?: UpdateStrings.UNKOWN.description
        view.findViewById<TextView>(R.id.tvEstimatedTime).text =
            String.format("Estimated %d h", projectDTO?.estimatedHours)
        view.findViewById<TextView>(R.id.tvWorkedTime).text = projectDTO?.getTotalTimeSpendHourFormat()

        view.tag = projectDTO
    }

    public fun getProjectAt(index : Int) : ProjectDTO {
        return mList[index]
    }
}