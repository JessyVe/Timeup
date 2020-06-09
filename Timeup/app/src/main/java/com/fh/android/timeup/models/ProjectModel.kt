package com.fh.android.timeup.models

import android.util.Log
import com.fh.android.timeup.dtos.ProjectDTO
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

object ProjectModel : Observable() {
    private var mValueDataListener : ValueEventListener? = null
    private var mProjectList : ArrayList<ProjectDTO> = ArrayList()

    private fun getDatabaseRef():DatabaseReference?{
        return FirebaseDatabase.getInstance().reference.child("projects")
    }

    init {
        if(mValueDataListener!= null){
            getDatabaseRef()?.removeEventListener(mValueDataListener as ValueEventListener)
        }
        mValueDataListener = null

        mValueDataListener = object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val data : ArrayList<ProjectDTO> = ArrayList()
                        for(snapshot : DataSnapshot in dataSnapshot.children){
                            try{
                                data.add(ProjectDTO(snapshot))
                            } catch (ex :Exception){
                                ex.printStackTrace()
                            }
                        }
                        mProjectList = data
                        Log.i("ProjectModel", "data updated " + mProjectList.size)
                        setChanged()
                        notifyObservers()

                } catch (ex :Exception){
                    ex.printStackTrace()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
               Log.i("ProjectModel", "Data update was cancelled. Err: ${p0.message}")
            }
        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getData(): ArrayList<ProjectDTO>?{
        return mProjectList
    }

    fun getProjectAt(index : Int) : ProjectDTO? {
        if(mProjectList.size > index && index > -1)
            return mProjectList[index]

        return null
    }

    fun updateProject(dataMap : HashMap<String, Any>){
        val reference = getDatabaseRef()
        reference?.updateChildren(dataMap)
    }

    fun saveProject(projectDTO: ProjectDTO, onCompleteListener: OnCompleteListener<Void>?){
        getDatabaseRef()?.push()?.updateChildren(projectDTO.toMap())?.addOnCompleteListener{task ->
            if(task.isComplete){
                onCompleteListener?.onComplete(task)
                setChanged()
                notifyObservers()
            }
        }
    }

    fun deleteProject(projectDTO: ProjectDTO, onCompleteListener: OnCompleteListener<Void>?){
       getDatabaseRef()?.child(projectDTO.snapshot?.key ?: return)?.removeValue()?.addOnCompleteListener{task ->
            if(task.isComplete){
                onCompleteListener?.onComplete(task)
                setChanged()
                notifyObservers()
            }
        }
    }
}