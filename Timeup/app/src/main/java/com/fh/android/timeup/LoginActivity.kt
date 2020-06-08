package com.fh.android.timeup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.txEmail
import kotlinx.android.synthetic.main.activity_login.txPassword

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
}