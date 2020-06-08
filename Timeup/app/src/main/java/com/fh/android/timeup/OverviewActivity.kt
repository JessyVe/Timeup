package com.fh.android.timeup

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.fh.android.timeup.dtos.ProjectDTO
import com.fh.android.timeup.models.ProjectModel
import com.fh.android.timeup.uihelper.CustomListItemAdapter
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.collections.ArrayList

class OverviewActivity : AppCompatActivity(), Observer {

    private var lvProjects: ListView? = null
    private var btAddProject: Button? = null
    private var listAdapter: CustomListItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkUserLogin()

        ProjectModel
        ProjectModel.addObserver(this)

        lvProjects = findViewById(R.id.lvProjects)
        val data: ArrayList<ProjectDTO> = ArrayList()
        listAdapter = CustomListItemAdapter(this, R.layout.openprojectrow, data)
        lvProjects?.adapter = listAdapter

        btAddProject = findViewById(R.id.btAddProject)
        btAddProject?.setOnClickListener {
            val intent = Intent(this, CreateProjectActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkUserLogin(){
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null){
            launchRegistrationActivity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                launchRegistrationActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun launchRegistrationActivity(){
        val intent = Intent(this, RegisterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun update(p0: Observable?, p1: Any?) {
        listAdapter?.clear()

        val data = ProjectModel.getData()
        if (data != null) {
            listAdapter?.addAll(data)
            listAdapter?.notifyDataSetChanged()
        }
    }

    /*
    override fun onResume() {
        super.onResume()
        ProjectModel.addObserver(this)
    }

    override fun onPause() {
        super.onPause()
        ProjectModel.deleteObserver(this)
    }

    override fun onStop() {
        super.onStop()
        ProjectModel.deleteObserver(this)
    }*/
}