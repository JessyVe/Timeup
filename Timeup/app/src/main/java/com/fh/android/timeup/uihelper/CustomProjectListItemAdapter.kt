package com.fh.android.timeup.uihelper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fh.android.timeup.R
import com.fh.android.timeup.dtos.ProjectDTO

class CustomProjectListItemAdapter(context : Context,
                                   resource :Int,
                                   projectList : ArrayList<ProjectDTO>)
    : ArrayAdapter<ProjectDTO>(context, resource, projectList) {

    private var mResource : Int = 0
    private var projectList : ArrayList<ProjectDTO>

    private val mLayoutInflater: LayoutInflater

    init{
        this.mResource = resource
        this.projectList = projectList
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

        projectDTO?.let {
            view.findViewById<TextView>(R.id.tvProjectTitle).text = projectDTO.title
            view.findViewById<TextView>(R.id.tvLastUpdate).text = "Last updated " + projectDTO.getLastUpdateString()

            if(projectDTO.estimatedHours == "0".toLong())
                view.findViewById<TextView>(R.id.tvEstimatedTime).text = "No estimation made"
            else
                view.findViewById<TextView>(R.id.tvEstimatedTime).text = String.format("Estimated %d h", projectDTO.estimatedHours)

            view.findViewById<TextView>(R.id.tvWorkedTime).text = projectDTO.getTotalTimeSpendHourFormat()

            setProgressBar(view, projectDTO)
            view.tag = projectDTO
        }
    }

    private fun setProgressBar(view : View, projectDTO : ProjectDTO) {

        val spendHours = projectDTO.getTotalTimeSpend()/3600.0

        when {
            (spendHours < projectDTO.estimatedHours * 0.6 || projectDTO.estimatedHours == "0".toLong()) -> {
                view.findViewById<TextView>(R.id.tvWorkedTime).background = ContextCompat.getDrawable(context,R.drawable.green_progress_circle)
            }
            spendHours < projectDTO.estimatedHours -> {
                view.findViewById<TextView>(R.id.tvWorkedTime).background = ContextCompat.getDrawable(context,R.drawable.orange_progress_circle)
            }
            else -> {
                view.findViewById<TextView>(R.id.tvWorkedTime).background = ContextCompat.getDrawable(context,R.drawable.red_progress_circle)
            }
        }
    }
}