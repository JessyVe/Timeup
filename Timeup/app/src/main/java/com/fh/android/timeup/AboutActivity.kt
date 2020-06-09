package com.fh.android.timeup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_about.*
import kotlin.math.roundToInt

class AboutActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setLogo()
    }

    private fun setLogo() {
        val storage = Firebase.storage("gs://timeup-b7f6a.appspot.com/")
        val storageRef = storage.reference
        var islandRef = storageRef.child("logo.png")

        val ONE_MEGABYTE: Long = 1024 * 1024
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            logo_imageview.setImageBitmap(
                Bitmap.createScaledBitmap(
                    bmp, (logo_imageview.getWidth() * 3.2).roundToInt(),
                    (logo_imageview.getHeight()* 1.5).roundToInt(), false
                )
            )

        }.addOnFailureListener {
            Log.d("Register", "Unable to download image ${it.message.toString()}")
        }
    }
}