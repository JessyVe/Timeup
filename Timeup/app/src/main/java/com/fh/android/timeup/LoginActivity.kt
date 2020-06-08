package com.fh.android.timeup

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.ivLogo
import kotlinx.android.synthetic.main.activity_login.txEmail
import kotlinx.android.synthetic.main.activity_login.txPassword
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        btLogin.setOnClickListener {
            val email = txEmail.text.toString()
            val password = txPassword.text.toString()

            vwLoading.visibility = android.view.View.VISIBLE
            performLogin(email, password)
        }

        tvShowRegistration.setOnClickListener {
            finish()
        }

        GlobalScope.launch {
            setLogo()
        }
    }

    private fun performLogin(email : String, password : String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                Log.d("Login", "Successfully logged into database.")

                // launch main activity
                val intent = Intent(this, OverviewActivity::class.java)
                startActivity(intent)

                vwLoading.visibility = android.view.View.GONE
            }
            .addOnFailureListener{
                val errorText = "Logon failed! ${it.message.toString()}"
                Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show()
                Log.d("Login", errorText)

                vwLoading.visibility = android.view.View.GONE
            }
    }

    private fun setLogo(){
        val storage = Firebase.storage("gs://timeup-b7f6a.appspot.com/")
        val storageRef = storage.reference
        var islandRef = storageRef.child("time-logo.PNG")

        val ONE_MEGABYTE: Long = 1024 * 1024
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            ivLogo.setImageBitmap(
                Bitmap.createScaledBitmap(
                    bmp, (ivLogo.getWidth() - ivLogo.getWidth() * 0.3).roundToInt(),
                    (ivLogo.getHeight() - ivLogo.getHeight() * 0.3).roundToInt(), false
                )
            )
        }.addOnFailureListener {
            Log.d("Register", "Unable to download image ${it.message.toString()}")
        }
    }
}