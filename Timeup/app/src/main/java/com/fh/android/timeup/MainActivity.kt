package com.fh.android.timeup

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var lvProjects: ListView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvProjects = findViewById(R.id.lvProjects);

        SetupProjectsList();
    }

    private fun SetupProjectsList(){

    }
}
