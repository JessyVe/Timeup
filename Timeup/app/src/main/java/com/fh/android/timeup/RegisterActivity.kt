package com.fh.android.timeup

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.solver.widgets.ConstraintWidget.VISIBLE
import androidx.constraintlayout.widget.ConstraintSet.VISIBLE
import com.fh.android.timeup.beans.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btRegister.setOnClickListener{
            performRegistration()
        }

        tvShowLogin.setOnClickListener {
            // launch login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        GlobalScope.launch {
            setLogo()
        }
    }

    private fun performRegistration() {
        val email = txEmail.text.toString()
        val username = txUsername.text.toString()
        val password = txPassword.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Email and password may not be empty!", Toast.LENGTH_SHORT).show()
            return
        } else if(password.length < 6){
            Toast.makeText(this, "Password most consist of at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }

        vwLoading.visibility = android.view.View.VISIBLE
        // Firebase auth
        GlobalScope.launch {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener

                    Log.d("Register", "Successfully created user with uid: ${it.result?.user?.uid}")
                    saveUserToDatabase(username)

                    vwLoading.visibility = android.view.View.GONE
                }
                .addOnFailureListener {
                    val errorText = "Failed to create user! ${it.message}"
                    showToast(errorText, Toast.LENGTH_SHORT)
                    Log.d("Register", errorText)

                    progressBar.visibility = android.view.View.GONE
                }
        }
    }

    private fun showToast(errorText : String, duration : Int) {
        Toast.makeText(this, errorText, duration).show()
    }

    private fun saveUserToDatabase(username:String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, username)
        GlobalScope.launch {
            ref.setValue(user)
                .addOnSuccessListener {
                    Log.d("Register", "Successfully saved user to database.")

                    launchMainActivity()
                }
                .addOnFailureListener {
                    Log.d("Register", "Unable to save user data! ${it.message}")
                }
        }
    }

    private fun launchMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun setLogo() {
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